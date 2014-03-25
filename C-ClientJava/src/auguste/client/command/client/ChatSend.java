/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import auguste.client.entity.Client;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        String msg = "";
        for(int i = 1; i<this.getArguments().length; i++)
        {
            msg = msg + " " + this.getArguments()[i];
        }
        int gameId = 0;
        try 
        {
            gameId = Client.getInstance().getCurrentGame().getId();
        } 
        catch (URISyntaxException ex) 
        {
            Logger.getLogger(ChatSend.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.getJSON().put(COMMAND, CHAT_SEND);
        this.getJSON().put("message", msg);
        this.getJSON().put("game_id",gameId);
    }
}