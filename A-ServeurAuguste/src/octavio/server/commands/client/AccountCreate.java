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
import octavio.server.commands.ClientCommand;
import octavio.server.db.entities.User;
import octavio.server.db.managers.UserManager;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import org.json.JSONException;

/**
 * Commande de création d'un compte. Instancie un objet User avec les paramètres
 * donnés, ouvre une connexion à la base de données, vérifie la disponibilité
 * du nom puis ajoute l'utilisateur.
 *
 * @author Lzard
 */
public class AccountCreate extends ClientCommand
{
    @Override
    public void execute() throws CommandException, JSONException, SQLException
    {
        if (this.getUser() == User.ANONYMOUS)
        {
            try (Connection connection = Configuration.getDbConnection())
            {
                UserManager manager = new UserManager(connection);

                if (manager.getNameAvailability(this.getJSON().getString("user_name")))
                {
                    manager.insertUser(this.getJSON().getString("user_name"), this.getJSON().getString("user_password"));
                    connection.commit();
                }
                else
                {
                    throw new CommandException("Unavailable name", "name_unavailable");
                }
            }
        }
        else
        {
            throw new CommandException("Already logged", "alread_logged");
        }
    }

}
