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

package octavio.server.commands.client;

import java.sql.Connection;
import java.sql.SQLException;
import octavio.server.Server;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.LogConfirm;
import octavio.server.commands.server.MessageConfirm;
import octavio.server.db.entities.Session;
import octavio.server.db.entities.User;
import octavio.server.db.managers.UserManager;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import org.json.JSONException;

/**
 * Commande d'authentification d'un joueur. Si l'utilisateur n'est pas
 * identifié, ouvre une connexion à la base de données et vérifie qu'un
 * utilisateur enregistré correspond au login donné.
 *
 * @author Lzard
 */
public class LogIn extends ClientCommand
{
    @Override
    public void execute() throws SQLException, JSONException, CommandException
    {
        // Commande pouvant être exécutée que si l'utilisateur n'est pas authentifié
        if (this.getUser() == null)
        {
            // Connexion à la base de données et récupération de l'utilisateur
            User userToLog;
            try (Connection connection = Configuration.getDbConnection())
            {
                UserManager manager = new UserManager(connection);
                userToLog = manager.getUser(
                        this.getJSON().getString("user_name"),
                        UserManager.hashPassword(this.getJSON().getString("user_password"))
                );
                connection.commit();
            }

            // Authentification du client
            if (userToLog != null)
            {
                // Connexion de l'utilisateur
                User.logIn(this.getSocket(), userToLog);
                Session.getSession(userToLog).send(new LogConfirm(userToLog));
            }
        }
        else
        {
            throw new CommandException("Already logged", "alread_logged");
        }
    }

}
