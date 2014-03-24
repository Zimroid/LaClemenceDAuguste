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

import auguste.server.command.client.ClientCommand;
import auguste.server.exception.AuthentificationException;
import auguste.server.exception.CommandException;
import auguste.server.exception.RoomException;
import auguste.server.exception.RuleException;
import auguste.server.util.Configuration;
import auguste.server.util.Log;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe du serveur. Hérite de la classe WebSocketServer, permettant de lancer
 * le serveur de WebSocket, et d'effectuer une action lors d'un évènement lié
 * à une WebSocket. Gère également l'authentification des utilisateurs et les
 * salles de jeu.
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
    
    // Liste des utilisateurs authentifiés
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    // Liste des salles disponibles
    private final HashMap<Integer, Room> rooms = new HashMap<>();
    
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
        Log.out("Server initialized on " + address);
    }
    
    /**
     * Méthode appelée lors de la connexion d'un client.
     * @param socket    WebSocket concernée
     * @param handshake Données handshake générées
     */
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake)
    {
        // Signalisation
        Log.out(socket.getRemoteSocketAddress() + ": Connection");
    }

    /**
     * Méthode appelée lors de la déconnexion d'un client.
     * @param socket    WebSocket concernée
     * @param code      Code décrivant la raison de la fermeture
     * @param reason    Raison de la fermeture
     * @param byRequest Fermeture demandée par le client ?
     */
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean byRequest)
    {
        // Signalisation
        Log.out(socket.getRemoteSocketAddress() + ": Disconnection " +
                (byRequest ? "by request " : "") +
                (reason.length() > 0 ? "\"" + reason + "\" " : "") +
                "(" + code + ")");
        
        // Suppression de la socket de la liste des utilisateurs authentifiés
        if (this.users.containsKey(socket)) this.logOut(this.users.get(socket));
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
        Log.out(socket.getRemoteSocketAddress() + ": Reçu \"" + message + "\"");

        // Identification de l'utilisateur
        User user = this.users.get(socket);
        
        // Lecture de la commande
        try
        {
            try
            {
                // Lecture du JSON
                JSONObject json = new JSONObject(message);

                // Définition de la commande
                ClientCommand command = ClientCommand.get(json.getString("command"));
                command.setSocket(socket);
                command.setUser(user);
                command.setJSON(json);
                
                // Définition de la salle concernée
                if (json.has("game_id"))
                {
                    int roomId = json.getInt("room_id");
                    if (this.rooms.containsKey(json.getInt("game_id")))
                    {
                        Room room = this.rooms.get(roomId);
                        if (room.isInRoom(user))
                        {
                            command.setRoom(
                                    this.rooms.get(roomId)
                            );
                        }
                        else throw new RoomException(roomId, false);
                    }
                    else throw new RoomException(roomId, true);
                }
                else command.setRoom(null);
                
                // Exécution
                command.execute();
            }
            catch (CommandException e)
            {
                // Commande inconnue
                Log.out("Unknown command");
                Log.debug(e.toString());
                ClientCommand.sendError(socket, "unknown_command");
            }
            catch (AuthentificationException e)
            {
                // Tentative d'utiliser une commande sans être authentifié
                Log.out("Must be authentified");
                Log.debug(e.toString());
                ClientCommand.sendError(socket, "must_be_logged");
            }
            catch (RoomException e)
            {
                // Salle inexistante
                Log.out("Inexistant room");
                Log.debug(e.toString());
                if (e.inexistant()) ClientCommand.sendError(socket, "inexistant_room");
                else                ClientCommand.sendError(socket, "not_in_this_room");
            }
            catch (RuleException e)
            {
                // Action contre les règles
                Log.out("Unauthorized move");
                Log.debug(e.toString());
                ClientCommand.sendError(socket, "rule_error");
            }
            catch (JSONException e)
            {
                // JSON reçu éronné
                Log.error("JSON error");
                Log.debug(e.toString());
                ClientCommand.sendError(socket, "json_error");
            }
            catch (SQLException e)
            {
                // Erreur SQL
                Log.error("SQL error");
                Log.debug(e.toString());
                ClientCommand.sendError(socket, "server_error");
            }
        }
        catch (JSONException e)
        {
            // Erreur lors de la création de JSON
            Log.error("JSON error");
            Log.debug(e.toString());
        }
    }

    /**
     * Méthode appelée lorsqu'une erreur est survenue.
     * @param socket WebSocket concernée, null en cas d'erreur serveur
     * @param e      Exception émise
     */
    @Override
    public void onError(WebSocket socket, Exception e)
    {
        // Signalisation
        if (socket != null) Log.error(socket.getRemoteSocketAddress() + ": Error");
        else                Log.error("Error");
        Log.debug(e.toString());
    }
    
    /**
     * Envoi d'un message à tous les utilisateurs authentifiés.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        Log.debug("Broadcast: " + message);
        for (WebSocket socket : this.users.keySet())
        {
            socket.send(message);
        }
    }
    
    /**
     * Ajoute un utilisateur à la liste des utilisateurs authentifiés.
     * @param socket Socket de l'utilisateur
     * @param user   Utilisateur à authentifier
     */
    public void logIn(WebSocket socket, User user)
    {
        synchronized (this.users)
        {
            this.users.put(socket, user);
        }
    }
    
    /**
     * Supprime un utilisateur de la liste des utilisateurs authentifiés et des
     * utilisateurs de chaque salle.
     * @param user Utilisateur à supprimer
     */
    public void logOut(User user)
    {
        synchronized (this.users)
        {
            for (Room room : this.getRoomList())
            {
                if (room.isInRoom(user))
                {
                    if (room.isOwner(user)) this.closeRoom(room);
                    else                    room.removeUser(user);
                }
            }
            this.users.values().removeAll(Collections.singleton(user));
        }
    }
    
    /**
     * Retourne la liste des utilisateurs authentifiés.
     * @return Collection d'utilisateurs authentifiés
     */
    public Collection<User> getUserSet()
    {
        return this.users.values();
    }
    
    /**
     * Créée une nouvelle salle.
     * @param name Nom de la salle
     * @return Salle créée
     */
    public Room createRoom(String name)
    {
        int id;     // ID de la nouvelle salle
        Room room; // Nouvelle salle
        
        synchronized (this.rooms)
        {
            // Recherche d'un ID disponible
            id = 1;
            while (this.rooms.containsKey(id)) id++;

            // Création de la salle
            room = new Room(id, name);
            this.rooms.put(id, room);
        }
        
        // Retour de son ID
        return room;
    }
    
    /**
     * Supprime une salle en cours.
     * @param room
     */
    public void closeRoom(Room room)
    {
        synchronized (this.rooms)
        {
            try
            {
                room.close();
                this.rooms.values().removeAll(Collections.singleton(room));
            }
            catch (JSONException e)
            {
                // Erreur de création de JSON
                Log.error("JSON Error");
                Log.debug(e.toString());
            }
        }
    }
    
    /**
     * Retourne une salle via son ID.
     * @param id ID de la salle
     * @return Salle correspondante
     * @throws auguste.server.exception.RoomException ID de salle inexistante
     */
    public Room getRoom(int id) throws RoomException
    {
        if (this.rooms.containsKey(id)) return this.rooms.get(id);
        else                            throw new RoomException(id, true);
    }
    
    /**
     * Retourne la liste des salles crées.
     * @return Collection des salles
     */
    public Collection<Room> getRoomList()
    {
        return this.rooms.values();
    }
    
}
