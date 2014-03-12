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

package auguste.server.command.client;

import auguste.server.entity.Player;
import auguste.server.exception.RuleException;
import java.sql.SQLException;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe abstraite des commandes client à exécuter.
 * @author Lzard
 */
public abstract class ClientCommand
{
	private JSONObject command; // Contenu de la commande
	private Player player;      // Joueur qui a émit la commande
	private WebSocket socket;   // Socket ayant reçu la commande
	
	/**
	 * Exécution de la commande.
	 * @throws java.sql.SQLException
	 * @throws org.json.JSONException
	 * @throws auguste.server.exception.RuleException
	 */
	public abstract void execute() throws SQLException, JSONException, RuleException;

	/**
	 * Retourne le JSONObject de la commande.
	 * @return JSONObject de la commande
	 */
	public JSONObject getCommand()
	{
		return command;
	}

	/**
	 * Retourne le joueur ayant émit la commande.
	 * @return Player ayant émit la commande
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Retourne la WebSocket ayant reçu la commande
	 * @return WebSocket ayant reçu la commande
	 */
	public WebSocket getSocket() {
		return socket;
	}

	/**
	 * Modifie le joueur ayant émit la commande.
	 * @param player Player à utiliser
	 */
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * Modifie le JSONObject de la commande.
	 * @param command JSONObject à utiliser
	 */
	public void setCommand(JSONObject command)
	{
		this.command = command;
	}

	/**
	 * Modifie la WebSocket ayant reçu la commande.
	 * @param socket WebSocket à utiliser
	 */
	public void setSocket(WebSocket socket) {
		this.socket = socket;
	}
	
	/**
	* Liste des commandes émises par le client.
	*/
	public enum CommandName
	{
		CHAT_SEND,
		CREATE_ACCOUNT,
		LOG_IN,
		LOG_OUT
	}

}
