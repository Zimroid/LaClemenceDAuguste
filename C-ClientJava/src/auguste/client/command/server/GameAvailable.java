/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameAvailable extends CommandServer
{
    public GameAvailable(JSONObject json)
    {
        super(json);
    }
    
    public GameAvailable()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        JSONArray game_array = this.getJSON().getJSONArray("games");
        System.out.println("Liste des parties en cours : ");
        for(int i = 0; i<game_array.length(); i++)
        {
            JSONObject game = game_array.getJSONObject(i);
            int game_id = game.getInt("id");
            String game_name = game.getString("name");
            int board_size = game.getInt("board_size");

            System.out.println(game_id+" Nom :  "+game_name+" Board size :  "+board_size);
        }
    }
}