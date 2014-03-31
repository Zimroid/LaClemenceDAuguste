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
import auguste.server.Room.State;
import auguste.server.Server;
import auguste.server.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Commande d'envoi de la liste des salles crées disponibles.
 * @author Lzard
 */
public class GameAvailables extends ServerCommand
{
    /**
     * Remplit le JSON avec les paramètres fournis.
     */
    public GameAvailables()
    {
        // Constructeur de la classe mère
        super("game_availables");
        
        // Remplissage du JSON
        try
        {
            // Création du JSONObject contenant la liste des salles
            JSONArray roomList = new JSONArray();
            for (Room room : Server.getInstance().getRooms())
            {
                // Salle ignorée si en cours de fermeture
                if (room.getState() != State.CLOSING)
                {
                    JSONObject roomEntry = new JSONObject();
                    roomEntry.put("room_id", room.getId());
                    roomEntry.put("game_name", room.getName());
                    roomList.put(roomEntry);
                }
            }

            // Création du JSON
            this.getJSON().put("list", roomList);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
}
