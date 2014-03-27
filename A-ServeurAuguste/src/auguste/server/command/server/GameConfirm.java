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
import org.json.JSONException;

/**
 * Commande de confirmation de création/modification d'une partie.
 * @author Lzard
 */
public class GameConfirm extends ServerCommand
{
    /**
     * Remplit le JSON avec la configuration de la partie.
     * @param room Salle de la partie
     * @throws JSONException Erreur JSON
     */
    public GameConfirm(Room room) throws JSONException
    {
        // Constructeur de la classe mère
        super("game_confirm", room);
        
        // Création du JSON
        this.getJSON().put("game_name",     room.getGameName());
        this.getJSON().put("player_number", room.getPlayerNumber());
        this.getJSON().put("board_size",    room.getBoardSize());
    }
    
}
