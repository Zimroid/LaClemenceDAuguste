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

package octavio.server.commands.server;

import octavio.server.Server;
import octavio.server.commands.ServerCommand;
import octavio.server.db.entities.User;
import octavio.server.util.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande de transfert de la liste des utilisateurs authentifiés actuellement
 * connectés au serveur.
 *
 * @author Lzard
 */
public class ListUsers extends ServerCommand
{
    /**
     * Remplit le JSON avec les utilisateurs authentifiés.
     */
    public ListUsers()
    {
        // Constructeur de la classe mère
        super("list_users");

        // Remplissage du JSON
        try
        {
            // Création du JSONArray décrivant la liste des utilisateurs
            JSONArray userList = new JSONArray();
            for (User user : User.getUsers())
            {
                JSONObject userEntry = new JSONObject();
                userEntry.put("user_id", user.getId());
                userEntry.put("user_name", user.getName());
                userList.put(userEntry);
            }

            // Ajout de la liste au JSON
            this.getJSON().put("users", userList);
        }
        catch (JSONException e)
        {
            Logger.printError(e);
        }
    }

}
