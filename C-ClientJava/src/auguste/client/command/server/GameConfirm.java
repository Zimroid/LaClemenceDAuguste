/*
 * Copyright 2014 Evinrude.
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

package auguste.client.command.server;

import auguste.client.entity.Game;
import auguste.client.graphical.UpdateListener;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class GameConfirm extends CommandServer
{
    public GameConfirm()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        Game game = new Game();
        game.setName(this.getJSON().getString("game_name"));
        game.setId(this.getJSON().getInt("game_id"));
        game.setLegion_number(this.getJSON().getInt("player_number"));
        game.setBoardSize(this.getJSON().getInt("board_size"));
        this.getClient().setCurrentGame(game);
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.createGameUpdate();
        }
    }
}