/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import auguste.client.entity.Client;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameJoin extends CommandClient
{
    public GameJoin(Client client)
    {
        super(client);
    }
    
    public GameJoin()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        this.getJSON().put(COMMAND,GAME_JOIN);
        this.getJSON().put("game_name",this.getArguments()[1]);
    }
}