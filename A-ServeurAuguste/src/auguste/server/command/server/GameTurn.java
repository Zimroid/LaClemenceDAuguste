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
import auguste.server.command.ServerCommand;
import auguste.server.util.Log;
import org.json.JSONException;

/**
 * Commande de transfert des données d'un tour.
 * 
 * @author Lzard
 */
public class GameTurn extends ServerCommand
{
    /**
     * Remplit le JSON avec les données du tour.
     * @param room Salon de la partie
     */
    public GameTurn(Room room)
    {
        // Constructeur de la classe mère
        super("game_turn", room);
        
        try
        {
            room.addTurnData(this.getJSON());
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
}
