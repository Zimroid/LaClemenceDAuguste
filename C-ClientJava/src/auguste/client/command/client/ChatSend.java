/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;

/**
 * Classe qui représente le comportement d'une commande d'envoi de message
 * @author Evinrude
 */
public class ChatSend extends CommandClient
{
    public ChatSend() throws URISyntaxException
    {
        super();
    }

    /**
     * Construit un JSON avec en paramètre : 
     * command : chat_send,
     * message : le corps du message,
     * game_id : l'id de la partie concernée
     * @throws JSONException
     */
    @Override
    public void buildJSON() throws JSONException
    {
        String msg = this.getArguments().get("message");
        int gameId = 0;
        
        if(this.getArguments().containsKey("room_id"))
        {
            gameId = Integer.parseInt(this.getArguments().get("room_id"));
        }
        
        this.getJSON().put(COMMAND, CHAT_SEND);
        this.getJSON().put("message", msg);
        if(gameId != 0)
        {
            this.getJSON().put(ROOM_ID,gameId);
        }
    }
}