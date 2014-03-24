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
 * Commande indiquant la fermeture d'une salle.
 * @author Lzard
 */
public class GameClose extends ServerCommand
{
    /**
     * Remplit le JSON avec les paramètres fournis.
     * @param room Salle fermée
     * @throws JSONException Erreur de JSON
     */
    public GameClose(Room room) throws JSONException
    {
        // Constructeur de la classe mère
        super("game_close");
        
        // Création du JSON
        this.getJSON().put("game_id", room.getId());
    }
    
}
