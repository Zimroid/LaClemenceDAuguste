/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameCreate extends CommandClient
{
    public GameCreate() throws URISyntaxException
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        String game_name = this.getArguments()[1];
        boolean cards = false;
        int turn_timer = 20;
        int board_size = 8;
        int number_of_team = 2;
        int legion_per_player = 8;
        
        if(this.getArguments().length>2)
        {
            turn_timer = Integer.parseInt(this.getArguments()[2]);            
        }
        
        if(this.getArguments().length>3)
        {
            board_size = Integer.parseInt(this.getArguments()[3]);
        }
        
        if(this.getArguments().length>4)
        {
            switch(this.getArguments()[4])
            {
                case "1":
                    cards=true;
                    break;
                case "true":
                    cards=true;
            }
        }
        
        if(this.getArguments().length>5)
        {
            number_of_team = Integer.parseInt(this.getArguments()[5]);
        }
        
        if(this.getArguments().length>6)
        {
            legion_per_player = Integer.parseInt(this.getArguments()[6]);
        }

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