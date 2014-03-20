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
public class ChatSend extends CommandClient
{
    public ChatSend(Client client)
    {
        super(client);
    }
    
    public ChatSend()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException
    {
        String msg = "";
        for(int i = 1; i<this.getArguments().length; i++)
        {
            msg = msg + " " + this.getArguments()[i];
        }
        
        this.getJSON().put(COMMAND, CHAT_SEND);
        this.getJSON().put("message", msg);
    }
}