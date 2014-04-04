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

package auguste.client.command.client;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class GameConfig extends CommandClient
{
    public GameConfig() throws URISyntaxException
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException
    {
        this.getJSON().put(COMMAND, GAME_CONFIG);
        this.getJSON().put(ROOM_ID, this.getArguments().get(ROOM_ID));
        this.getJSON().put(PLAYER_NUMBER,this.getArguments().get(PLAYER_NUMBER));
        this.getJSON().put(GAME_NAME, this.getArguments().get(GAME_NAME));
        this.getJSON().put(BOARD_SIZE, this.getArguments().get(BOARD_SIZE));
        
        System.out.println(this.getJSON().toString());
    }
}