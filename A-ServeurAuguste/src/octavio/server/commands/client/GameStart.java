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
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.GameTurn;
import octavio.server.db.entities.Room;
import octavio.server.exception.CommandException;
import org.json.JSONException;

/**
 * Commande pour commencer une partie.
 *
 * @author Lzard
 */
public class GameStart extends ClientCommand
{
    @Override
    public void execute() throws SQLException, JSONException, CommandException
    {
        Room room = Room.getRoom(this.getJSON().getInt("room_id"));
        room.getGame().initBoard();
        room.broadcast((new GameTurn(room)));
    }

}
