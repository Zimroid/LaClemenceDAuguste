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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import auguste.client.engine.Legion;
import auguste.client.engine.Player;
import auguste.client.engine.Team;
import auguste.client.entity.Game;

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
        this.getJSON().put("game_mode", "fast");
        this.getJSON().put(PLAYER_NUMBER, this.getArguments().get(PLAYER_NUMBER));
        
        Game game = this.getClient().getGame(Integer.parseInt((String) this.getArguments().get(ROOM_ID)));
        
        // Il semblerai que les teams ne s'initialisent pas correctement à voir dans le game_confirm
    	List<?> teams = game.getTeams();
        JSONArray jsonTeams = getJSONTeams(teams);
        this.getJSON().put(TEAMS, jsonTeams);
        System.out.println(this.getJSON().toString());
    }
    
    private static JSONArray getJSONTeams(List<?> teams) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(int i = 0; i < teams.size(); i++)
    	{
    		Team team = (Team) teams.get(i);
    		List<Player> players = team.getPlayers();
    		res.put(getJSONTeam(players));
    	}
    	
    	return res;
    }
    
    private static JSONObject getJSONTeam(List<Player> players) throws JSONException
    {
    	JSONObject res = new JSONObject();
    	
    	JSONArray jsonPlayers = getJSONPlayers(players);
    	res.put("players", jsonPlayers);
    	
    	return res;
    }
    
    private static JSONArray getJSONPlayers(List<Player> players) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(Player player : players)
    	{
    		res.put(getJSONPlayer(player));
    	}
    	
    	return res;
    }
    
    private static JSONObject getJSONPlayer(Player player) throws JSONException
    {
    	JSONObject res = new JSONObject();
    	
    	res.put("player_user_id", player.getId());
    	res.put("legions", getLegions(player.getLegions()));
    	
    	return res;
    }
    
    private static JSONArray getLegions(List<Legion> legions) throws JSONException
    {
    	JSONArray res = new JSONArray();
    	
    	for(Legion legion : legions)
    	{
    		res.put(getLegion(legion));
    	}
    	
    	return res;
    }
    
    private static JSONObject getLegion(Legion legion) throws JSONException
    {
    	JSONObject res = new JSONObject();
    	
    	res.put("legion_color", legion.getColor());
    	res.put("legion_shape", legion.getShape());
    	res.put("legion_position", legion.getPosition());
    	
    	return res;
    }
}