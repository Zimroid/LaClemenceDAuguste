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
    
    /**
     * Récupère le JSON associé à une commande serveur.
     * @return Le JSON associé à la commande serveur.
     */
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    /**
     * Définie le JSON associé à une commande serveur.
     * @param json Le JSON à associer.
     */
    public void setJSON(JSONObject json)
    {
        this.json = json;
    }
    
    /**
     * Récupère le client associé à une commande serveur.
     * @return Le client associé à une commande serveur.
     */
    public Client getClient()
    {
        return this.client;
    }
    
    /**
     * Définie le client associé à une commande serveur.
     * @param client Le client à associer.
     */    
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