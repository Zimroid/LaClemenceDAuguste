/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import auguste.client.entity.Game;
import auguste.client.graphical.UpdateListener;
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
    public GameAvailable()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        List<Game> games = new ArrayList<Game>();
        JSONArray game_array = this.getJSON().getJSONArray("games");
        for(int i = 0; i<game_array.length(); i++)
        {
            Game g = new Game();
            JSONObject game = game_array.getJSONObject(i);
            int game_id = game.getInt("id");
            String game_name = game.getString("name");
            int board_size = game.getInt("board_size");
            
            g.setId(game_id);
            g.setName(game_name);
            g.setBoardSize(board_size);
            
            games.add(g);
        }
        this.getClient().setGameAvailable(games);
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.listGameUpdate();
        }
    }
}