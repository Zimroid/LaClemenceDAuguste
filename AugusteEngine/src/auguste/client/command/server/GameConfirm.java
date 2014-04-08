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

import java.util.ArrayList;
import java.util.List;

import auguste.client.engine.Legion;
import auguste.client.engine.Player;
import auguste.client.engine.Team;
import auguste.client.entity.Game;
import auguste.client.interfaces.UpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameConfirm extends CommandServer
{
    public static final String GAME_NAME =      	"game_name";
    public static final String ROOM_ID =        	"room_id";
    public static final String BOARD_SIZE =     	"game_board_size";
    public static final String GAME_TURN_DURATION =	"game_turn_duration";
    public static final String CONFIGURATION =		"configuration";
    public static final String TEAMS =				"teams";
    
    private Game game;
    
    public GameConfirm()
    {
        super();
        game = new Game();
    }

    @Override
    public void execute() throws JSONException 
    {
        this.setConfiguration(this.getJSON());
        this.setTeams(this.getJSON());
        
        this.getClient().updateGame(game);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.createGameUpdate(game.getId());
        }
    }
    
    private void setConfiguration(JSONObject json) throws JSONException
    {
    	JSONObject configuration = json.getJSONObject(CONFIGURATION);
    	
    	String game_name 		= configuration.getString(GAME_NAME);
    	int board_size			= configuration.getInt(BOARD_SIZE);
    	long game_turn_duration	= configuration.getLong(GAME_TURN_DURATION);
    	
    	this.game.setName(game_name);
    	this.game.setBoardSize(board_size);
    	this.game.setTurn_duration(game_turn_duration);
    }
    
    private void setTeams(JSONObject json) throws JSONException
    {
    	List<Team> res = new ArrayList<>();
    	JSONArray teams = json.getJSONArray(TEAMS);
    	
    	for(int i = 0; i < teams.length(); i++)
    	{
    		Team team = new Team();
    		this.setPlayers(teams.getJSONArray(i), team);
    	}
    	
    	this.game.setTeams(res);
    }
    
    private void setPlayers(JSONArray players, Team team) throws JSONException
    {
    	for(int i = 0; i < players.length(); i++)
    	{
    		this.setPlayer(players.getJSONObject(i), team);
    	}
    }
    
    private void setPlayer(JSONObject player, Team team) throws JSONException
    {
    	Player pl = game.getPlayer(player.getInt("player_user_id"));
    	setLegions(pl, player.getJSONArray("legions"));
    	team.addPlayer(pl);
    }
    
    private void setLegions(Player player, JSONArray jsonLegions) throws JSONException
    {
    	List<Legion> legions = new ArrayList<>();
    	
    	for(int i = 0; i < jsonLegions.length(); i++)
    	{
    		Legion legion = new Legion(i+1, player);
    		
    		String color = jsonLegions.getJSONObject(i).getString("legion_color");
    		String shape = jsonLegions.getJSONObject(i).getString("legion_shape");
    		
    		legion.setColor(color);
    		legion.setShape(shape);
    		
    		legions.add(legion);
    	}
    	
    	player.setLegions(legions);
    }
}