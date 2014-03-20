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
import auguste.server.command.server.MessageError;
import auguste.engine.entity.Game;
import auguste.engine.entity.Player;
import auguste.server.exception.RuleException;
import auguste.server.exception.UnknownCommandException;
import auguste.server.util.Log;
import auguste.server.util.Configuration;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe du serveur. Gère les interactions avec les différents clients
 * connectés via les WebSockets.
 * @author Lzard / Zim
 */
public class Server extends WebSocketServer 
{
    // Singleton
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
    
    // Liste des joueurs connectés
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    // Liste des parties en cours de création
    private final HashMap<String, Game> games = new HashMap<>();
    
    /**
     * Instanciation du serveur. Effectue un simple appel au constructeur de la
     * classe mère en utilisant l'adresse donnée.
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
     * @param socket WebSocket concernée
     * @param handshake Données handshake générées
     */
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake)
    {
        // Signalisation
        Log.out("Connection with " + socket.getRemoteSocketAddress());
        
        // Création de l'utilisateur anonyme et ajout dans la liste
        User newUser = new User(User.DEFAULT_ID, User.DEFAULT_LOGIN, User.DEFAULT_PASSWORD);
        this.users.put(socket, newUser);
    }

    /**
     * Méthode appelée lors de la déconnexion d'un client.
     * @param socket WebSocket concernée
     * @param code Code décrivant la raison de la fermeture
     * @param reason Raison de la fermeture
     * @param byRemoteHost Fermeture provoquée par le client ?
     */
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean byRemoteHost)
    {
        // Signalisation
        Log.out("Disconnected from " + socket.getRemoteSocketAddress() + " (" + code + ": " + reason + ")");
        
        // Suppression de la liste des joueurs connectés
        this.users.remove(socket);
    }

    /**
     * Méthode appelée lors de la réception d'un message du client.
     * @param socket WebSocket concernée
     * @param content Contenu du message
     */
    @Override
    public void onMessage(WebSocket socket, String content)
    {
        // Signalisation
        Log.debug("Received from " + socket.getRemoteSocketAddress() + ": \"" + content + "\"");
        
        // Lecture de la commande
        try
        {
            try
            {
                // Instanciation et signalisation de la commande
                JSONObject json = new JSONObject(content);
                User user = this.users.get(socket);
                System.out.println("Identified command: " + json.getString("command"));

                // Définition de la commande
                ClientCommand command = ClientCommand.get(json.getString("command"));

                // Exécution de la commande
                command.setJSON(json);
                command.setUser(user);
                command.setSocket(socket);
                command.execute();
            }
            catch (UnknownCommandException ex)
            {
                // Reçu une commande inconnue
                Log.debug("Unknown command: " + ex);
                socket.send((new MessageError("unknown_command")).toString());
            }
            catch (RuleException ex)
            {
                // Reçu une action qui enfreint les règles
                Log.debug("Unauthorized move: " + ex);
                socket.send((new MessageError("rule_error")).toString());
            }
        }
        catch (JSONException ex)
        {
            // JSON reçu faux ou illisible
            Log.debug("JSON error: " + ex);
        }
        catch (SQLException ex)
        {
            // Erreur de communication avec la base de données
            Log.debug("SQL error: " + ex);
        }
    }

    /**
     * Méthode appelée lorsqu'une erreur est survenue.
     * @param socket WebSocket concernée
     * @param ex Exception émise
     */
    @Override
    public void onError(WebSocket socket, Exception ex)
    {
        // Signalisation
        if (socket != null) Log.error("Error from " + socket.getRemoteSocketAddress() + ": " + ex);
        else                Log.error("Error:" + ex);
    }
    
    /**
     * Méthode pour broadcaster un message à tous les utilisateurs.
     * @param message Message à envoyer
     */
    public void broadcast(String message)
    {
        this.broadcast(this.connections(), message);
    }
    
    /**
     * Méthode pour broadcaster un message à une liste d'utilisateurs.
     * @param targets Liste des sockets cibles
     * @param message Message à envoyer
     */
    public void broadcast(Iterable<WebSocket> targets, String message)
    {
        // Signalisation
        Log.out("Broadcast: \"" + message + "\"");
        
        // Envoi du message
        for (WebSocket socket : targets)
        {
            socket.send(message);
        }
    }
    
    /**
     * Retourne la liste des joueurs actuellement connectés.
     * @return HashMap des joueurs connectés
     */
    public HashMap<WebSocket, User> getUsers()
    {
        return this.users;
    }
    
    /**
     * Retourne la liste des parties en cours.
     * @return HashMap des parties en cours
     */
    public HashMap<String, Game> getGames()
    {
        return this.games;
    }
    
}
