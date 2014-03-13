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

import org.json.JSONException;

/**
 * Commande de création d'une partie.
 * @author Lzard
 */
public class GameCreate extends ClientCommand
{
	@Override
	public void execute() throws JSONException
	{
		// Création de la partie si le joueur est connecté est n'est pas en game
		if (this.getPlayer().isLogged() && !this.getPlayer().isInGame())
		{
			
		}
	}
}