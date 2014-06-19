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

import java.awt.Point;
import java.sql.SQLException;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.MessageConfirm;
import octavio.server.db.entities.Room;
import octavio.server.db.entities.Session;
import octavio.server.exception.CommandException;
import org.json.JSONException;

/**
 * Commande pour effectuer une action.
 *
 * @author Lzard
 */
public class GameMove extends ClientCommand
{
    @Override
    public void execute() throws SQLException, JSONException, CommandException
    {
        Room room = Room.getRoom(this.getJSON().getInt("room_id"));
        Game game = room.getGame();
        Cell cell = game.getBoard().getCell(new Point(this.getJSON().getInt("start_u"), this.getJSON().getInt("start_w")));
        Cell newCell = game.getBoard().getCell(new Point(this.getJSON().getInt("end_u"), this.getJSON().getInt("end_w")));

        if (room.getPlayers().get(game.getLegion(this.getJSON().getInt("legion_id")).getPlayer()) != this.getUser())
        {
            throw new CommandException("Not " + this.getUser().getName() + "'s legion", "not_players_legion");
        }

        if (cell.getPawn() == null)
        {
            throw new CommandException("No pawn here", "no_pawn_here");
        }

        if (newCell == null)
        {
            throw new CommandException("Inexistant destination cell", "inexistant_destination_cell");
        }

        Movement movement = new Movement(cell.getPawn(), newCell);
        Action action = new Action(game.getLegion(this.getJSON().getInt("legion_id")), movement, null);
        game.addAction(action);
        
        Session.getSession(this.getUser()).send(new MessageConfirm("action_received"));
    }

}
