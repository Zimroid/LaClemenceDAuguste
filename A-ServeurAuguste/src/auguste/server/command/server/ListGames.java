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

import auguste.server.command.ServerCommand;
import auguste.server.Room;
import auguste.server.Server;
import auguste.server.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande d'envoi de la liste des salons créés.
 * 
 * @author Lzard
 */
public class ListGames extends ServerCommand
{
    /**
     * Remplit le JSON avec les salles créés.
     */
    public ListGames()
    {
        // Constructeur de la classe mère
        super("list_games");
        
        // Remplissage du JSON
        try
        {
            // Création du JSONArray décrivant la liste des salons
            JSONArray roomList = new JSONArray();
            for (Room room : Server.getInstance().getRooms())
            {
                JSONObject roomEntry = new JSONObject();
                room.addLightConfiguration(roomEntry);
                roomList.put(roomEntry);
            }

            // Création du JSON
            this.getJSON().put("games", roomList);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
}
