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

import auguste.server.command.client.ChatSend;
import auguste.server.command.client.ClientCommand;
import auguste.server.command.client.AccountCreate;
import auguste.server.command.client.LogIn;
import auguste.server.command.client.LogOut;
import auguste.server.command.server.ErrorMessage;
import auguste.server.entity.Player;
import auguste.server.exception.RuleException;
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
	private final HashMap<WebSocket, Player> players = new HashMap<>();
	
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
		Log.out("Initialized server on " + address);
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
		Player newPlayer = new Player(Player.DEFAULT_ID, Player.DEFAULT_LOGIN, Player.DEFAULT_PASSWORD);
		this.players.put(socket, newPlayer);
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
		this.players.remove(socket);
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
				Player player = this.players.get(socket);
				System.out.println("Identified command: " + json.getString("command"));

				// Définition de la commande
				ClientCommand command;
				switch (ClientCommand.CommandName.valueOf(json.getString("command").toUpperCase()))
				{
					case ACCOUNT_CREATE: command = new AccountCreate(); break;
					case CHAT_SEND:      command = new ChatSend();      break;
					case GAME_CREATE:    command = new GameCreate();    break;
					case GAME_JOIN:      command = new GameJoin();      break;
					case GAME_LIST:      command = new GameList();      break;
					case GAME_START:     command = new GameStart();     break;
					case LOG_IN:         command = new LogIn();         break;
					case LOG_OUT:        command = new LogOut();        break;
					default:             command = null;                break;
				}

				// Exécution de la commande
				if (command == null) throw new JSONException("Unknown command");
				else
				{
					command.setCommand(json);
					command.setPlayer(player);
					command.setSocket(socket);
					command.execute();
				}
			}
			catch (RuleException ex)
			{
				// Reçu une action qui enfreint les règles
				Log.debug("Unauthorized move: " + ex);
				socket.send((new ErrorMessage(ErrorMessage.TYPE_RULE_ERROR)).getJSONString());
			}
		}
		catch (JSONException ex)
		{
			// JSON reçu faux ou illisible
			Log.debug("Unable to read JSON: " + ex);
		}
		catch (SQLException ex)
		{
			// Erreur de communication avec la base de données
			Log.debug("SQL error: " + ex);
		}
	}

	/**
	 * Méthode appelée lorsqu'une erreur est apparue.
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
	
}
