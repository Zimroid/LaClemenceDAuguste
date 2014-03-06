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

import java.net.InetSocketAddress;
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
		// Retour de l'instance du serveur
		return Server.INSTANCE;
	}
	
	/**
	 * Instanciation du serveur. Effectue un simple appel au constructeur de la
	 * classe mère en utilisant l'adresse donnée.
	 * @param address Adresse à laquelle le serveur est attachée
	 */
	public Server(InetSocketAddress address)
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
			JSONObject command = new JSONObject(content);
			System.out.println("Identified command: " + command.getString("command"));
		}
		catch (JSONException ex) {
			Log.debug("Unable to read JSON:" + ex);
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
		Log.error("Error from " + socket.getRemoteSocketAddress() + ": " + ex);
	}
	
	/**
	 * Méthode pour broadcaster un message à tous les utilisateurs.
	 * @param message Message à envoyer
	 */
	public void broadcast(String message)
	{
		// Broadcast du message à tous les utilisateurs connectés
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
