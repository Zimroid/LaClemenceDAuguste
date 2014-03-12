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

import auguste.server.command.server.LogConfirm;
import auguste.server.command.server.LogError;
import auguste.server.entity.Player;
import auguste.server.manager.PlayerManager;
import auguste.server.util.Db;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande d'identification d'un joueur.
 * @author Lzard
 */
public class LogIn extends ClientCommand
{
	// Clés du JSON
	private static final String JSONKEY_NAME     = "name";
	private static final String JSONKEY_PASSWORD = "password";
	
	@Override
	public void execute() throws SQLException, JSONException
	{
		// Connexion à la base de données et récupération du nouveau joueur
		Player playerToLog;
		try (Connection connection = Db.open())
		{
			playerToLog = PlayerManager.getPlayer(
					connection,
					this.getCommand().getString(LogIn.JSONKEY_NAME),
					this.getCommand().getString(LogIn.JSONKEY_PASSWORD)
			);
			connection.close();
		}
		
		// Si un joueur a été trouvé, mise à jour du joueur connecté
		if (playerToLog != null)
		{
			this.getPlayer().setId(playerToLog.getId());
			this.getPlayer().setLogin(playerToLog.getLogin());
			this.getSocket().send((new LogConfirm()).getJSONString());
		}
		else this.getSocket().send((new LogError()).getJSONString());
	}
	
}
