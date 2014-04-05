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
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        this.getJSON().put(GAME_NAME, this.getArguments().get(GAME_NAME));
        this.getJSON().put(BOARD_SIZE, this.getArguments().get(BOARD_SIZE));
        this.getJSON().put(GAME_TURN_DURATION, this.getArguments().get(GAME_TURN_DURATION));
        
    	List<?> teams = (List<?>) this.getArguments().get(TEAMS);
        JSONArray jsonTeams = getJSONTeams(teams);
        this.getJSON().put("teams", jsonTeams);
        //System.out.println(this.getJSON().toString());
    }
    
    private static JSONArray getJSONTeams(List<?> teams) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(int i = 0; i < teams.size(); i++)
    	{
    		List<?> players = (List<?>) teams.get(i);
    		JSONArray jsonPlayers = getJSONPlayers(players);
    		res.put(jsonPlayers);
    	}
    	
    	return res;
    }
    
    private static JSONArray getJSONPlayers(List<?> players) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(int i = 0; i < players.size(); i++)
    	{
    		Map<?,?> player = (Map<?,?>) players.get(i);
    		JSONObject jsonPlayer = getJSONPlayer(player);
    		res.put(jsonPlayer);
    	}
    	
    	return res;
    }
    
    private static JSONObject getJSONPlayer(Map<?,?> player) throws JSONException
    {
    	JSONObject res = new JSONObject();
    	
    	int player_user_id = (Integer) player.get("player_user_id");
    	List<?> legions = (List<?>) player.get("legions");
    	JSONArray jsonLegions = getJSONLegions(legions);
    	
    	res.put("player_user_id", player_user_id);
    	res.put("legions", jsonLegions);
    	
    	return res;
    }
    
    private static JSONArray getJSONLegions(List<?> legions) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(Object obj : legions)
    	{
    		Map<?,?> legion = (Map<?,?>) obj;
    		JSONObject jsonLegion = getJSONLegion(legion);
    		res.put(jsonLegion);
    	}
    	
    	return res;
    }
    
    private static JSONObject getJSONLegion(Map<?,?> legion) throws JSONException
    {
    	JSONObject res = new JSONObject();
    	
    	res.put("legion_color", legion.get("legion_color"));
    	res.put("legion_shape", legion.get("legion_shape"));
    	
    	return res;
    }
}