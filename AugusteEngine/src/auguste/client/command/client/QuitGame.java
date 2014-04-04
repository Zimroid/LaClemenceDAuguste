/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import auguste.client.interfaces.UpdateListener;

import java.net.URISyntaxException;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class QuitGame extends CommandClient
{
    public QuitGame() throws URISyntaxException
    {
        super();
    }
    
    @Override
    public void execute()
    {
        if(this.getClient().getUser() != null)
        {
            this.getClient().getClientSocket().send(this.getJSON().toString());
        }
        System.out.println("Exiting the application");
        for(UpdateListener ul : client.getInterfaces())
        {
            ul.stop();
        }
        this.getClient().getClientSocket().close();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        if(this.getClient().getUser() != null)
        {
            this.getJSON().put(COMMAND, LOG_OUT);
        }
    }
}