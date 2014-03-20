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
public class LogOut extends CommandClient
{
    public LogOut(Client client)
    {
        super(client);
    }
    
    public LogOut()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        this.getJSON().put(COMMAND, LOG_OUT);        
    }
}