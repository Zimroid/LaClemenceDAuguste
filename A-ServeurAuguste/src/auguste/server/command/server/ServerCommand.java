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

package auguste.server.command.server;

import org.json.JSONObject;

/**
 * Classe abstraite des commandes serveurs à envoyer.
 * @author Lzard
 */
public abstract class ServerCommand
{
	// JSON de la commande
	private final JSONObject json = new JSONObject();
	
	/**
	 * Retourne le JSON de la commande.
	 * @return JSON de la commande
	 */
	public JSONObject getJSON()
	{
		return this.json;
	}
	
	/**
	 * Retourne le JSON de la commande sous forme de JSON.
	 * @return JSON de la commande
	 */
	public String getJSONString()
	{
		return this.json.toString();
	}
	
	/**
	* Enumération des commandes émises par le serveur.
	* @author Lzard
	*/
   public enum CommandName
   {
	   CHAT_MESSAGE,
	   CONFIRM,
	   CONFIRM_LOG,
	   ERROR_MESSAGE,
	   GAME_AVAILABLES
   }
}
