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

import auguste.engine.GameListener;
import auguste.engine.entity.Game;
import auguste.server.command.server.GameClose;
import auguste.server.command.server.GameConfirm;
import auguste.server.exception.RoomException;
import auguste.server.exception.RoomException.Type;
import auguste.server.util.Log;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe représentant une instance de salle de jeu. Gère la configuration de la
 * future partie, les modifications du propriétaire de la salle, le lancement de
 * la partie et les utilisateurs entrant et sortant.
 * @author Lzard
 */
public class Room implements GameListener
{
    private final int  id;   // Identifiant de la salle
    private final Game game; // Partie de la salle
    
    private User owner;                          // Propriétaire de la salle
    private String name;                         // Nom de la partie
    private State state = State.WAITING;         // Etat de la partie
    
    // Liste des clients affectés à la salle
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    /**
     * Création d'une salle avec le nom et l'identifiant donné.
     * @param id   Identifiant de la salle
     * @param name Nom de la salle
     */
    public Room(int id, String name)
    {
        this.id   = id;
        this.name = name;
        this.game = new Game(this, 300000);
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs de la salle.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        Log.out("Broadcast to users of room " + this.getId() + " " + message);
        for (WebSocket socket : this.users.keySet()) socket.send(message);
    }
    
    /**
     * Configure la salle et sa partie avec les paramètres du JSON fournie.
     * @param json JSONObject à lire
     */
    public void readConfiguration(JSONObject json)
    {
        try
        {
            if (json.has("game_name")) this.name = json.getString("game_name");
            if (json.has("board_size")) this.game.getBoard().setSize(json.getInt("board_size"));
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Remplit le JSON fournie avec la configuration de la partie de la salle.
     * @param json JSONObject à remplir
     */
    public void fillConfiguration(JSONObject json)
    {
        try
        {
            json.put("game_name", this.name);
            json.put("board_size", this.game.getBoard().getSize());
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
        
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
     * Lance la partie.
     */
    public void start()
    {
    }
    
    @Override
    public void onTurnEnd()
    {
    }
    
    /**
     * Fermeture de la salle. Modification de l'état de la salle, envoi d'une
     * commande game_close à tous les utilisateurs de la salle et suppression
     * des utilisateurs.
     */
    public void close()
    {
        synchronized (this.users)
        {
            this.state = State.CLOSING;
            this.broadcast((new GameClose(this)).toString());
            for (WebSocket socket : this.users.keySet()) this.removeUser(socket);
            Server.getInstance().removeRoom(this);
        }
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
     * Indique si l'utilisateur donné est propriétaire de la salle.
     * @param user Utilisateur à vérifier
     * @return Booléen indiquant si l'utilisateur est le propriétaire
     */
    public boolean isOwner(User user)
    {
        return this.owner.equals(user);
    }
    
    /**
     * Retourne l'état de la partie.
     * @return Etat de la partie
     */
    public State getState()
    {
        return this.state;
    }
    
    /**
     * Retourne le nom de la partie.
     * @return Nom de la partie
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Modifie le propriétaire de la salle.
     * @param user Nouveau propriétaire
     * @throws auguste.server.exception.RoomException Utilisateur absent de la salle
     */
    public void setOwner(User user) throws RoomException
    {
        synchronized (this.users)
        {
            if (!this.users.containsValue(user) && user != null) throw new RoomException(Type.ABSENT_USER);
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
                if (this.users.isEmpty()) this.setOwner(null);
                else
                {
                    Object[] sockets = this.users.keySet().toArray();
                    this.setOwner(
                            this.users.get(
                                    (WebSocket) sockets[(new Random()).nextInt(sockets.length)]
                            )
                    );
                }
            }
            catch (RoomException e)
            {
                Log.debug(e);
            }
        }
    }
    
    /**
     * Modifie le nom de la partie.
     * @param name Nouveau nom
     */
    public void setName(String name)
    {
        this.name = name;
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
        // Vérification de l'état de la salle
        if (this.state == State.CLOSING) throw new RoomException(Type.UNAVAILABLE_ROOM);
        
        // Ajout de la salle à l'utilisateur
        user.getRooms().add(this);
        
        // Ajout de l'utilisateur à la salle
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
        // Vérification de l'existance de l'utilisateur
        if (this.users.containsValue(user))
        {
            // Retrait de la salle de la liste des salles de l'utilisateur
            user.getRooms().remove(this);

            // Retrait de l'utilisateur de la salle
            for (Entry<WebSocket, User> set : this.users.entrySet())
            {
                if (set.getValue().equals(user))
                {
                    synchronized (this.users)
                    {
                        this.users.remove(set.getKey());
                    }
                }
            }

            // Fermeture de la salle s'il n'y a plus d'utilisateurs
            if (this.getUsers().isEmpty()) this.close();

            // Changement de propriétaire si l'utilisateure retiré l'était
            if (user == this.owner) this.changeOwner();
        }
    }
    
    /**
     * Retire un utilisateur de la salle à partir de sa socket.
     * @param socket Socket de l'utilisateur à retirer
     */
    public void removeUser(WebSocket socket)
    {
        if (this.users.containsKey(socket)) this.removeUser(this.users.get(socket));
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
    
    /**
     * Etats possibles d'une partie.
     */
    public enum State
    {
        WAITING,
        RUNNING,
        FINISHING,
        CLOSING
    }
    
}
