/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import org.json.JSONObject;
import auguste.client.entity.Client;
import java.net.URISyntaxException;
import org.json.JSONException;

/**
 * Classe qui représente le comportement d'une commande que le client envoie
 * au serveur
 * @author Evinrude
 */
public abstract class CommandClient
{
    private JSONObject json;
    private String[] args;
    Client client;
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
    
    public CommandClient() throws URISyntaxException
    {
        this.json = new JSONObject();
        this.client = Client.getInstance();
    }
    
    /**
     *
     * @return Le client concerné par la commande.
     */
    public Client getClient()
    {
        return this.client;
    }
    
    /**
     *
     * @param s Le premier argument est le nom de la commande, le reste change en fonction de la commande concernée.
     */
    public void setArguments(String[] s)
    {
        this.args = s;
    }
    
    /**
     *
     * @return Le JSON contruit par la commande.
     */
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    /**
     *
     * @return Les arguments de la commande.
     */
    public String[] getArguments()
    {
        return this.args;
    }
    
    /**
     * Envoie le JSON vers le serveur.
     */
    public void execute() 
    {
        this.getClient().getClientSocket().send(this.getJSON().toString());
    }
    
    /**
     * Liste des commandes disponible sur le client.
     */
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