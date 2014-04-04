/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class LogOut extends CommandClient
{
    public LogOut() throws URISyntaxException
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        this.getClient().setUser(null);
        this.getJSON().put(COMMAND, LOG_OUT);        
    }
}