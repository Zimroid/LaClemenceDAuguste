/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package august.client.command.client;

import auguste.client.Client;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class GameStart extends CommandClient
{
    public GameStart(Client client)
    {
        super(client);
    }
    
    public GameStart()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        this.getJSON().put(COMMAND,GAME_START);
    }
}