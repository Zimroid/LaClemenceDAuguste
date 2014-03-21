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

package auguste.server.command.client;

import auguste.server.Room;
import auguste.server.Server;
import org.json.JSONException;

/**
 * Commande de configuration d'une partie.
 * @author Lzard
 */
public class GameConfig extends ClientCommand
{
    @Override
    public void execute() throws JSONException
    {
        // Vérification de l'identification de l'utilisateur
        if (this.getClient().isLogged())
        {
            // Récupération de la salle et vérification de son existence
            Room room = Server.getInstance().getRoom(this.getJSON().getInt("game_id"));
            if (room != null)
            {
                // Vérification de la présence de l'utilisateur
                if (this.getClient().isInRoom(room))
                {
                    // Vérification du propriétaire
                    if (room.isOwner(this.getClient()))
                    {
                        // Modification de la configuration et confirmation
                        room.setGameName(this.getJSON().getString("game_name"));
                        room.setPlayerNumber(this.getJSON().getInt("player_number"));
                        room.setBoardSize(this.getJSON().getInt("board_size"));
                        room.confirm();
                    }
                    else this.getClient().error("not_owner_of_this_room");
                }
                else this.getClient().error("not_in_this_room");
            }
            else this.getClient().error("inexistant_room");
        }
        else this.getClient().error("not_logged");
    }
    
}
