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
 * Classe qui représente le comportement d'une commande de création de partie.
 * @author Evinrude
 */
public class GameCreate extends CommandClient
{
    public GameCreate() throws URISyntaxException
    {
        super();
    }

    /**
     * 
     * @throws JSONException
     */
    @Override
    public void buildJSON() throws JSONException 
    {
        String game_name = this.getArguments().get(GAME_NAME);
        
//        if(this.getArguments().containsKey(TURN_TIMER))
//        {
//            turn_timer = Integer.parseInt(this.getArguments().get(TURN_TIMER));            
//        }
//        
//        if(this.getArguments().containsKey(BOARD_SIZE))
//        {
//            board_size = Integer.parseInt(this.getArguments().get(BOARD_SIZE));
//        }
//        
//        if(this.getArguments().containsKey(CARDS))
//        {
//            switch(this.getArguments().get(CARDS))
//            {
//                case "1":
//                    cards=true;
//                    break;
//                case "true":
//                    cards=true;
//            }
//        }
//        
//        if(this.getArguments().containsKey(NUMBER_OF_TEAM))
//        {
//            number_of_team = Integer.parseInt(this.getArguments().get(NUMBER_OF_TEAM));
//        }
//        
//        if(this.getArguments().containsKey(LEGION_PER_PLAYER))
//        {
//            legion_per_player = Integer.parseInt(this.getArguments().get(LEGION_PER_PLAYER));
//        }

        JSONObject json = this.getJSON();
        json.put(COMMAND,GAME_CREATE);
        json.put(GAME_NAME,game_name);
    }
}