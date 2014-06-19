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

package octavio.server.command.server;

import octavio.server.Room;
import octavio.server.command.ServerCommand;
import octavio.server.util.Log;
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
     *
     * @param room    Salon de la partie
     * @param initial
     */
    public GameTurn(Room room, boolean initial)
    {
        // Constructeur de la classe mère
        super("game_turn", room);

        try
        {
            if (initial)
            {
                room.addInitialTurnData(this.getJSON());
            }
            else
            {
                room.addTurnData(this.getJSON());
            }
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }

}
