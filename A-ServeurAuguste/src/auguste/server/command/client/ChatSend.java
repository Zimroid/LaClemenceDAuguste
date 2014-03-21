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
import auguste.server.command.server.ChatMessage;
import org.json.JSONException;

/**
 * Commande d'envoi d'un message.
 * @author Lzard
 */
public class ChatSend extends ClientCommand
{
    @Override
    public void execute() throws JSONException
    {
        // Envoi du message si l'utilisateur est identifié
        if (this.getClient().isLogged())
        {
            // Identification de la room concernée
            Integer gameId = Integer.valueOf(this.getJSON().getInt("game_id"));
            Room room = Server.getInstance().getRoom(gameId);
            
            // Vérification de l'existance de la salle
            if (room != null)
            {
                // Vérification de la présence dans la salle
                if (this.getClient().isInRoom(room))
                {
                    // Envoi du message à la salle
                    room.broadcast(
                            (new ChatMessage(
                                    this.getClient(),
                                    gameId,
                                    this.getJSON().getString("message")
                            )).toString()
                    );
                }
                else this.getClient().error("not_in_this_room");
            }
            else this.getClient().error("inexistent_room");
        }
        else this.getClient().error("must_be_logged");
    }
    
}
