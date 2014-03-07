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
import org.json.JSONObject;

/**
 * Classe abstraite des commandes client à exécuter.
 * @author Lzard
 */
public abstract class ClientCommand
{
	private Player player;      // Joueur qui a émit la commande
	private JSONObject command; // Contenu de la commande
	
	/**
	 * Constructeur d'un objet commande du client.
	 * @param player Joueur ayant émit cette commande
	 * @param command JSON de la commande
	 */
	public ClientCommand(Player player, JSONObject command)
	{
		this.player = player;
		this.command = command;
	}
	
	/**
	 * Exécution de la commande.
	 */
	public abstract void execute();

	/**
	 * Retourne le joueur ayant émit la commande.
	 * @return Player ayant émit la commande
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 * Retourne le JSONObject de la commande.
	 * @return JSONObject de la commande
	 */
	public JSONObject getCommand()
	{
		return command;
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
