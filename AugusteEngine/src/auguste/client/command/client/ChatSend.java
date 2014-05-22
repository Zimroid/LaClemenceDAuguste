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
        String msg = (String) this.getArguments().get(MESSAGE);
        int gameId = 0;
        
        if(this.getArguments().containsKey(ROOM_ID))
        {
            gameId = (Integer) this.getArguments().get(ROOM_ID);
        }
        
        this.getJSON().put(COMMAND, CHAT_SEND);
        this.getJSON().put(MESSAGE, msg);
        if(gameId != 0)
        {
            this.getJSON().put(ROOM_ID,gameId);
        }
    }
}