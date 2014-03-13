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

import auguste.server.command.server.Confirm;
import auguste.server.entity.Player;
import auguste.server.manager.PlayerManager;
import auguste.server.util.Db;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande de création d'un compte.
 * @author Lzard
 */
public class AccountCreate extends ClientCommand
{
	@Override
	public void execute() throws JSONException, SQLException
	{
		// Création du compte si le joueur n'est pas identifié
		if (!this.getPlayer().isLogged())
		{
			// Création du joueur
			Player newPlayer = new Player(
					Player.DEFAULT_ID,
					this.getCommand().getString("name"),
					Player.hashPassword(this.getCommand().getString("password"))
			);
			
			// Sauvegarde du joueur
			try (Connection connection = Db.open())
			{
				PlayerManager manager = new PlayerManager(connection);
				manager.savePlayer(newPlayer);
				connection.commit();
				connection.close();
			}
			
			// Signalisation
			this.getSocket().send((new Confirm(Confirm.TYPE_ACCOUNT_CREATE)).getJSONString());
		}
	}
}
