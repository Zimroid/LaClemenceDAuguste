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
import auguste.server.command.server.GameConfirm;
import auguste.server.command.server.RoomUsers;
import auguste.server.exception.NotInThisRoomException;
import auguste.server.util.Configuration;
import auguste.server.util.Log;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe représentant une instance de salon de jeu. Gère la configuration de la
 * future partie, les modifications du propriétaire du salon et le lancement de
 * la partie.
 * 
 * La configuration de la partie est stockée dans un objet JSON qui est lu et
 * retransmis aux différents utilisateurs. Lors de la réception d'une commande
 * game_start, la partie est crée et configurée en fonction de cet objet JSON.
 * 
 * L'évolution de la partie et la communication de son avancement est gérée par
 * le salon. Les informations sont transmises à tous les utilisateurs.
 * 
 * Les utilisateurs présents dans la salle ou non sont gérés par l'instance
 * courante de Server.
 * 
 * @author Lzard
 */
public class Room implements GameListener
{
    // Identifiant du salon
    private final int id;
    
    private User   owner; // Propriétaire du salon
    private State  state; // Etat de la partie
    private Game   game;  // Partie du salon
    
    // Configuration de la partie
    private final JSONObject configuration = new JSONObject(); 
    
    // Liste des clients affectés au salon
    private final HashMap<Integer, User> users = new HashMap<>();
    
    /**
     * Instanciation d'un salon avec le nom et l'identifiant donné.
     * @param id   Identifiant du salon
     * @param name Nom du salon
     */
    public Room(int id, String name)
    {
        // Initialisation des attributs
        this.id   = id;
        
        // Paramètrage de la configuration par défaut
        try
        {
            this.configuration.put("game_name",          name                                               );
            this.configuration.put("game_board_size",    Configuration.getInt ("game_default_board_size"   ));
            this.configuration.put("game_turn_duration", Configuration.getLong("game_default_turn_duration"));
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs présents dans le salon.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        for (User user : this.users.values()) Server.getInstance().send(user.getSocket(), message);
    }
    
    /**
     * Effectue le lancement de la partie.
     */
    public void start()
    {
    }
    
    /**
     * Gère la fin d'un tour de jeu.
     */
    @Override
    public void onTurnEnd()
    {
    }
    
    /**
     * Retourne l'identifiant du salon.
     * @return Identifiant du salon
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * Modifie le propriétaire du salon.
     * @param user Nouveau propriétaire
     * @throws NotInThisRoomException Utilisateur absent du salon
     */
    public void setOwner(User user) throws NotInThisRoomException
    {
        synchronized (this.users)
        {
            if (!this.users.containsValue(user)) throw new NotInThisRoomException(this, user);
            this.owner = user;
        }
    }
    
    /**
     * Modifie le propriétaire du salon. Sélectionne un utilisateur au hasard
     * parmis les utilisateurs du salon.
     */
    public void setRandomOwner()
    {
        synchronized (this.users)
        {
            try
            {
                Object[] ids = this.users.keySet().toArray();
                this.setOwner(
                        this.users.get(
                                (Integer) ids[(new Random()).nextInt(ids.length)]
                        )
                );
            }
            catch (NotInThisRoomException e)
            {
                Log.debug(e);
            }
        }
    }
    
    /**
     * Indique si l'utilisateur donné est le propriétaire du salon.
     * @param user Utilisateur à vérifier
     * @return Utilisateur propriétaire
     */
    public boolean isOwner(User user)
    {
        return user == this.owner;
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
     * Modifie l'état de la partie.
     * @param state Nouvel état
     */
    public void setState(State state)
    {
        this.state = state;
    }
    
    /**
     * Configure la partie avec les paramètres du JSON fourni.
     * @param json JSONObject à lire
     */
    public void setConfiguration(JSONObject json)
    {
        try
        {
            if (json.has("game_name"))          this.configuration.put("game_name",          json.getString("game_name"         ));
            if (json.has("game_board_size"))    this.configuration.put("game_board_size",    json.getInt   ("game_board_size"   ));
            if (json.has("game_turn_duration")) this.configuration.put("game_turn_duration", json.getLong  ("game_turn_duration"));
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Ajoute au JSON fourni la configuration partielle de la partie du salon.
     * @param json JSONObject à remplir
     */
    public void addLightConfiguration(JSONObject json)
    {
        try
        {
            json.put("room_id",            this.id                                           );
            json.put("game_name",          this.configuration.getString("game_name"         ));
            json.put("game_board_size",    this.configuration.getInt   ("game_board_size"   ));
            json.put("game_turn_duration", this.configuration.getLong  ("game_turn_duration"));
            json.put("players_number",     this.getUsers().size()                            );
            json.put("game_state",         this.getState()                                   );
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Ajoute au JSON fourni la configuration complète de la partie du salon.
     * @param json JSONObject à remplir
     */
    public void addFullConfiguration(JSONObject json)
    {
        try
        {
            json.put("game_state",    this.getState()   );
            json.put("configuration", this.configuration);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Broadcast la configuration de la partie.
     */
    public void updateConfiguration()
    {
        this.broadcast((new GameConfirm(this)).toString());
    }
    
    /**
     * Retourne la liste des utilisateurs du salon.
     * @return Collection des utilisateurs du salon
     */
    public HashMap<Integer, User> getUsers()
    {
        return this.users;
    }
    
    /**
     * Ajoute un attribut "users" contenant la liste des utilisateurs du salon
     * au JSON fourni en paramètre.
     * @param json
     */
    public void addUserList(JSONObject json)
    {
        try
        {
            // Création du JSONArray décrivant la liste des utilisateurs
            JSONArray userList = new JSONArray();
            for (User user : this.users.values())
            {
                JSONObject userEntry = new JSONObject();
                userEntry.put("user_id", user.getId());
                userEntry.put("user_name", user.getName());
                userEntry.put("is_owner", this.isOwner(user));
                userEntry.put("team", 0);
                userList.put(userEntry);
            }

            // Ajout de la liste au JSON
            json.put("users", userList);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Indique si un utilisateur est présent dans le salon.
     * @param user Utilisateur à vérifier
     * @return Booléen indiquant la présence de l'utilisateur
     */
    public boolean isInRoom(User user)
    {
        return this.users.containsValue(user);
    }
    
    /**
     * Broadcast la liste des utilisateurs du salon.
     */
    public void updateUsers()
    {
        this.broadcast((new RoomUsers(this)).toString());
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
