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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import auguste.client.engine.Legion;
import auguste.client.engine.Player;
import auguste.client.engine.Team;
import auguste.client.entity.Client;
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
    }

    /**
     * Récupère la configuration d'une partie depuis un JSON.
     * La commande construit les équipes et met à jour les informations de la partie dans le moteur.
     */
    @Override
    public void execute() throws JSONException, URISyntaxException 
    {
    	if(Client.getInstance().getGame(this.getJSON().getInt(ROOM_ID)) == null)
    	{
    		this.game = new Game();
    	}
    	else
    	{
            this.game = Client.getInstance().getGame(this.getJSON().getInt(ROOM_ID));
    	}
        this.setConfiguration(this.getJSON());
        this.setTeams(this.getJSON());

        this.getClient().updateGame(game);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.createGameUpdate(game.getId());
        }
    }
    
    /**
     * Met à jour la configuration d'une partie à partir du JSON
     * @throws JSONException, URISyntaxException
     */
    private void setConfiguration(JSONObject json) throws JSONException, URISyntaxException
    {
    	JSONObject configuration = json.getJSONObject(CONFIGURATION);
    	
    	int roomId 				= json.getInt(ROOM_ID);
    	String game_name 		= configuration.getString(GAME_NAME);
    	int board_size			= configuration.getInt(BOARD_SIZE);
    	long game_turn_duration	= configuration.getLong(GAME_TURN_DURATION);
    	
    	this.game.setId(roomId);
    	this.game.setName(game_name);
    	this.game.setBoardSize(board_size);
    	this.game.setTurn_duration(game_turn_duration);
    }
    
    private void setTeams(JSONObject json) throws JSONException, URISyntaxException
    {
    	List<Team> res = new ArrayList<>();
		JSONArray teams = json.getJSONArray(TEAMS);
    	
		if(teams.length() > 0)
		{
	    	for(int i = 0; i < teams.length(); i++)
	    	{
	    		Team team = new Team();
	    		JSONObject arrayPlayers = teams.getJSONObject(i);
	    		this.setPlayers(arrayPlayers.getJSONArray("players"), team);
	    		res.add(team);
	    	}
		}
		else
		{
			for(int i = 1; i < 3; i++)
			{
				Team team = initTeam(i);
				res.add(team);
			}
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
        Player pl;
        if(game.getPlayer(player.getInt("player_user_id")) == null)
		{
    		pl = new Player(player.getInt("player_user_id"));
		}
        else
        {
        	pl = game.getPlayer(player.getInt("player_user_id"));
        }
    	setLegions(pl, player.getJSONArray("legions"));
    	team.updatePlayer(pl);
    }
    
    private void setLegions(Player player, JSONArray jsonLegions) throws JSONException
    {
    	List<Legion> legions = new ArrayList<>();
    	
    	for(int i = 0; i < jsonLegions.length(); i++)
    	{
    		Legion legion = new Legion(i+1, player);
    		
			String color = jsonLegions.getJSONObject(i).getString("legion_color");
    		String shape = jsonLegions.getJSONObject(i).getString("legion_shape");
    		int position = jsonLegions.getJSONObject(i).getInt("legion_position");
    		
    		legion.setColor(color);
    		legion.setShape(shape);
    		legion.setPosition(position);
    		
    		legions.add(legion);
    	}
    	
    	player.setLegions(legions);
    }
    
    private Team initTeam(int i)
	{
    	Team res = new Team();
    	res.setPlayers(initPlayers());
		return res;
	}

	private List<Player> initPlayers()
	{
		List<Player> res = new ArrayList<>();
		Player p = new Player(0);
		p.setLegions(initLegions(p));
		res.add(p);
		return res;
	}

	private List<Legion> initLegions(Player p)
	{
		List<Legion> res = new ArrayList<>();
		Legion l = new Legion(0, p);
		l.setColor("#FF0000");
		l.setPosition(3);
		l.setShape("square");
		res.add(l);
		return res;
	}
}