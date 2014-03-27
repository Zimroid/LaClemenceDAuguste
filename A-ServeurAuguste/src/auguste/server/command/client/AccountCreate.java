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
import auguste.server.manager.UserManager;
import auguste.server.util.Db;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande de création d'un compte. Instancie un objet User avec les paramètres
 * donnés, ouvre une connexion à la base de données, vérifie la disponibilité
 * du nom puis ajoute l'utilisateur.
 * @author Lzard
 */
public class AccountCreate extends ClientCommand
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
    public void execute() throws JSONException, SQLException
    {
        if (this.getUser() == null)
        {
            // Création du joueur
            User newUser = new User(
                    this.getJSON().getString("name"),
                    User.hashPassword(this.getJSON().getString("password"))
            );

            // Sauvegarde du joueur
            try (Connection connection = Db.open())
            {
                // Initialisation du manager
                UserManager manager = new UserManager(connection);

                // Vérification de la disponibilité du nom
                if (manager.getNameAvailable(newUser.getName()))
                {
                    manager.saveUser(newUser);
                    this.confirm("account_create");
                }
                else this.error("name_unavailable");

                // Fermeture de la connexion
                Db.close(connection);
            }
        }
        else this.error("already_logged");
    }
    
}
