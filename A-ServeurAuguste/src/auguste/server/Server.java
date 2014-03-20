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
    
    // Liste des utilisateurs connectés
    private final HashMap<WebSocket, User> users = new HashMap<>();
    
    // Liste des utilisateurs identifiés
    private final HashMap<Integer, User> loggedUsers = new HashMap<>();
    
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
     * @param socket    WebSocket concernée
     * @param handshake Données handshake générées
     */
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake)
    {
        // Signalisation
        Log.out("Connection with " + socket.getRemoteSocketAddress());
        
        // Création de l'utilisateur anonyme et ajout dans la liste
        this.users.put(socket, new User(socket));
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
        Log.out("Disconnected from " + socket.getRemoteSocketAddress() + " " +
                (byRequest ? "by request" : "") +
                " (" + code + ": " + reason + ")");
        
        // Suppression de la liste des joueurs connectés
        this.users.remove(socket);
    }

    /**
     * Méthode appelée lors de la réception d'un message du client.
     * @param socket  WebSocket concernée
     * @param content Contenu du message
     */
    @Override
    public void onMessage(WebSocket socket, String content)
    {
        // Signalisation
        Log.out("Received from " + socket.getRemoteSocketAddress() + ": \"" + content + "\"");
        
        // Lecture de la commande
        try
        {
            try
            {
                // Instanciation et signalisation de la commande
                User user = this.users.get(socket);
                JSONObject json = new JSONObject(content);
                Log.out("Identified command: " + json.getString("command"));

                // Définition de la commande
                ClientCommand command = ClientCommand.get(json.getString("command"));
                command.setSocket(socket);
                command.setUser(user);
                command.setJSON(json);

                // Exécution de la commande
                command.execute();
            }
            catch (RuleException ex)
            {
                // Reçu une action qui enfreint les règles
                Log.out("Unauthorized move: " + ex);
                socket.send((new MessageError("rule_error")).toString());
            }
            catch (UnknownCommandException ex)
            {
                // Reçu une commande inconnue
                Log.out("Unknown command: " + ex);
                socket.send((new MessageError("unknown_command")).toString());
            }
            catch (SQLException ex)
            {
                // Erreur de communication avec la base de données
                Log.error("SQL error: " + ex);
                socket.send((new MessageError("unknown_error")).toString());
            }
        }
        catch (JSONException ex)
        {
            // JSON reçu faux ou illisible
            Log.error("JSON error: " + ex);
        }
    }

    /**
     * Méthode appelée lorsqu'une erreur est survenue.
     * @param socket WebSocket concernée
     * @param ex     Exception émise
     */
    @Override
    public void onError(WebSocket socket, Exception ex)
    {
        // Signalisation
        if (socket != null) Log.error("Error from " + socket.getRemoteSocketAddress() + ": " + ex);
        else                Log.error("Error:" + ex);
    }
    
    /**
     * Ajoute un utilisateur à la liste des utilisateurs connectés.
     * @param user Utilisateur à connecter
     */
    public void logIn(User user)
    {
        this.loggedUsers.put(user.getId(), user);
    }
    
    /**
     * Supprime un utilisateur de la liste des utilisateurs connectés.
     * @param user Utilisateur à déconnecter
     */
    public void logOut(User user)
    {
        this.loggedUsers.remove(user.getId());
    }
    
}
