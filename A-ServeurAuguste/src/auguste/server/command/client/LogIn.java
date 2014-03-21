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

import auguste.server.Server;
import auguste.server.command.server.LogConfirm;
import auguste.server.Client;
import auguste.server.manager.UserManager;
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
    @Override
    public void execute() throws SQLException, JSONException
    {
        // Commande pouvant être exécutée que si l'utilisateur n'est pas identifié
        if (!this.getClient().isLogged())
        {
            // Connexion à la base de données et récupération du nouvel utilisateur
            Client userToLog;
            try (Connection connection = Db.open())
            {
                UserManager manager = new UserManager(connection);
                userToLog = manager.getUser(
                        this.getJSON().getString("name"),
                        this.getJSON().getString("password")
                );
                Db.close(connection);
            }

            // Identification du joueur
            if (userToLog != null)
            {
                // Déconnexion de l'utilisateur de même nom déjà connecté
                Server.getInstance().logOut(userToLog);
                
                // Mise à jour du client courant
                this.getClient().setId(userToLog.getId());
                this.getClient().setName(userToLog.getName());
                
                // Connexion de l'utilisateur
                Server.getInstance().logIn(this.getClient());
                this.getClient().send((new LogConfirm(this.getClient())).toString());
            }
            else this.getClient().error("log_error");
        }
        else this.getClient().error("already_logged");
    }
    
}
