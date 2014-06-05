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

package octavio.server;

import octavio.engine.GameListener;
import octavio.engine.entity.Board;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.Legion;
import octavio.engine.entity.Player;
import octavio.engine.entity.Team;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.engine.entity.pawn.Laurel;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.turnData.Battle;
import octavio.engine.turnData.Move;
import octavio.engine.turnData.Tenaille;
import octavio.server.command.server.GameConfirm;
import octavio.server.command.server.GameTurn;
import octavio.server.command.server.RoomUsers;
import octavio.server.exception.NotInThisRoomException;
import octavio.server.util.Configuration;
import octavio.server.util.Log;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import octavio.engine.entity.Bot;
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
    
    // Listes des joueurs et des équipes
    private final HashMap<Player, Integer> playing = new HashMap<>();
    private final ArrayList<Team>          teams   = new ArrayList<>();
    
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
        this.state = State.WAITING;
        
        // Paramètrage de la configuration par défaut
        try
        {
            this.configuration.put("game_name",          name                                               );
            this.configuration.put("game_board_size",    Configuration.getInt ("game_default_board_size"   ));
            this.configuration.put("game_turn_duration", Configuration.getLong("game_default_turn_duration"));
            this.configuration.put("game_mode", "normal");
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
     * @throws org.json.JSONException
     */
    public void start() throws JSONException
    {
        if (this.state == State.WAITING)
        {
            this.state = State.RUNNING;
            this.game = new Game(this, this.configuration.getLong("game_turn_duration"));
            //this.game.setTurnDuration(this.configuration.getLong("game_turn_duration"));
            this.game.setBoard(new Board(this.configuration.getInt("game_board_size")));
            for (Player player : this.playing.keySet()) this.game.addPlayer(player);
            this.game.initBoard();
            this.broadcast((new GameTurn(this, true)).toString());
        }
    }
    
    /**
     *
     * @param user
     * @param json
     * @throws org.json.JSONException
     */
    public void addAction(User user, JSONObject json) throws JSONException
    {
        Cell cell = this.game.getBoard().getCell(new Point(json.getInt("start_u"), json.getInt("start_w")));
        Action action = null;
        if (this.playing.get(this.game.getLegion(json.getInt("legion_id")).getPlayer()) == user.getId() && cell != null)
        {
            if (cell.getPawn() != null)
            {
                Cell newCell = this.game.getBoard().getCell(new Point(json.getInt("end_u"), json.getInt("end_w")));
                if (newCell != null)
                {
                    Movement movement = new Movement(cell.getPawn(), newCell);
                    action = new Action(this.game.getLegion(json.getInt("legion_id")), movement, null);
                }
            }
        }
        
        if (action != null) this.game.addAction(action);
    }
    
    /**
     * Gère la fin d'un tour de jeu.
     */
    @Override
    public void onTurnEnd()
    {
        this.broadcast((new GameTurn(this, false)).toString());
    }
    
    /**
     *
     * @param json
     * @throws org.json.JSONException
     */
    public void addTurnData(JSONObject json) throws JSONException
    {
        // Ajout des informations
        JSONObject informations = new JSONObject();
        informations.put("board_size", this.game.getBoard().getSize());
        JSONArray legions = new JSONArray();
        JSONObject legionData;
        Legion legion;
        for (int i = 0; i < 6; i++)
        {
            legion = this.game.getLegion(i);
            if (legion != null)
            {
                legionData = new JSONObject();
                legionData.put("legion_id", legion.getPosition());
                legionData.put("legion_shape", legion.getShape());
                legionData.put("legion_color", legion.getColor());
                if (this.playing.get(legion.getPlayer()) == 0) legionData.put("legion_owner", "Bot");
                else legionData.put("legion_owner", this.users.get(this.playing.get(legion.getPlayer())).getName());
                legions.put(legionData);
            }
        }
        informations.put("legions", legions);
        json.put("informations", informations);
        
        // Ajout du plateau
        JSONArray boardData = new JSONArray();
        JSONObject cellData;
        for (Cell cell : this.game.getBoard().getCells().values())
        {
            if (cell.getPawn() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("type", cell.getPawn().getClass().getSimpleName().toLowerCase());
                
                if (cell.getTent() != null)
                {
                    cellData.put("tent_color", cell.getTent().getColor());
                    cellData.put("tent_shape", cell.getTent().getShape());
                    cellData.put("tent_legion", cell.getTent().getPosition());
                }

                if (cell.getPawn() instanceof Soldier)
                {
                    cellData.put("legion_armor", ((Soldier)cell.getPawn()).isArmored());
                    cellData.put("legion_id", ((Soldier)cell.getPawn()).getLegion().getPosition());
                    cellData.put("legion_color", cell.getPawn().getLegion().getColor());
                    cellData.put("legion_shape", cell.getPawn().getLegion().getShape());
                }
                boardData.put(cellData);
            }
            else if (cell.getTent() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("tent_color", cell.getTent().getColor());
                cellData.put("tent_shape", cell.getTent().getShape());
                cellData.put("tent_legion", cell.getTent().getPosition());
                boardData.put(cellData);
            }
        }
        json.put("board", boardData);
        
        // Application des actions et ajout du prochain timeout
        json.put("turn_timeout", (new Date((new Date()).getTime() + this.configuration.getLong("game_turn_duration"))).getTime());
        boolean ends = this.game.applyActions();
        
        // Ajout des déplacements
        JSONArray movesData = new JSONArray();
        JSONObject moveData;
        for (Move move : this.game.getMoves())
        {
            moveData = new JSONObject();
            moveData.put("start_u", move.getP1().getX());
            moveData.put("start_w", move.getP1().getY());
            moveData.put("end_u", move.getP2().getX());
            moveData.put("end_w", move.getP2().getY());
            moveData.put("destroyed", move.isDies());
            movesData.put(moveData);
        }
        json.put("moves", movesData);
        
        // Ajout des tenailles
        JSONArray tenaillesData = new JSONArray();
        JSONObject tenailleData;
        for (Tenaille tenaille : this.game.getTenailles())
        {
            tenailleData = new JSONObject();
            tenailleData.put("front1_u", tenaille.getP1().getX());
            tenailleData.put("front1_w", tenaille.getP1().getY());
            tenailleData.put("front2_u", tenaille.getP2().getX());
            tenailleData.put("front2_w", tenaille.getP2().getY());
            tenaillesData.put(tenailleData);
        }
        json.put("tenailles", tenaillesData);
        
        // Ajout des combats
        JSONArray battlesData = new JSONArray();
        JSONObject battleData;
        for (Battle battle : this.game.getBattles())
        {
            battleData = new JSONObject();
            battleData.put("pawn1_u", battle.getP1().getX());
            battleData.put("pawn1_w", battle.getP1().getY());
            battleData.put("pawn2_u", battle.getP2().getX());
            battleData.put("pawn2_w", battle.getP2().getY());
            if (battle.getDies() != null)
            {
                battleData.put("loser_u", battle.getDies().getX());
                battleData.put("loser_w", battle.getDies().getY());
            }
            battlesData.put(battleData);
        }
        json.put("battles", battlesData);
        
        if (ends)
        {
            json.put("winner", this.game.getWinner().getPosition());
            this.game.getTimer().cancel();
            this.game.getTimer().purge();
        }
        
        // Début du prochain tour
        this.game.nextTurn();
    }/**
     *
     * @param json
     * @throws org.json.JSONException
     */
    public void addInitialTurnData(JSONObject json) throws JSONException
    {
        // Ajout des informations
        JSONObject informations = new JSONObject();
        informations.put("board_size", this.game.getBoard().getSize());
        JSONArray legions = new JSONArray();
        JSONObject legionData;
        Legion legion;
        for (int i = 0; i < 6; i++)
        {
            legion = this.game.getLegion(i);
            if (legion != null)
            {
                legionData = new JSONObject();
                legionData.put("legion_id", legion.getPosition());
                legionData.put("legion_shape", legion.getShape());
                legionData.put("legion_color", legion.getColor());
                if (this.playing.get(legion.getPlayer()) == 0) legionData.put("legion_owner", "Bot");
                else legionData.put("legion_owner", this.users.get(this.playing.get(legion.getPlayer())).getName());
                legions.put(legionData);
            }
        }
        informations.put("legions", legions);
        json.put("informations", informations);
        
        // Ajout du plateau
        JSONArray boardData = new JSONArray();
        JSONObject cellData;
        for (Cell cell : this.game.getBoard().getCells().values())
        {
            if (cell.getPawn() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("type", cell.getPawn().getClass().getSimpleName().toLowerCase());
                
                if (cell.getTent() != null)
                {
                    cellData.put("tent_color", cell.getTent().getColor());
                    cellData.put("tent_shape", cell.getTent().getShape());
                    cellData.put("tent_legion", cell.getTent().getPosition());
                }

                if (cell.getPawn() instanceof Soldier)
                {
                    cellData.put("legion_armor", ((Soldier)cell.getPawn()).isArmored());
                    cellData.put("legion_id", ((Soldier)cell.getPawn()).getLegion().getPosition());
                    cellData.put("legion_color", cell.getPawn().getLegion().getColor());
                    cellData.put("legion_shape", cell.getPawn().getLegion().getShape());
                }
                boardData.put(cellData);
            }
            else if (cell.getTent() != null)
            {
                cellData = new JSONObject();
                cellData.put("u", cell.getP().getX());
                cellData.put("w", cell.getP().getY());
                cellData.put("tent_color", cell.getTent().getColor());
                cellData.put("tent_shape", cell.getTent().getShape());
                cellData.put("tent_legion", cell.getTent().getPosition());
                boardData.put(cellData);
            }
        }
        json.put("board", boardData);
        
        
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
            this.updateUsers();
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
     * @param json JSON à lire
     * @throws JSONException JSON éronné
     */
    public void setConfiguration(JSONObject json) throws JSONException, NotInThisRoomException
    {
        // Configuration de la partie
        //this.configuration.put("game_name",          json.getString("game_name"         ));
        this.configuration.put("game_board_size",    json.getInt   ("game_board_size"   ));
        this.configuration.put("game_turn_duration", json.getLong  ("game_turn_duration"));
        this.configuration.put("game_mode", json.getString  ("game_mode"));

        // Attribution des utilisateurs
        if (json.has("teams"))
        {
            // Vidage des listes
            this.playing.clear();
            this.teams.clear();
            
            // Ajout des équipes
            for (int iTeam = 0; iTeam < json.getJSONArray("teams").length(); iTeam++)
            {
                // Récupération du JSON
                JSONObject teamData = json.getJSONArray("teams").getJSONObject(iTeam);
                
                // Instanciation de l'équipe
                Team newTeam = new Team();
                this.teams.add(newTeam);
            
                // Ajout des joueurs
                for (int iPlayer = 0; iPlayer < teamData.getJSONArray("players").length(); iPlayer++)
                {
                    // Récupération du JSON
                    JSONObject playerData = teamData.getJSONArray("players").getJSONObject(iPlayer);
                    
                    // Instanciation du joueur
                    Player newPlayer = new Player();
                    newPlayer.setGame(this.game);
                    newPlayer.setTeam(newTeam);
                    
                    // Attribution du joueur à l'utilisateur
                    if (playerData.has("bot"))
                    {
                        Bot.Strategy strategy = playerData.getString("bot").equals("random") ? Bot.Strategy.random : Bot.Strategy.pseudoRandom;
                        newPlayer.setBot(new Bot(newPlayer, strategy));
                        newPlayer.setConnected(false);
                        this.playing.put(newPlayer, 0); 
                    }
                    else
                    {
                        int userId = playerData.getInt("player_user_id");
                        if (this.getUsers().containsKey(userId))
                        {
                            //throw new NotInThisRoomException(this, userId);
                            this.playing.put(newPlayer, userId);
                        }
                    }

                    // Ajout des légions
                    for (int iLegion = 0; iLegion < playerData.getJSONArray("legions").length(); iLegion++)
                    {
                        // Récupération du JSON
                        JSONObject legionData = playerData.getJSONArray("legions").getJSONObject(iLegion);
                        
                        // Instanciation de la légion
                        Legion newLegion = new Legion(newPlayer);
                        newLegion.setColor(legionData.getString("legion_color"));
                        newLegion.setShape(legionData.getString("legion_shape"));
                        newLegion.setPosition(legionData.getInt("legion_position"));
                        newPlayer.addLegion(newLegion);
                    }
                }
            }
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
            // Ajout de la configuriation partielle de la partie
            json.put("room_id",            this.id                                           );
            json.put("game_name",          this.configuration.getString("game_name"         ));
            json.put("game_board_size",    this.configuration.getInt   ("game_board_size"   ));
            json.put("game_turn_duration", this.configuration.getLong  ("game_turn_duration"));
            json.put("game_mode", this.configuration.getString  ("game_mode"));
            json.put("game_state",         this.getState()                                   );
            json.put("players_number",     this.getUsers().size()                            );
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
            // Etat de la partie
            json.put("game_state", this.getState());
            
            // Configuration de la partie
            json.put("configuration", this.configuration);
            
            // Ajout des équipes
            JSONArray teamsToAdd = new JSONArray();
            JSONObject teamData;
            for (Team team : this.teams)
            {
                // Instanciation du JSON
                teamData = new JSONObject();
            
                // Ajout des joueurs
                JSONArray playersToAdd = new JSONArray();
                JSONObject playerData;
                for (Player player : team.getPlayers())
                {
                    // Instanciation du JSON
                    playerData = new JSONObject();
                    if (this.playing.containsKey(player))
                    {
                        playerData.put("player_user_id", this.playing.get(player));
                    }
                    else playerData.put("player_user_id", 0);
            
                    // Légions
                    JSONArray legionsToAdd = new JSONArray();
                    JSONObject legionData;
                    for (Legion legion : player.getLegions())
                    {
                        legionData = new JSONObject();
                        legionData.put("legion_position", legion.getPosition());
                        legionData.put("legion_color", legion.getColor());
                        legionData.put("legion_shape", legion.getShape());
                        legionsToAdd.put(legionData);
                    }
                    playerData.put("legions", legionsToAdd);
                    playersToAdd.put(playerData);
                }
                teamData.put("players", playersToAdd);
                teamsToAdd.put(teamData);
            }
            json.put("teams", teamsToAdd);
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
