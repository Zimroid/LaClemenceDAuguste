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

package octavio.server.command.client;

import octavio.server.Room;
import octavio.server.Server;
import octavio.server.command.ClientCommand;
import octavio.server.exception.InexistantRoomException;
import org.json.JSONException;

/**
 * Commande pour rejoindre une partie. Identifie le salon et, si il existe et
 * que l'utilisateur ne l'a pas déjà rejoint, y ajoute l'utilisateur.
 *
 * @author Lzard
 */
public class RoomJoin extends ClientCommand
{
    @Override
    public boolean checkRoom()
    {
        return false;
    }

    @Override
    public void execute() throws InexistantRoomException, JSONException
    {
        // Récupération du salon
        Room room = Server.getInstance().getRoom(this.getJSON().getInt("room_id"));

        synchronized (room)
        {
            // Vérification de la non-présence de l'utilisateur
            if (!room.isInRoom(this.getUser()))
            {
                // Ajout du client au salon
                Server.getInstance().joinRoom(this.getUser(), room);
            }
            else
            {
                this.error("already_in_this_room");
            }
        }
    }

}
