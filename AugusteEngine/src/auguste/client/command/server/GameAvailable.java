/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import auguste.client.entity.Game;
import auguste.client.interfaces.UpdateListener;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameAvailable extends CommandServer
{    
    public static final String LIST =       	"games";
    public static final String ROOM_ID =    	"room_id";
    public static final String GAME_NAME =  	"game_name";
    public static final String BOARD_SIZE =		"game_board_size";
    public static final String TURN_DURATION =	"game_turn_duration";
    public static final String GAME_STATE =		"game_state";
    public static final String GAME_MODE =		"game_mode";
    public static final String NUMBER_OF_PLAYER="players_number";
    
    public GameAvailable()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        List<Game> games = new ArrayList<>();
        JSONArray game_array = this.getJSON().getJSONArray(LIST);
        for(int i = 0; i<game_array.length(); i++)
        {
            Game g = new Game();
            JSONObject game = game_array.getJSONObject(i);
            int game_id = game.getInt(ROOM_ID);
            String game_name = game.getString(GAME_NAME);
            int board_size = game.getInt(BOARD_SIZE);
            long turn_duration = game.getLong(TURN_DURATION);
            String gameState = game.getString(GAME_STATE);
            String gameMode = game.getString(GAME_MODE);
            int playerNumber = game.getInt(NUMBER_OF_PLAYER);
            
            g.setId(game_id);
            g.setName(game_name);
            g.setBoardSize(board_size);
            g.setTurn_duration(turn_duration);
            g.setGameState(gameState);
            g.setGameMode(gameMode);
            g.setNumberOfPlayer(playerNumber);
            
            games.add(g);
        }
        this.getClient().setGameAvailable(games);
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.listGameUpdate();
        }
    }
}