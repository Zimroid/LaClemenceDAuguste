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

package octavio.server.db.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import octavio.server.db.entities.Room;
import octavio.server.db.entities.User;
import octavio.server.exception.CommandException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Gestionnaire des salles de jeu. Charge et sauvegarde les salles de jeu dans
 * la base de données, avec leur configuration et l'état courant du plateau.
 * Sauvegarde également les tours de jeu.
 *
 * @author Lzard
 */
public class RoomManager extends Manager
{
    /**
     * Récupère les informations d'une salle.
     */
    private static final String QUERY_ROOM
                                = "SELECT * FROM `room` WHERE `room_id` = ?";

    /**
     * Récupère tous les tours d'une partie.
     */
    private static final String QUERY_ALL_TURNS
                                = "SELECT * FROM `turn` WHERE `room_id` = ? ORDER BY `turn_id`";

    /**
     * Insère une nouvelle salle.
     */
    private static final String INSERT_ROOM
                                = "INSERT INTO `room` (`name`, `owner`) VALUES(?, ?)";

    /**
     * Insère un nouveau tour.
     */
    private static final String INSERT_TURN
                                = "INSERT INTO `turn` (`room_id`, `turn_id`, `command`) VALUES (?, ?, ?)";

    /**
     * Modifie les informations d'une salle.
     */
    private static final String UPDATE_ROOM_CONFIGURATION
                                = "UPDATE `room` SET `name` = ?, `owner` = ?, `configuration` = ? WHERE `room_id` = ?";

    /**
     * Modifie l'état du plateau de la salle.
     */
    private static final String UPDATE_ROOM_BOARD
                                = "UPDATE `room` SET `board` = ? WHERE `room_id` = ?";

    /**
     * Supprime une salle.
     */
    private static final String DELETE_ROOM
                                = "DELETE FROM `room` WHERE `room_id` = ?";

    /**
     * Initialise la connexion à la base de données. Appel le constructeur de la
     * classe Manager pour récupérer la connexion à la base de données.
     *
     * @param connection Connexion à la base de données
     */
    public RoomManager(Connection connection)
    {
        super(connection);
    }

    /**
     * Retourne la salle ayant l'identifiant donné.
     *
     * @param id Identifiant de la salle
     *
     * @return Salle correspondante
     *
     * @throws CommandException Salle inconnue
     * @throws SQLException     Erreur SQL
     * @throws JSONException    Erreur JSON
     */
    public Room getRoom(int id) throws CommandException, JSONException, SQLException
    {
        // Exécution de la requête
        PreparedStatement statement = this.query(RoomManager.QUERY_ROOM);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();

        // Instanciation de la salle
        if (set.next())
        {
            Room room = new Room(set.getInt("room_id"));
            room.setOwner((new UserManager(this.getConnection())).getUser(set.getInt("owner")));
            if (set.getString("configuration") != null) room.setConfiguration(new JSONObject(set.getString("configuration")));
            if (set.getString("board") != null) room.setBoard(new JSONArray(set.getString("board")));
            return room;
        }
        else
        {
            CommandException exception = new CommandException("Unknown room", "unknown_room");
            exception.setArgument("room_id", String.valueOf(id));
            throw exception;
        }
    }

    /**
     * Retourne la liste des tours de la salle donnée.
     *
     * @param room Salle concernée
     *
     * @return Liste des tours de la salle
     *
     * @throws SQLException  Erreur SQL
     * @throws JSONException Erreur JSON
     */
    public JSONArray getAllTurns(Room room) throws SQLException, JSONException
    {
        // Exécution de la requête
        PreparedStatement statement = this.query(RoomManager.QUERY_ALL_TURNS);
        statement.setInt(1, room.getId());
        ResultSet set = statement.executeQuery();

        // Construction de la liste des tours
        JSONArray turns = new JSONArray();
        while (set.next())
        {
            turns.put(new JSONObject(set.getString("command")));
        }

        // Retour de la liste
        return turns;
    }

    /**
     * Créé et insère une nouvelle salle dans la base de données.
     *
     * @param name  Nom de la salle
     * @param owner Propriétaire
     *
     * @return Salle créée
     *
     * @throws SQLException Erreur SQL
     */
    public Room insertRoom(String name, User owner) throws SQLException
    {
        // Exécution de la requête
        PreparedStatement statement = this.queryWithKeys(RoomManager.INSERT_ROOM);
        statement.setString(1, name);
        statement.setInt(2, owner.getId());
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        keys.next();

        // Instanciation de la nouvelle salle
        Room room = new Room(keys.getInt(1));
        room.setName(name);
        room.setOwner(owner);
        return room;
    }

    /**
     * Insère un nouveau tour à la salle.
     *
     * @param room Salle concernée
     * @param turn Tour à ajouter
     *
     * @throws SQLException
     */
    public void insertTurn(Room room, JSONObject turn) throws SQLException
    {
        // Exécution de la requête
        PreparedStatement statement = this.query(RoomManager.INSERT_TURN);
        statement.setInt(1, room.getId());
        statement.setInt(2, room.getTurnId());
        statement.setString(3, turn.toString());
        statement.executeUpdate();
    }

    /**
     * Modifie la configuration d'une salle de la base de données.
     *
     * @param room Salle concernée
     *
     * @throws JSONException Erreur JSON
     * @throws SQLException  Erreur SQL
     */
    public void updateRoomConfiguration(Room room) throws JSONException, SQLException
    {
        PreparedStatement statement = this.query(RoomManager.UPDATE_ROOM_CONFIGURATION);
        statement.setString(1, room.getName());
        statement.setInt(2, room.getOwner().getId());
        statement.setString(3, room.getConfiguration().toString());
        statement.setInt(4, room.getId());
        statement.executeUpdate();
    }

    /**
     * Modifie le plateau d'une salle de la base de données.
     *
     * @param room Salle concernée
     *
     * @throws JSONException Erreur JSON
     * @throws SQLException  Erreur SQL
     */
    public void updateRoomBoard(Room room) throws JSONException, SQLException
    {
        PreparedStatement statement = this.query(RoomManager.UPDATE_ROOM_BOARD);
        statement.setString(1, room.getBoard().toString());
        statement.setInt(2, room.getId());
        statement.executeUpdate();
    }

    /**
     * Supprime une salle de la base de données.
     *
     * @param room Salle à supprimer
     *
     * @throws SQLException Erreur SQL
     */
    public void deleteRoom(Room room) throws SQLException
    {
        PreparedStatement statement = this.query(RoomManager.DELETE_ROOM);
        statement.setInt(1, room.getId());
        statement.executeUpdate();
    }
}
