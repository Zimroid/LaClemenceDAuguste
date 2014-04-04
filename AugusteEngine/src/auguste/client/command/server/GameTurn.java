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
import auguste.client.interfaces.UpdateListener;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class GameTurn extends CommandServer
{
    public static final String GAME_ID =   "game_id";
    
    public GameTurn()
    {
        super();
    }
    
    @Override
    public void execute() throws JSONException 
    {
        int id = this.getJSON().getInt(GAME_ID);
        
        Game game = this.getClient().getGame(id);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.gameTurnUpdate(id);
        }
    }
}