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
public class LogIn extends CommandClient
{
    public LogIn(Client client)
    {
        super(client);
    }
    
    public LogIn()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        String login = this.getArguments()[1];
        String password = this.getArguments()[2];
        
        this.getJSON().put(COMMAND, LOG_IN);
        this.getJSON().put("name",login);
        this.getJSON().put("password",password);
    }
}