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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande de transfert de la liste des utilisateurs authentifiés.
 * @author Lzard
 */
public class ChatUsers extends ServerCommand
{
    /**
     * Remplit le JSON avec les paramètres fournis.
     * @throws JSONException Erreur de JSON
     */
    public ChatUsers() throws JSONException
    {
        // Constructeur de la classe mère
        super("chat_users");
        
        // Création du JSONObject contenant la liste des utilisateurs
        JSONArray userList = new JSONArray();
        for (User user : Server.getInstance().getUserSet())
        {
            JSONObject userEntry = new JSONObject();
            userEntry.put("id", user.getId());
            userEntry.put("name", user.getName());
            userList.put(userEntry);
        }
        
        // Création du JSON
        this.getJSON().put("list", userList);
    }
    
    /**
     * Remplit le JSON avec les paramètres fournis.
     * @param room Salle demandée
     * @throws JSONException Erreur de JSON
     */
    public ChatUsers(Room room) throws JSONException
    {
        // Constructeur de la classe mère
        super("chat_users");
        
        // Création du JSONObject contenant la liste des utilisateurs
        JSONArray userList = new JSONArray();
        for (User user : room.getUserCollection())
        {
            JSONObject userEntry = new JSONObject();
            userEntry.put("name", user.getName());
            userList.put(userEntry);
        }
        
        // Création du JSON
        this.getJSON().put("list", userList);
    }

}