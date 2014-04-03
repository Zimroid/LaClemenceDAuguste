/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import auguste.client.entity.ChatMessageReceived;
import auguste.client.graphical.UpdateListener;
import java.util.Date;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class ChatMessage extends CommandServer
{    
    public ChatMessage()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        String author = this.getJSON().getString("author");
        String message= this.getJSON().getString("text");
        Date date = new Date(this.getJSON().getLong("date"));
        
        ChatMessageReceived cmr = new ChatMessageReceived();
        cmr.setAuthor(author);
        cmr.setMessage(message);
        cmr.setDate(date);
        
        this.getClient().getChatMessageReceived().add(cmr);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.chatUpdate();
        }
    }
}