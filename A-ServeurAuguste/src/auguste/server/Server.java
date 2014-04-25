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

import auguste.server.command.client.QueryUsers;
import auguste.server.command.ClientCommand;
import auguste.server.command.server.ListGames;
import auguste.server.command.server.RoomClose;
import auguste.server.exception.AuthentificationException;
import auguste.server.exception.CommandException;
import auguste.server.exception.InexistantRoomException;
import auguste.server.exception.NotInThisRoomException;
import auguste.server.exception.RuleException;
import auguste.server.util.Configuration;
import auguste.server.util.Log;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe du serveur. Hérite de la classe WebSocketServer, permettant de lancer
 * le serveur de WebSocket, et d'effectuer une action lors d'un évènement lié
 * à une WebSocket.
 * 
 * L'instance du serveur gère également l'authentification des utilisateurs et
 * leur appartenance aux salles. Lorsqu'une modification concernant la liste des
 * utilisateurs authentifiés ou la liste des salons créés, les utilisateurs
 * regardant ces listes sont notifiés.
 * 
 * Tout message envoyé par le serveur doit être envoyé via cette instance.
 * 
 * @author Lzard / Zim
 */
public class Server extends WebSocketServer 
{
    // Création du singleton
    private static final Server INSTANCE = new Server(
        new InetSocketAddress(
                Configuration.get("server_host_name"), 
                Configuration.getInt("server_host_port")
        )
    );
    
    /**
     * Retourne l'instance du serveur.
     * @return Instance du serveur
     */
    public static Server getInstance()
    {
        return Server.INSTANCE;
    }
    
    // Liste des clients connectés
    private final HashMap<WebSocket, User> clients = new HashMap<>();
    
    // Liste des utilisateurs authentifiés
    private final HashMap<Integer, User> users = new HashMap<>();
    
    // Liste des salons existantes
    private final HashMap<Integer, Room> rooms = new HashMap<>();
    
    // Listes des utilisateurs regardant la liste utilisateurs ou des salles disponibles
    private final ArrayList<User> usersWatchers = new ArrayList<>();
    private final ArrayList<User> roomsWatchers = new ArrayList<>();
    
    /**
     * Instanciation du serveur. Effectue un simple appel au constructeur de la
     * classe mère en utilisant l'adresse donnée et signal le lancement du
     * serveur.
     * @param address Adresse à laquelle le serveur est attachée
     */
    private Server(InetSocketAddress address)
    {
        // Appel à la fonction mère
        super(address);
        
        // Confirmation de la connexion
        Log.out("Server started on " + address);
    }
    
    /**
     * Méthode appelée lors de la connexion d'un client. Ajoute le client à la
     * liste des clients connectés.
     * @param socket    WebSocket créée
     * @param handshake Données handshake générées
     */
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake)
    {
        // Signalisation
        Log.out(socket.getRemoteSocketAddress() + ": Open ");
        
        // Ajout du client à la liste
        this.clients.put(socket, null);
    }

    /**
     * Méthode appelée lors de la déconnexion d'un client. Désauthentifie
     * l'utilisateur si nécessaire, et supprime le client de la liste des
     * clients connectés.
     * @param socket    WebSocket fermée
     * @param code      Code décrivant la raison de la fermeture
     * @param reason    Raison de la fermeture
     * @param byRequest Fermeture demandée par le client ?
     */
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean byRequest)
    {
        // Signalisation
        Log.out(socket.getRemoteSocketAddress() + ": Close " +
                (byRequest ? " by client" : "") +
                (reason.length() > 0 ? ": \"" + reason + "\"" : "") +
                " (" + code + ")");
        
        // Désauthentification de l'utilisateur si nécessaire et suppression du client
        if (this.clients.get(socket) != null) this.logOut(this.clients.get(socket));
        this.clients.remove(socket);
    }

    /**
     * Méthode appelée lors de la réception d'un message du client. Procède à
     * l'identification du client et à la lecture puis au traitement de la
     * commande reçue.
     * @param socket  WebSocket concernée
     * @param message Message reçu
     */
    @Override
    public void onMessage(WebSocket socket, String message)
    {
        // Signalisation
        Log.out(socket.getRemoteSocketAddress() + ": Received " + message);
        
        // Lecture de la commande
        try
        {
            // Lecture du JSON
            JSONObject json = new JSONObject(message);

            // Définition de la commande
            ClientCommand command = ClientCommand.get(json.getString("command"));
            command.setSocket(socket);
            command.setJSON(json);

            // Vérification de l'authentification et de la salon
            if (command.checkAuth()) command.setUser(this.clients.get(socket));
            if (command.checkRoom()) command.setRoom(this.rooms.get(json.getInt("room_id")));

            // Exécution
            command.execute();
        }
        catch (CommandException e)
        {
            // Commande inconnue
            Log.out("Unknown command");
            Log.debug(e);
            ClientCommand.sendError(socket, "unknown_command");
        }
        catch (AuthentificationException e)
        {
            // Commande nécessitant d'être authentifié
            Log.out("Must be logged");
            Log.debug(e);
            ClientCommand.sendError(socket, "must_be_logged");
        }
        catch (InexistantRoomException e)
        {
            // Salon inexistant
            Log.out("Inexistant room");
            Log.debug(e);
            ClientCommand.sendError(socket, "inexistant_room");
        }
        catch (NotInThisRoomException e)
        {
            // Utilisateur absent du salon
            Log.out("Not in this room");
            Log.debug(e);
            ClientCommand.sendError(socket, "not_in_this_room");
        }
        catch (RuleException e)
        {
            // Action contre les règles
            Log.out("Unauthorized move");
            Log.debug(e);
            ClientCommand.sendError(socket, "rule_error");
        }
        catch (JSONException e)
        {
            // JSON reçu éronné ou incomplet
            Log.error("JSON error");
            Log.debug(e);
            ClientCommand.sendError(socket, "json_error");
        }
        catch (SQLException e)
        {
            // Erreur SQL
            Log.error("SQL error");
            Log.debug(e);
            ClientCommand.sendError(socket, "server_error");
        }
        catch (Exception e)
        {
            // Autre errur
            Log.error("Error");
            Log.debug(e);
            ClientCommand.sendError(socket, "server_error");
        }
    }

    /**
     * Méthode appelée lorsqu'une erreur est survenue.
     * @param socket WebSocket concernée, null en cas d'erreur interne au serveur
     * @param e      Exception émise
     */
    @Override
    public void onError(WebSocket socket, Exception e)
    {
        // Signalisation
        if (socket != null) Log.error(socket.getRemoteSocketAddress() + ": Error");
        else                Log.error("Internal error");
        Log.debug(e);
    }
    
    /**
     * Envoi d'un message à un utilisateur.
     * @param socket  Socket destinataire
     * @param message Message à envoyer
     */
    public void send(WebSocket socket, String message)
    {
        // Envoi du message
        Log.out(socket.getRemoteSocketAddress() + ": Send " + message);
        socket.send(message);
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs authentifiés.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        for (User user : this.users.values()) this.send(user.getSocket(), message);
    }
    
    /**
     * Retourne la liste des utilisateurs authentifiés.
     * @return Collection d'utilisateurs authentifiés
     */
    public Collection<User> getUsers()
    {
        return this.users.values();
    }
    
    /**
     * Ajoute un utilisateur à la liste des utilisateurs authentifiés et
     * l'attribue à la socket correspondante.
     * @param socket Socket de l'utilisateur
     * @param user   Utilisateur à authentifier
     */
    public void logIn(WebSocket socket, User user)
    {
        // Liaison de l'utilisateur et de la socket
        user.setSocket(socket);
        this.clients.put(socket, user);
        
        // Authentificaction
        this.users.put(user.getId(), user);
        
        // Notification
        this.updateUsersWatchers();
    }
    
    /**
     * Supprime un utilisateur de la liste des utilisateurs authentifiés et des
     * listes des utilisateurs de chaque salon. La socket n'est plus liée à cet
     * utilisateur.
     * @param user Utilisateur à supprimer
     */
    public void logOut(User user)
    {
        // Retrait de l'utilisateur des salons dont il fait partie
        for (Room room : ((HashMap<Integer, Room>)user.getRooms().clone()).values()) this.leaveRoom(user, room);

        // Retrait de l'utilisateur de la liste des utilisateurs authentifiés
        if (this.users.remove(user.getId()) != null) ClientCommand.sendError(user.getSocket(), "logged_out");
        
        // Désattribution de l'utilisateur à la socket et suppression des listes
        this.clients.put(user.getSocket(), null);
        this.getUsersWatchers().remove(user);
        this.getRoomsWatchers().remove(user);
        
        // Notification
        this.updateUsersWatchers();
    }
    
    /**
     * Retourne la liste des salons crées.
     * @return Collection des salons
     */
    public Collection<Room> getRooms()
    {
        return this.rooms.values();
    }
    
    /**
     * Retourne un salon via son identifiant.
     * @param id Identifiant du salon
     * @return Salon correspondant à l'identifiant
     * @throws InexistantRoomException Identifiant inexistant
     */
    public Room getRoom(int id) throws InexistantRoomException
    {
        if (this.rooms.containsKey(id)) return this.rooms.get(id);
        else throw new InexistantRoomException(id);
    }
    
    /**
     * Instancie un nouveau salon. Recherche un identifiant disponible et
     * l'attribue à ce nouveau salon.
     * @param name Nom du salon
     * @return Salon instancié
     */
    public Room createRoom(String name)
    {
        int roomId; // Identifiant du salon
        Room room;  // Nouveau salon
        
        synchronized (this.rooms)
        {
            // Recherche d'un identifiant disponible
            roomId = 1;
            while (this.rooms.containsKey(roomId)) roomId++;

            // Instanciation du salon
            room = new Room(roomId, name);
            this.rooms.put(roomId, room);
        }
        
        // Notification
        this.updateRoomsWatchers();
        
        return room;
    }
    
    /**
     * Joint un utilisateur à un salon. Notifie les utilisateurs du salon et
     * les utilisateurs regardant la liste des salons.
     * @param user Utilisateur à joindre
     * @param room Salon cible
     */
    public void joinRoom(User user, Room room)
    {
        // Laison de l'utilisateur et du salon
        user.getRooms().put(room.getId(), room);
        room.getUsers().put(user.getId(), user);
        
        // Notification
        room.updateConfiguration();
        room.updateUsers();
        this.updateRoomsWatchers();
    }
    
    /**
     * Retire un utilisateur d'un salon. Si cet utilisateur était le dernier
     * utilisateur du salon, ce dernier est supprimé. Sinon, les utilisateurs
     * regardant la liste des salons sont notifiés, et si cet utilisateur
     * était le propriétaire du salon, un nouveau propriétaire est élu.
     * @param user Utilisateur à retirer
     * @param room Salon cible
     */
    public void leaveRoom(User user, Room room)
    {
        // Suppression des liens entre l'utilisateur et le salon
        user.getRooms().remove(room.getId());
        room.getUsers().remove(user.getId());
        room.updateUsers();
        
        // Suppression du salon si nécessaire
        if (room.getUsers().isEmpty()) this.closeRoom(room);
        else
        {
            // Changement de propriétaire et notification
            if (room.isOwner(user)) room.setRandomOwner();
            this.updateRoomsWatchers();
        }
    }
    
    /**
     * Ferme un salon. Le supprime de la liste des salons du serveur, règle son
     * état à Closing, indique que le salon est fermé aux utilisateurs et
     * supprime le salon des listes des salons des utilisateurs.
     * @param room Salon à fermer
     */
    public void closeRoom(Room room)
    {
        // Fermeture et suppression du salon
        this.rooms.remove(room.getId());
        room.setState(Room.State.CLOSING);
        this.updateRoomsWatchers();
        
        // Signalisation et retrait des utilisateurs du salon
        room.broadcast((new RoomClose(room)).toString());
        for (User user : ((HashMap<Integer, User>)room.getUsers().clone()).values()) this.leaveRoom(user, room);
    }
    
    /**
     * Retourne la liste des utilisateurs regardant la liste des utilisateurs
     * authentifiés.
     * @return Liste des utilisateurs regardant la liste des utilisateurs
     */
    public ArrayList<User> getUsersWatchers()
    {
        return this.usersWatchers;
    }
    
    /**
     * Met à jour les utilisateurs regardant la liste des utilisateurs
     * authentifiés.
     */
    public void updateUsersWatchers()
    {
        for (User user : this.getUsersWatchers()) this.send(user.getSocket(), (new QueryUsers()).toString());
        this.getUsersWatchers().clear();
    }
    
    /**
     * Retourne la liste des utilisateurs regardant la liste des parties.
     * @return Liste des utilisateurs regardant les parties
     */
    public ArrayList<User> getRoomsWatchers()
    {
        return this.roomsWatchers;
    }
    
    /**
     * Met à jour les utilisateurs regardant la liste des parties.
     */
    public void updateRoomsWatchers()
    {
        for (User user : this.getRoomsWatchers()) this.send(user.getSocket(), (new ListGames()).toString());
        this.getRoomsWatchers().clear();
    }
    
}
