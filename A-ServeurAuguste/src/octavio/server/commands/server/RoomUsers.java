/*
 * Copyright 2014 Lzard.
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

package octavio.server.commands.server;

import octavio.server.commands.ServerCommand;
import octavio.server.db.entities.Room;
import org.json.JSONException;

/**
 * Commande de signalisation de la liste des utilisateurs d'un salon.
 *
 * @author Lzard
 */
public class RoomUsers extends ServerCommand
{
    /**
     * Remplit le JSON avec les utilisateurs du salon.
     *
     * @param room Salon de la partie
     */
    public RoomUsers(Room room) throws JSONException
    {
        // Constructeur de la classe mère
        super("game_users", room);

        // Remplissage du JSON
        this.getJSON().put("users", room.getUsersJSON());
    }

}
