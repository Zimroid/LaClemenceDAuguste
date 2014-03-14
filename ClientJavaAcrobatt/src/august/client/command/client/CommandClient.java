/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package august.client.command.client;

import org.json.JSONObject;
import auguste.client.Client;
import auguste.command.client.exception.UnknownCommandException;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public abstract class CommandClient
{
    private JSONObject json;
    private String[] args;
    private Client client;
    protected static final String GAME_CREATE = "game_create";
    protected static final String GAME_LIST = "game_list";
    protected static final String GAME_JOIN = "game_join";
    protected static final String GAME_START = "game_start";
    protected static final String COMMAND = "command";
    protected static final String EXIT = "exit";
    protected static final String CHAT_SEND = "chat_send";
    protected static final String ACCOUNT_CREATE = "account_create";
    protected static final String LOG_IN = "log_in";
    protected static final String LOG_OUT = "log_out";
    
    public abstract void buildJSON() throws JSONException;
    
    public CommandClient()
    {
        this(null);
    }
    
    public CommandClient(Client client)
    {
        this.json = new JSONObject();
        this.client = client;
    }
    
    public void setClient(Client client)
    {
        this.client = client;
    }
    
    public Client getClient()
    {
        return this.client;
    }
    
    public void setArguments(String[] s)
    {
        this.args = s;
    }
    
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    public String[] getArguments()
    {
        return this.args;
    }
    
    public void execute() 
    {
        this.getClient().send(this.getJSON().toString());        
    }
    
    public enum CommandName
    {
        ACCOUNT_CREATE,
        CHAT_SEND,
        GAME_CREATE,
        GAME_JOIN,
        GAME_LIST,
        GAME_START,
        LOG_IN,
        LOG_OUT,
        EXIT
    }
}