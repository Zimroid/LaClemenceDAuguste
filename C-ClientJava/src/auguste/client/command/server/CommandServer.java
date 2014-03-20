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
public abstract class CommandServer 
{
    
    private JSONObject json;
    
    public abstract void execute() throws JSONException;
    
    public CommandServer(JSONObject json)
    {
        this.json = json;
    }
    
    public CommandServer()
    {
        this(null);
    }
    
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    public void setJSON(JSONObject json)
    {
        this.json = json;
    }
    
    public enum CommandName
    {
        CHAT_MESSAGE,
        CONFIRM,
        GAME_AVAILABLE,
        CONFIRM_LOG
    }
}