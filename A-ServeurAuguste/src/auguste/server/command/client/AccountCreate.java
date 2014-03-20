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

import auguste.server.User;
import auguste.server.command.server.MessageConfirm;
import auguste.server.manager.UserManager;
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
        if (!this.getUser().isIdentified())
        {
            // Création du joueur
            User newUser = new User(
                    User.DEFAULT_ID,
                    this.getJSON().getString("name"),
                    User.hashPassword(this.getJSON().getString("password"))
            );
            
            // Sauvegarde du joueur
            try (Connection connection = Db.open())
            {
                UserManager manager = new UserManager(connection);
                manager.saveUser(newUser);
                Db.close(connection);
            }
            
            // Signalisation
            this.getSocket().send((new MessageConfirm("account_create")).toString());
        }
    }
    
}
