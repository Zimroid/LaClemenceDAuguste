/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import java.net.URISyntaxException;

import auguste.client.entity.Client;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public abstract class CommandServer 
{
    private JSONObject json;
    private Client client;
    
    public abstract void execute() throws JSONException, URISyntaxException;
        
    public CommandServer()
    {}
    
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    public void setJSON(JSONObject json)
    {
        this.json = json;
    }
    
    public Client getClient()
    {
        return this.client;
    }
    
    public void setClient(Client client)
    {
        this.client = client;
    }
    
    public enum CommandName
    {
        CHAT_MESSAGE,
        MESSAGE_CONFIRM,
        LIST_GAMES,
        LOG_CONFIRM,
        GAME_TURN,
        GAME_CONFIRM,
        GAME_USERS,
        MESSAGE_ERROR
    }
}