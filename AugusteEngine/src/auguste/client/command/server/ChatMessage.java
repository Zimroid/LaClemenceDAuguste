/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import auguste.client.entity.ChatMessageReceived;
import auguste.client.entity.UserInterfaceManager;
import auguste.client.interfaces.UpdateListener;

import java.util.Date;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class ChatMessage extends CommandServer
{    
    public static final String AUTHOR =     "user_name";
    public static final String TEXT =       "text";
    public static final String DATE =       "date";
    public static final String GAME_ID =	"room_id";
    
    private UserInterfaceManager IUM;
    
    public ChatMessage()
    {
        super();
        this.IUM = UserInterfaceManager.getInstance();
    }

    /**
     * Récupère un message de chat d'un JSON. Le message est classé en fonction de sa room. Une notification est envoyée aux interfaces utilisateurs.
     * @throws JSONException
     */
    @Override
    public void execute() throws JSONException 
    {
        String author = this.getJSON().getString(AUTHOR);
        String message= this.getJSON().getString(TEXT);
        Date date = new Date(this.getJSON().getLong(DATE));
        int id = 0;
        
        if(this.getJSON().has(GAME_ID))
        {
        	id = this.getJSON().getInt(GAME_ID);
        }
        
        ChatMessageReceived cmr = new ChatMessageReceived();
        cmr.setAuthor(author);
        cmr.setMessage(message);
        cmr.setDate(date);

        IUM.addMessageChat(id, cmr);
        IUM.fillQueueChatMessage(id);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.chatUpdate(id);
        }
    }
}