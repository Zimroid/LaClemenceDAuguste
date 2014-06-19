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
import octavio.server.db.entities.Session;
import octavio.server.exception.CommandException;
import org.json.JSONException;

/**
 * Gestionnaire des sessions utilisateurs. Charge et modifie les salles
 * auxquelles l'utilisateur de la session appartient. Peut supprimer toutes les
 * appartenances d'un utilisateur.
 *
 * @author Lzard
 */
public class SessionManager extends Manager
{
    /**
     * Requête des salles auxquelles un utilisateur appartient.
     */
    private static final String QUERY_SESSION
                                = "SELECT `room_id` FROM `session` WHERE `user_id` = ?";

    /**
     * Requête de liaison d'un utilisateur avec une salle.
     */
    private static final String INSERT_SESSION
                                = "INSERT INTO `session` (`user_id`, `room_id`) VALUES (?, ?)";

    /**
     * Requête de retrait d'un utilisateur d'une salle.
     */
    private static final String DELETE_SESSION
                                = "DELETE FROM `session` WHERE `user_id` = ? AND `room_id` = ?";

    /**
     * Requête de retrait d'un utilisateur de toutes les salles.
     */
    private static final String DELETE_SESSION_BY_USER
                                = "DELETE FROM `session` WHERE `user_id` = ?";

    /**
     * Initialise la connexion à la base de données. Appel le constructeur de la
     * classe Manager pour récupérer la connexion à la base de données.
     *
     * @param connection Connexion à la base de données
     */
    public SessionManager(Connection connection)
    {
        super(connection);
    }

    /**
     * Remplit la liste des salles de la session fournie en paramètre.
     *
     * @param session Session à remplir
     *
     * @throws CommandException Salle inconnue
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public void fill(Session session) throws CommandException, JSONException, SQLException
    {
        // Exécution de la requête
        PreparedStatement statement = this.query(SessionManager.QUERY_SESSION);
        statement.setInt(1, session.getUser().getId());
        ResultSet set = statement.executeQuery();

        // Ajout des salles à la session
        while (set.next())
        {
            //session.joinRoom(Room.getRoom(set.getInt(1)));
        }
    }

    /**
     * Enregistre une liaison entre la session et la salle spécifiées.
     *
     * @param session Session concernée
     * @param room    Salle rejoint
     *
     * @throws SQLException Erreur SQL
     */
    public void joinRoom(Session session, Room room) throws SQLException
    {
        PreparedStatement statement = this.query(SessionManager.INSERT_SESSION);
        statement.setInt(1, session.getUser().getId());
        statement.setInt(2, room.getId());
        statement.executeUpdate();
    }

    /**
     * Supprime une liaison entre la session et la salle spécifiées.
     *
     * @param session Session concernée
     * @param room    Salle quittée
     *
     * @throws SQLException Erreur SQL
     */
    public void leaveRoom(Session session, Room room) throws SQLException
    {
        PreparedStatement statement = this.query(SessionManager.DELETE_SESSION);
        statement.setInt(1, session.getUser().getId());
        statement.setInt(2, room.getId());
        statement.executeUpdate();
    }

    /**
     * Supprime toutes les liaisons de la session spécifiée.
     *
     * @param session Session terminée
     *
     * @throws SQLException Erreur SQL
     */
    public void leave(Session session) throws SQLException
    {
        PreparedStatement statement = this.query(SessionManager.DELETE_SESSION_BY_USER);
        statement.setInt(1, session.getUser().getId());
        statement.executeUpdate();
    }
}
