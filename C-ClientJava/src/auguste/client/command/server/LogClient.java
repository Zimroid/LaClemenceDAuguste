/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class LogClient extends CommandServer
{
    public LogClient(JSONObject json)
    {
        super(json);
    }
    
    public LogClient()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        System.out.println("Bienvenu "+this.getJSON().getString("login"));
    }
}