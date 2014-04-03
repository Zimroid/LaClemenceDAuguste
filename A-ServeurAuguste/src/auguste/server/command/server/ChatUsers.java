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

package auguste.server.command.server;

import auguste.server.Room;
import auguste.server.Server;
import auguste.server.User;
import auguste.server.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande de transfert de la liste des utilisateurs authentifiés actuellement
 * connectés au serveur ou à une salon.
 * @author Lzard
 */
public class ChatUsers extends ServerCommand
{
    /**
     * Remplit le JSON avec les utilisateurs authentifiés.
     */
    public ChatUsers()
    {
        // Constructeur de la classe mère
        super("chat_users");
        
        // Remplissage du JSON
        try
        {
            // Création du JSONArray décrivant la liste des utilisateurs
            JSONArray userList = new JSONArray();
            for (User user : Server.getInstance().getUsers())
            {
                JSONObject userEntry = new JSONObject();
                userEntry.put("id", user.getId());
                userEntry.put("name", user.getName());
                userList.put(userEntry);
            }

            // Ajout de la liste au JSON
            this.getJSON().put("list", userList);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Remplit le JSON avec les utilisateurs de la salon demandée.
     * @param room salon demandée
     */
    public ChatUsers(Room room)
    {
        // Constructeur de la classe mère
        super("chat_users");
        
        // Remplissage du JSON
        try
        {
            // Création du JSONArray décrivant la liste des utilisateurs de la salon
            JSONArray userList = new JSONArray();
            for (User user : room.getUsers().values())
            {
                JSONObject userEntry = new JSONObject();
                userEntry.put("id", user.getId());
                userEntry.put("name", user.getName());
                userList.put(userEntry);
            }

            // Ajout de la liste au JSON
            this.getJSON().put("list", userList);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }

}
