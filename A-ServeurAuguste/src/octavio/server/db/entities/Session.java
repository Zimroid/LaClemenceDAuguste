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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.ServerCommand;
import octavio.server.commands.server.ListGames;
import octavio.server.db.managers.SessionManager;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;
import org.java_websocket.WebSocket;
import org.json.JSONException;

/**
 * Classe gérant une session d'un utilisateur. Elle référence les salles
 * auxquelles il participe et ses différents clients connectés. Elle gère
 * l'exécutation des commandes de l'utilisateur et envoi les messages à tous
 * ses clients.
 *
 * @author Lzard
 */
public final class Session
{
    /**
     * Liste des sessions en cours par utilisateur.
     */
    private static final HashMap<User, Session> sessions = new HashMap<>();

    /**
     * Liste des sessions regardant les salles.
     */
    private static final ArrayList<Session> roomWatchers = new ArrayList<>();

    /**
     * Récupération d'une session d'un utilisateur précis. Soit la session
     * existe, auquel cas elle est retournée, ou n'existe pas, et est créée puis
     * retournée.
     *
     * @param user Utilisateur à rechercher.
     *
     * @return Session de l'utilisateur
     *
     * @throws CommandException Utilisateur inconnu
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL lors de l'instanciation de la session
     */
    public static Session getSession(User user) throws CommandException, JSONException, SQLException
    {
        synchronized (Session.sessions)
        {
            if (Session.sessions.containsKey(user))
            {
                return Session.sessions.get(user);
            }
            else
            {
                // Instanciation d'une nouvelle session
                Session session = new Session(user);
                try (Connection connection = Configuration.getDbConnection())
                {
                    (new SessionManager(connection)).fill(session);
                    connection.commit();
                }

                // Ajout de cette session aux sessions en cours et retour
                Session.sessions.put(user, session);
                return session;
            }
        }
    }

    /**
     * Retourne le nombre de sessions en cours.
     *
     * @return Nombre de sessions en cours
     */
    public static int getSessionCount()
    {
        synchronized (Session.sessions)
        {
            return Session.sessions.size();
        }
    }

    public static void notifyRoomListeners()
    {
        synchronized (Session.roomWatchers)
        {
            Session.roomWatchers.stream().forEach((session) ->
            {
                session.send(new ListGames());
            });
            Session.roomWatchers.clear();
        }
    }

    /**
     * Utilisateur de la session.
     */
    private final User user;

    /**
     * Liste des sockets des clients de l'utilisateur.
     */
    private final ArrayList<WebSocket> sockets = new ArrayList<>();

    /**
     * Liste des salles de l'utilisateur.
     */
    private final ArrayList<Room> rooms = new ArrayList<>();

    /**
     * Création d'une session pour l'utilisateur donné. La session est ajoutée à
     * la liste des sessions en cours.
     *
     * @param user Utilisateur de la session
     */
    private Session(User user)
    {
        this.user = user;
    }

    /**
     * Fermeture de la session. Quitte toutes les salles en cours, ferme toutes
     * les WebSocket et retire la session.
     */
    public void close()
    {
        synchronized (Session.sessions)
        {
            ((ArrayList<WebSocket>)this.sockets.clone()).stream().forEach((socket) ->
            {
                this.removeSocket(socket);
            });
            ((ArrayList<Room>)this.rooms.clone()).stream().forEach((room) ->
            {
                try
                {
                    this.leaveRoom(room);
                }
                catch (SQLException exception)
                {
                    Logger.printError(exception);
                }
            });
            Session.sessions.remove(this.user);
        }
    }

    /**
     * Retourne l'utilisateur de la session.
     *
     * @return Utilisateur de la session
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * Ajoute une socket a l'utilisateur.
     *
     * @param socket Socket à ajouter
     */
    public void addSocket(WebSocket socket)
    {
        this.sockets.add(socket);
    }

    /**
     * Indique qu'il faut informer cette session en cas de modification des
     * salles.
     */
    public void listenRoomUpdate()
    {
        synchronized (Session.roomWatchers)
        {
            Session.roomWatchers.add(this);
        }
    }

    /**
     * Retire une socket de l'utilisateur. S'il s'agissait de la dernière socket
     * de la session, la session est fermée.
     *
     * @param socket Socket à retirer
     */
    public void removeSocket(WebSocket socket)
    {
        this.sockets.remove(socket);
        if (this.sockets.isEmpty())
        {
            this.close();
        }
    }

    /**
     * Retourne la liste des salles dans lesquelles l'utilisateur est présent.
     *
     * @return Liste des salles de l'utilisateur
     */
    public ArrayList<Room> getRooms()
    {
        return this.rooms;
    }

    /**
     * Ajoute une salle aux salles de l'utilisateur.
     *
     * @param room Salle à ajouter
     *
     * @throws SQLException Erreur SQL
     */
    public void joinRoom(Room room) throws SQLException
    {
        try (Connection connection = Configuration.getDbConnection())
        {
            (new SessionManager(connection)).joinRoom(this, room);
            room.joinPlayer(this.user);
            room.addUser(this.user);
            this.rooms.add(room);

            connection.commit();
        }
    }

    /**
     * Retire une salle des salles de l'utilisateur.
     *
     * @param room Salle à retirer
     *
     * @throws SQLException Erreur SQL
     */
    public void leaveRoom(Room room) throws SQLException
    {
        try (Connection connection = Configuration.getDbConnection())
        {
            (new SessionManager(connection)).leaveRoom(this, room);
            room.leavePlayer(this.user);
            room.removeUser(this.user);
            this.rooms.remove(room);

            connection.commit();
        }
    }

    /**
     * Réception d'un message depuis un des clients de l'utilisateur.
     *
     * @param command Commande reçu
     *
     * @throws CommandException Erreur de commande
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public void receive(ClientCommand command) throws CommandException, JSONException, SQLException
    {
        command.setUser(this.user);
        command.execute();
    }

    /**
     * Envoi d'un message à tous les clients de l'utilisateur.
     *
     * @param command Commande à envoyer
     */
    public void send(ServerCommand command)
    {
        for (WebSocket socket : this.sockets)
        {
            socket.send(
                    command.getMessage()
            );
        }
    }
}
