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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande d'envoi de la liste des salles crées.
 * @author Lzard
 */
public class GameAvailables extends ServerCommand
{
    /**
     * Remplit le JSON avec les paramètres fournis.
     * @throws JSONException Erreur de JSON
     */
    public GameAvailables() throws JSONException
    {
        // Constructeur de la classe mère
        super("game_availables");
        
        // Création du JSONObject contenant la liste des salles
        JSONArray roomList = new JSONArray();
        for (Room room : Server.getInstance().getRoomList())
        {
            JSONObject roomEntry = new JSONObject();
            roomEntry.put("game_id", room.getId());
            roomEntry.put("game_name", room.getGameName());
            roomList.put(roomEntry);
        }
        
        // Création du JSON
        this.getJSON().put("list", roomList);
    }
}
