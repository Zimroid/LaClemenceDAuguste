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

package octavio.server.commands.client;

import java.sql.SQLException;
import octavio.server.Server;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.GameConfirm;
import octavio.server.commands.server.GameTurn;
import octavio.server.commands.server.RoomUsers;
import octavio.server.db.entities.Room;
import octavio.server.db.entities.Session;
import octavio.server.exception.CommandException;
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
    public void execute() throws JSONException, CommandException, SQLException
    {
        // Récupération du salon
        Room room = Room.getRoom(this.getJSON().getInt("room_id"));

        // Vérification de la non-présence de l'utilisateur
        if (!Session.getSession(this.getUser()).getRooms().contains(room))
        {
            // Ajout du client au salon
            Session.getSession(this.getUser()).joinRoom(room);
            Session.getSession(this.getUser()).send(new GameConfirm(room));
            Session.getSession(this.getUser()).send(new GameTurn(room));
            room.broadcast(new RoomUsers(room));
        }
        else
        {
            throw new CommandException("Already in this room", "already_in_this_room");
        }
    }

}
