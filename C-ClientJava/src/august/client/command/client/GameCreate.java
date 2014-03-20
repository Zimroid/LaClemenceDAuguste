/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package august.client.command.client;

import auguste.client.Client;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameCreate extends CommandClient
{
    public GameCreate(Client client)
    {
        super(client);
    }
    
    public GameCreate()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        String game_name = this.getArguments()[1];
        int turn_timer = Integer.parseInt(this.getArguments()[2]);
        int board_size = Integer.parseInt(this.getArguments()[3]);
        boolean cards = false;
        switch(this.getArguments()[4])
        {
            case "1":
                cards=true;
                break;
            case "true":
                cards=true;
        }
        int number_of_team = Integer.parseInt(this.getArguments()[5]);
        int legion_per_player = Integer.parseInt(this.getArguments()[6]);

        JSONObject json = this.getJSON();
        json.put(COMMAND,GAME_CREATE);
        json.put("game_name",game_name);
        json.put("board_size",board_size);
        json.put("cards",cards);
        json.put("teams",number_of_team);
        json.put("legion_per_player",legion_per_player);
        json.put("turn_timer",turn_timer);
    }
}