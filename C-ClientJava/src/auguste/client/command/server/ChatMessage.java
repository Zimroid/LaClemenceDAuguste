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
public class ChatMessage extends CommandServer
{
    public ChatMessage(JSONObject json)
    {
        super(json);
    }
    
    public ChatMessage()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        String author = this.getJSON().getString("author");
        String message= this.getJSON().getString("text");
        System.out.println("Message received from "+author+" : "+message);
    }
}