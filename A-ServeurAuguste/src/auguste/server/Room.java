/*
 * Copyright 2014 Conseil7.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package auguste.server;

import auguste.server.command.server.GameClose;
import auguste.server.command.server.GameConfirm;
import auguste.server.exception.RoomException;
import auguste.server.exception.RoomException.RoomExceptionType;
import auguste.server.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.json.JSONException;

/**
 * Classe représentant une instance de salle de jeu. Gère la configuration de la
 * future partie, les modifications du propriétaire de la salle, le lancement de
 * la partie et les utilisateurs entrant et sortant.
 * @author Lzard
 */
public class Room
{
    private final int id; // Identifiant de la salle
    
    private User owner;                          // Propriétaire de la salle
    private GameState state = GameState.WAITING; // Etat de la salle
    
    // Configuration de la partie
    private String gameName;
    private int    playerNumber = 6;
    private int    boardSize    = 8;
    
    // Liste des clients affectés à la salle
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    /**
     * Création d'une salle avec le nom et l'identifiant donné.
     * @param id       Identifiant de la salle
     * @param gameName Nom de la salle
     */
    public Room(int id, String gameName)
    {
        this.id       = id;
        this.gameName = gameName;
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs de la salle.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        Log.out("Broadcast to room " + this.getId() + " " + message);
        for (WebSocket socket : this.users.keySet()) socket.send(message);
    }
    
    /**
     * Envoi d'une commande game_confirm à tous les utilisateurs de la salle.
     * @throws org.json.JSONException Erreur de création du JSON
     */
    public void confirm() throws JSONException
    {
        this.broadcast((new GameConfirm(this)).toString());
    }
    
    /**
     * Envoi d'une commande game_close à tous les utilisateurs de la salle.
     * @throws JSONException Erreur de création du JSON
     */
    public void close() throws JSONException
    {
        synchronized (this.users)
        {
            this.state = GameState.CLOSING;
            this.broadcast((new GameClose(this)).toString());
            for (User user : this.users.values()) this.removeUser(user);
        }
    }
    
    /**
     * Indique si l'utilisateur donné est propriétaire de la salle.
     * @param user Utilisateur à vérifier
     * @return Booléen indiquant si l'utilisateur est le propriétaire
     */
    public boolean isOwner(User user)
    {
        return user == this.owner;
    }
    
    /**
     * Retourne l'identifiant de la salle.
     * @return Identifiant de la salle
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * Retourne le nom de la partie.
     * @return Nom de la partie
     */
    public String getGameName()
    {
        return this.gameName;
    }
    
    public GameState getGameState()
    {
        return this.state;
    }
    
    /**
     * Retourne le nombre de joueurs maximum.
     * @return Nombre de joueurs maximum
     */
    public int getPlayerNumber()
    {
        return this.playerNumber;
    }
    
    /**
     * Retourne la taille du plateau.
     * @return Taille du plateau
     */
    public int getBoardSize()
    {
        return this.boardSize;
    }
    
    /**
     * Définie le propriétaire de la salle.
     * @param user Nouveau propriétaire
     * @throws auguste.server.exception.RoomException Utilisateur absent de la salle
     */
    public void setOwner(User user) throws RoomException
    {
        synchronized (this.users)
        {
            if (!this.users.containsValue(user)) throw new RoomException(RoomExceptionType.ABSENT_USER);
            this.owner = user;
        }
    }
    
    /**
     * Change le propriétaire de la salle. Sélectionne un utilisateur au hasard
     * parmis les utilisateurs de la salle.
     */
    public void changeOwner()
    {
        synchronized (this.users)
        {
            try
            {
                Object[] sockets = this.users.keySet().toArray();
                this.setOwner(
                        this.users.get(
                                (WebSocket) sockets[(new Random()).nextInt(sockets.length)]
                        )
                );
            }
            catch (RoomException e) {}
        }
    }
    
    /**
     * Modifie le nom de la partie.
     * @param gameName Nouveau nom
     */
    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }
    
    /**
     * Modifie le nombre de joueurs maximum.
     * @param playerNumber Nouveau nombre
     */
    public void setPlayerNumber(int playerNumber)
    {
        this.playerNumber = playerNumber;
    }
    
    /**
     * Modifie la taille du plateau de jeu.
     * @param boardSize Nouvelle taille
     */
    public void setBoardSize(int boardSize)
    {
        this.boardSize = boardSize;
    }
    
    /**
     * Ajoute un utilisateur à la salle. Si la salle est fermée, l'utilisateur
     * ne peut rejoindre.
     * @param socket WebSocket de l'utilisateur
     * @param user   Utilisateur à ajouter
     * @throws auguste.server.exception.RoomException Salle fermée
     */
    public void addUser(WebSocket socket, User user) throws RoomException
    {
        if (this.state == GameState.CLOSING) throw new RoomException(RoomExceptionType.UNAVAILABLE_ROOM);
        synchronized (this.users)
        {
            user.getRooms().add(this);
            this.users.put(socket, user);
        }
    }
    
    /**
     * Retire un utilisateur de la salle.
     * @param user Utilisateur à retirer
     */
    public void removeUser(User user)
    {
        synchronized (this.users)
        {
            user.getRooms().remove(this);
            this.users.values().remove(user);
            if (user == this.owner) this.changeOwner();
        }
    }
    
    /**
     * Retourne la liste des utilisateurs de la salle.
     * @return Collection des utilisateurs de la salle
     */
    public Collection<User> getUsers()
    {
        return this.users.values();
    }
    
    /**
     * Indique si un utilisateur est présent dans la salle.
     * @param user Utilisateur à vérifier
     * @return Booléen indiquant la présence de l'utilisateur
     */
    public boolean isInRoom(User user)
    {
        return this.users.containsValue(user);
    }
    
    public enum GameState
    {
        WAITING,
        RUNNING,
        FINISHING,
        CLOSING
    }
    
}
