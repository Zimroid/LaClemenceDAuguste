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
import java.util.HashMap;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe représentant une instance de salon de jeu. Gère la configuration de la
 * future partie, les modifications du propriétaire de la salon, le lancement de
 * la partie et les utilisateurs entrant et sortant.
 * 
 * Une fois la partie lancée, les actions de chaque joueurs sont transmises au
 * jeu, et lorsque le tour est terminée, le nouvel état du plateau est
 * broadcasté à tous les utilisateurs du salon.
 * 
 * @author Lzard
 */
public class Room implements GameListener
{
    // Identifiant du salon
    private final int id;
    
    private User   owner;                 // Propriétaire du salon
    private String name;                  // Nom de la partie
    private State  state = State.WAITING; // Etat de la partie
    
    private final Game       game          = new Game(this, 300000); // Partie du salon
    private final JSONObject configuration = new JSONObject();       // Configuration de la partie
    
    // Liste des clients affectés au salon
    private final HashMap<Integer, User> users = new HashMap<>();
    
    /**
     * Instanciation d'un salon avec le nom et l'identifiant donné.
     * @param id   Identifiant du salon
     * @param name Nom du salon
     */
    public Room(int id, String name)
    {
        this.id   = id;
        this.name = name;
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs du salon.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        for (User user : this.users.values()) Server.getInstance().send(user.getSocket(), message);
    }
    
    /**
     * Configure le salon et sa partie avec les paramètres du JSON fourni.
     * @param json JSONObject à lire
     */
    public void readConfiguration(JSONObject json)
    {
        try
        {
            if (json.has("game_name"))  this.configuration.put("game_name",  json.getString("game_name" ));
            if (json.has("board_size")) this.configuration.put("board_size", json.getInt   ("board_size"));
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Remplit le JSON fourni avec la configuration de la partie du salon.
     * @param json JSONObject à remplir
     */
    public void fillConfiguration(JSONObject json)
    {
        try
        {
            json.put("game_name",  this.configuration.getString("game_name" ));
            json.put("board_size", this.configuration.getInt   ("board_size"));
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Envoi d'une commande game_confirm à tous les utilisateurs du salon.
     */
    public void confirm()
    {
        this.broadcast((new GameConfirm(this)).toString());
    }
    
    /**
     * Gère le lancement de la partie.
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
     * Indique si l'utilisateur donné est le propriétaire du salon.
     * @param user Utilisateur à vérifier
     * @return Utilisateur propriétaire ou non
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
     * Retourne le nom de la partie.
     * @return Nom de la partie
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Modifie le propriétaire du salon.
     * @param user Nouveau propriétaire
     * @throws auguste.server.exception.RoomException Utilisateur absent du salon
     */
    public void setOwner(User user) throws RoomException
    {
        synchronized (this.users)
        {
            if (!this.users.containsValue(user)) throw new RoomException(Type.ABSENT_USER);
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
            catch (RoomException e)
            {
                Log.debug(e);
            }
        }
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
     * Modifie le nom de la partie.
     * @param name Nouveau nom
     */
    public void setName(String name)
    {
        this.name = name;
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
     * Indique si un utilisateur est présent dans le salon.
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
