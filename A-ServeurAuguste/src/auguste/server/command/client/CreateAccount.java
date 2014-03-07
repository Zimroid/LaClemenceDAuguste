/*
 * Copyright 2014 Lzard.
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

import auguste.server.Server;
import auguste.server.entity.Player;
import org.json.JSONObject;

/**
 * Commande de création d'un compte.
 * @author Lzard
 */
public class CreateAccount extends ClientCommand
{
	/**
	 * Constructeur faisant appel au constructeur de la classe mère.
	 * @param player Joueur ayant émit la commande
	 * @param command Commande émise
	 */
	public CreateAccount(Player player, JSONObject command)
	{
		super(player, command);
	}
	
	@Override
	public void execute()
	{
		Server.getInstance().broadcast(":)");
	}
	
}
