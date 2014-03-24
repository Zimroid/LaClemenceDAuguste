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
import auguste.server.util.Log;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.java_websocket.WebSocket;
import org.json.JSONException;

/**
 * Classe représentant une instance de salle de jeu.
 * @author Lzard
 */
public class Room
{
    private final int id; // Identifiant de la salle
    
    private User owner; // Propriétaire de la salle
    
    // Configuration de la partie
    private String gameName;
    private int    playerNumber = 6;
    private int    boardSize    = 8;
    
    // Liste des clients affectés à la salle
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    /**
     * Création d'une salle avec le nom et l'identifiant donné.
     * @param id ID de la salle
     * @param gameName Nom de la salle
     */
    public Room(int id, String gameName)
    {
        this.id = id;
        this.gameName = gameName;
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs de la salle.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        Log.debug("Broadcast (room " + this.getId() + "): " + message);
        for (WebSocket socket : this.users.keySet())
        {
            socket.send(message);
        }
    }
    
    /**
     * Envoi d'une commande game_confirm à tous les utilisateurs de la salle.
     * @throws org.json.JSONException Erreur JSON
     */
    public void confirm() throws JSONException
    {
        this.broadcast((new GameConfirm(this)).toString());
    }
    
    /**
     * Envoi d'une commande game_close à tous les utilisateurs de la salle.
     * @throws JSONException
     */
    public void close() throws JSONException
    {
        this.broadcast((new GameClose(this)).toString());
    }
    
    /**
     * Ajoute un utilisateur à la salle.
     * @param socket WebSocket de l'utilisateur
     * @param user   Utilisateur à ajouter
     */
    public void addUser(WebSocket socket, User user)
    {
        synchronized (this.users)
        {
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
            this.users.values().removeAll(Collections.singleton(user));
        }
    }
    
    /**
     * Retourne la liste des utilisateurs de la salle.
     * @return Collection des utilisateurs de la salle
     */
    public Collection<User> getUserCollection()
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
    
    /**
     * Indique si l'utilisateur donné est propriétaire de la salle.
     * @param user User à vérifier
     * @return User donné est le propriétaire
     */
    public boolean isOwner(User user)
    {
        return user.getId() == this.owner.getId();
    }
    
    /**
     * Retourne l'ID de la salle.
     * @return ID de la salle
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
     */
    public void setOwner(User user)
    {
        this.owner = user;
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
    
}
