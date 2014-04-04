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

import auguste.server.command.ClientCommand;
import auguste.server.Server;
import auguste.server.User;
import auguste.server.command.server.LogConfirm;
import auguste.server.manager.UserManager;
import auguste.server.util.Db;
import java.sql.Connection;
import java.sql.SQLException;
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
    public boolean checkAuth()
    {
        return false;
    }
    
    @Override
    public boolean checkRoom()
    {
        return false;
    }
    
    @Override
    public void execute() throws SQLException, JSONException
    {
        // Commande pouvant être exécutée que si l'utilisateur n'est pas authentifié
        if (this.getUser() == null)
        {
            // Connexion à la base de données et récupération de l'utilisateur
            User userToLog;
            try (Connection connection = Db.open())
            {
                UserManager manager = new UserManager(connection);
                userToLog = manager.getUser(
                        this.getJSON().getString("user_name"),
                        User.hashPassword(this.getJSON().getString("user_password"))
                );
            }

            // Authentification du client
            if (userToLog != null)
            {
                // Déconnexion des utilisateurs de même nom déjà connecté
                Server.getInstance().logOut(userToLog);
                
                // Connexion de l'utilisateur
                Server.getInstance().logIn(this.getSocket(), userToLog);
                this.send((new LogConfirm(userToLog)).toString());
            }
            else this.error("log_error");
        }
        else this.error("already_logged");
    }
    
}
