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

package octavio.server.db.entities;

import java.awt.Point;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import octavio.engine.GameListener;
import octavio.engine.entity.Board;
import octavio.engine.entity.Bot;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.Legion;
import octavio.engine.entity.Player;
import octavio.engine.entity.Team;
import octavio.engine.entity.pawn.Armor;
import octavio.engine.entity.pawn.Laurel;
import octavio.engine.entity.pawn.Pawn;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.entity.pawn.Wall;
import octavio.server.commands.ServerCommand;
import octavio.server.commands.server.GameTurn;
import octavio.server.db.managers.RoomManager;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 *
 * @author Lzard
 */
public class Room implements GameListener
{
    /**
     * Liste des salles par identifiant.
     */
    private static final HashMap<Integer, Room> rooms = new HashMap<>();

    /**
     * Retourne une salle avec son identifiant. Si la salle n'est pas en
     * mémoire, elle est chargée de la base de donnée.
     *
     * @param id Identifiant de la salle
     *
     * @return Salle correspondante
     *
     * @throws CommandException Identifiant inconnu
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public static Room getRoom(int id) throws CommandException, JSONException, SQLException
    {
        synchronized (Room.rooms)
        {
            if (Room.rooms.containsKey(id))
            {
                return Room.rooms.get(id);
            }
            else
            {
                try (Connection connection = Configuration.getDbConnection())
                {
                    Room room = (new RoomManager(connection)).getRoom(id);
                    Room.rooms.put(id, room);
                    connection.commit();
                    return room;
                }
            }
        }
    }

    /**
     * Retourne le nombre de salles en cours.
     *
     * @return Nombre de salles
     */
    public static int getRoomCount()
    {
        synchronized (Room.rooms)
        {
            return Room.rooms.size();
        }
    }

    /**
     * Créée et retourne une nouvelle salle.
     *
     * @return Nouvelle salle vide
     */
    public static Room getNewRoom()
    {
        synchronized (Room.rooms)
        {
            return new Room(0);
        }
    }

    public static ArrayList<Room> getRooms()
    {
        return new ArrayList<>(Room.rooms.values());
    }

    /**
     * Identifiant de la salle.
     */
    private final int id;

    /**
     * Propriétaire de la salle.
     */
    private User owner;

    /**
     * Nom de la salle.
     */
    private String name;

    /**
     * Utilisateurs de la salle.
     */
    private final HashMap<Integer, User> users = new HashMap<>();

    /**
     * Joueurs de la salle.
     */
    private final HashMap<Player, User> players = new HashMap<>();

    /**
     * Numéro du tour courant.
     */
    private int turnId;

    /**
     * Partie de la salle.
     */
    private final Game game = new Game(
            this,
            Configuration.getInt("default_turn_duration", 60000)
    );

    /**
     * Instanciation de la salle.
     *
     * @param id Identifiant de la salle
     */
    public Room(int id)
    {
        this.id = id;
        this.game.setBoard(new Board(Configuration.getInt("default_board_size", 5)));
    }

    /**
     * Retourne l'identifiant de la salle.
     *
     * @return Identifiant de la salle
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Retourne le propriétaire de la salle.
     *
     * @return Propriétaire de la salle
     */
    public User getOwner()
    {
        return this.owner;
    }

    /**
     * Modifie le propriétaire de la salle. Si le paramètre fourni est nul, le
     * nouveau propriétaire est choisi aléatoirement parmis les utilisateurs de
     * la salle.
     *
     * @param user Nouveau propriétaire
     */
    public void setOwner(User user)
    {
        if (user == null)
        {
            this.owner = this.users.get(
                    (new Random()).nextInt(
                            this.users.size()
                    )
            );
        }
        else
        {
            this.owner = user;
        }
    }

    /**
     * Retourne le nom de la salle.
     *
     * @return Nom de la salle
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Modifie le nom de la salle.
     *
     * @param name Nouveau nom
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retourne la liste des utilisateurs de la salle.
     *
     * @return Utilisateurs de la salle
     */
    public ArrayList<User> getUsers()
    {
        return new ArrayList<>(this.users.values());
    }

    public JSONArray getUsersJSON() throws JSONException
    {
        // Création du JSONArray décrivant la liste des utilisateurs
        JSONArray userList = new JSONArray();
        for (User user : this.users.values())
        {
            JSONObject userEntry = new JSONObject();
            userEntry.put("user_id", user.getId());
            userEntry.put("user_name", user.getName());
            userEntry.put("is_owner", this.getOwner() == user);
            userList.put(userEntry);
        }

        return userList;
    }

    /**
     * Ajoute un utilisateur à la salle.
     *
     * @param user Utilisateur à ajouter
     */
    public void addUser(User user)
    {
        this.users.put(user.getId(), user);
    }

    /**
     * Retire un utilisateur de la salle.
     *
     * @param user Utilisateur à retirer
     */
    public void removeUser(User user)
    {
        this.users.remove(user.getId());
    }

    /**
     * Retourne la liste des joueurs
     *
     * @return Liste des joueurs
     */
    public HashMap<Player, User> getPlayers()
    {
        return this.players;
    }

    /**
     * Retourne le numéro du tour courant.
     *
     * @return Numéro du tour
     */
    public int getTurnId()
    {
        return this.turnId;
    }

    /**
     * Modifie le numéro du tour courant.
     */
    public void nextTurn()
    {
        this.turnId++;
    }

    /**
     * Retourne la partie de la salle.
     *
     * @return La partie
     */
    public Game getGame()
    {
        return this.game;
    }

    /**
     * Retourne la configuration de la partie sous forme de JSON.
     *
     * @return JSON de la configuration
     *
     * @throws JSONException Erreur JSON
     */
    public JSONObject getConfiguration() throws JSONException
    {
        JSONObject json = new JSONObject();

        json.put("game_name", this.getName());
        json.put("game_board_size", this.game.getBoard().getSize());
        json.put("game_turn_duration", this.game.getTurnDuration());

        JSONArray teamArray = new JSONArray();
        JSONArray playerArray;
        JSONArray legionArray;
        JSONObject teamJson;
        JSONObject playerJson;
        JSONObject legionJson;

        for (Team team : this.game.getTeams())
        {
            teamJson = new JSONObject();
            playerArray = new JSONArray();

            for (Player player : team.getPlayers())
            {
                playerJson = new JSONObject();
                legionArray = new JSONArray();

                for (Legion legion : player.getLegions())
                {
                    legionJson = new JSONObject();
                    legionJson.put("legion_position", legion.getPosition());
                    legionJson.put("legion_color", legion.getColor());
                    legionJson.put("legion_shape", legion.getShape());
                    legionArray.put(legionJson);
                }

                playerJson.put("player_user_id", this.players.get(player).getId());
                playerJson.put("player_user_name", this.players.get(player).getName());
                playerJson.put("legions", legionArray);

                playerArray.put(playerJson);
            }

            teamJson.put("players", playerArray);
            teamArray.put(teamJson);
        }

        return json;
    }

    /**
     * Modifie la configuration de la partie sous forme de JSON.
     *
     * @param json JSON de la nouvelle configuration
     *
     * @throws JSONException Erreur JSON
     */
    public void setConfiguration(JSONObject json) throws JSONException
    {
        this.setName(
                json.getString("game_name")
        );

        this.game.setBoard(
                new Board(
                        json.getInt("game_board_size")
                )
        );

        this.game.setTurnDuration(
                json.getLong("game_turn_duration")
        );

        Team team;
        Player player;
        User user;
        Legion legion;
        JSONObject teamJson;
        JSONObject playerJson;
        JSONObject legionJson;

        this.players.clear();

        for (int iTeam = 0; iTeam < json.getJSONArray("teams").length(); iTeam++)
        {
            // Récupération du JSON
            teamJson = json.getJSONArray("teams").getJSONObject(iTeam);

            // Instanciation de l'équipe
            team = new Team();
            team.setNum(iTeam);

            // Ajout des joueurs
            for (int iPlayer = 0; iPlayer < teamJson.getJSONArray("players").length(); iPlayer++)
            {
                // Récupération du JSON
                playerJson = teamJson.getJSONArray("players").getJSONObject(iPlayer);

                // Instanciation du joueur
                player = new Player();
                player.setGame(this.game);
                player.setTeam(team);

                // Attribution du joueur à l'utilisateur
                if (playerJson.has("bot"))
                {
                    try
                    {
                        player.setBot(new Bot(player, Bot.Strategy.valueOf(playerJson.getString("bot"))));
                    }
                    catch (RuntimeException exception)
                    {
                        Logger.printError(exception);
                        player.setBot(new Bot(player, Bot.Strategy.distributed));
                    }
                    player.setConnected(false);
                    user = null;
                }
                else
                {
                    user = this.users.get(playerJson.getInt("player_user_id"));
                    player.setBot(new Bot(player, Bot.Strategy.distributed));
                }
                this.players.put(player, user);

                // Ajout des légions
                for (int iLegion = 0; iLegion < playerJson.getJSONArray("legions").length(); iLegion++)
                {
                    // Récupération du JSON
                    legionJson = playerJson.getJSONArray("legions").getJSONObject(iLegion);

                    // Instanciation de la légion
                    legion = new Legion(player);
                    legion.setColor(legionJson.getString("legion_color"));
                    legion.setShape(legionJson.getString("legion_shape"));
                    legion.setPosition(legionJson.getInt("legion_position"));
                    player.addLegion(legion);
                }
            }
        }
    }

    /**
     * Retourne l'état du plateau courant.
     *
     * @return JSON représentant le plateau
     *
     * @throws JSONException Erreur JSON
     */
    public JSONArray getBoard() throws JSONException
    {
        JSONObject cellJson;
        JSONArray cellArray = new JSONArray();

        for (Cell cell : this.game.getBoard().getCells().values())
        {
            if (cell.getPawn() != null)
            {
                cellJson = new JSONObject();
                cellJson.put("u", cell.getP().getX());
                cellJson.put("w", cell.getP().getY());
                cellJson.put("type", cell.getPawn().getClass().getSimpleName().toLowerCase());

                if (cell.getPawn() instanceof Soldier)
                {
                    cellJson.put("legion_armor", ((Soldier)cell.getPawn()).isArmored());
                    cellJson.put("legion_id", ((Soldier)cell.getPawn()).getLegion().getPosition());
                    cellJson.put("legion_color", cell.getPawn().getLegion().getColor());
                    cellJson.put("legion_shape", cell.getPawn().getLegion().getShape());
                }

                cellArray.put(cellJson);
            }
        }

        return cellArray;
    }

    /**
     * Modifie le plateau de la partie à partir d'un plateau stockée sous forme
     * de JSON. Le plateau est vidé puis rempli avec les pions spécifiés dans le
     * JSONArray donné.
     *
     * @param json JSON du tour courant
     *
     * @throws JSONException Erreur JSON
     */
    public void setBoard(JSONArray json) throws JSONException
    {
        Pawn pawn;
        ArrayList<Pawn> pawns = new ArrayList<>();

        for (int i = 0; i < json.length(); i++)
        {
            if (json.getJSONObject(i).has("type"))
            {
                // Identification du type du pion
                switch (json.getJSONObject(i).getString("type"))
                {
                    case "armor":
                        pawn = new Armor();
                        break;
                    case "laurel":
                        pawn = new Laurel();
                        break;
                    case "soldier":
                        pawn = new Soldier();
                        break;
                    case "wall":
                        pawn = new Wall();
                        break;
                    default:
                        throw new JSONException("Unknown soldier type");
                }

                // Positionnement du pion sur le plateau
                pawn.setCell(
                        this.game.getBoard().getCell(
                                new Point(
                                        json.getJSONObject(i).getInt("x"),
                                        json.getJSONObject(i).getInt("y")
                                )
                        )
                );

                // S'il s'agit d'un soldat, définition de sa légion et de son armure
                if (pawn instanceof Soldier)
                {
                    ((Soldier)pawn).setLegion(
                            this.game.getLegion(
                                    json.getJSONObject(i).getInt("legion_id")
                            )
                    );

                    ((Soldier)pawn).setArmored(
                            json.getJSONObject(i).getBoolean("legion_armor")
                    );
                }

                pawns.add(pawn);
            }
        }

        this.game.initBoard(pawns);
    }

    /**
     * Fait rejoindre un joueur à la partie.
     *
     * @param user Utilisateur ayant rejoint
     */
    public void joinPlayer(User user)
    {
        if (this.players.values().contains(user))
        {
            this.players.entrySet().stream().filter((entry) -> (entry.getValue() == user)).forEach((entry) ->
            {
                entry.getKey().setConnected(true);
            });
        }
    }

    /**
     * Fait quitter un joueur de la partie.
     *
     * @param user Utilisateur ayant quitté
     */
    public void leavePlayer(User user)
    {
        if (this.players.values().contains(user))
        {
            this.players.entrySet().stream().filter((entry) -> (entry.getValue() == user)).forEach((entry) ->
            {
                entry.getKey().setConnected(false);
            });
        }
    }

    /**
     * Fonction appelée à la fin d'un tour.
     */
    @Override
    public void onTurnEnd()
    {
        try
        {
            synchronized (this)
            {
                this.broadcast(
                        new GameTurn(this)
                );
            }
        }
        catch (CommandException | JSONException | SQLException exception)
        {
            Logger.printError(exception);
        }
    }

    /**
     * Envoie une commande à tous les utilisateurs de la salle.
     *
     * @param command Commande à envoyer
     *
     * @throws CommandException Erreur de commande
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public void broadcast(ServerCommand command) throws CommandException, JSONException, SQLException
    {
        for (User user : this.users.values())
        {
            Session session = Session.getSession(user);
            synchronized (session)
            {
                session.send(command);
            }
        }
    }
}
