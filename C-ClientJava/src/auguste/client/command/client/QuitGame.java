/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import auguste.client.entity.Client;
import auguste.client.graphical.UpdateListener;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class QuitGame extends CommandClient
{
    public QuitGame(Client client)
    {
        super(client);
    }
    
    public QuitGame()
    {
        super();
    }
    
    @Override
    public void execute()
    {
        super.execute();
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
        this.getJSON().put(COMMAND, LOG_OUT);
    }
}