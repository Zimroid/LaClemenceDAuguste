/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import org.json.JSONObject;
import auguste.client.entity.Client;
import java.net.URISyntaxException;
import java.util.Map;
import org.json.JSONException;

/**
 * Classe qui représente le comportement d'une commande que le client envoie
 * au serveur
 * @author Evinrude
 */
public abstract class CommandClient
{
    private final JSONObject json;
    private Map<String,String> args;
    Client client;
    
    // Noms de commandes possibles
    public static final String GAME_CREATE      = "game_create";
    public static final String GAME_LIST        = "game_list";
    public static final String GAME_JOIN        = "game_join";
    public static final String GAME_START       = "game_start";
    public static final String GAME_LEAVE       = "game_leave";
    public static final String GAME_CONFIG      = "game_config";
    public static final String COMMAND          = "command";
    public static final String EXIT             = "exit";
    public static final String CHAT_SEND        = "chat_send";
    public static final String ACCOUNT_CREATE   = "account_create";
    public static final String LOG_IN           = "log_in";
    public static final String LOG_OUT          = "log_out";
    public static final String GAME_TURN        = "game_turn";
    
    // Noms de paramètres possibles
    public static final String ROOM_ID              = "room_id";
    public static final String PLAYER_NUMBER        = "player_number";
    public static final String MESSAGE              = "message";
    public static final String NAME                 = "name";
    public static final String PASSWORD             = "password";
    public static final String GAME_NAME            = "game_name";
    public static final String TURN_TIMER           = "turn_timer";
    public static final String BOARD_SIZE           = "board_size";
    public static final String CARDS                = "cards";
    public static final String NUMBER_OF_TEAM       = "number_of_team";
    public static final String LEGION_PER_PLAYER    = "legion_per_player";
    public static final String PAWN_ID              = "pawn_id";
    public static final String POS_BEG              = "pos_beg";
    public static final String POS_END              = "pos_end";
    
    
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
    public void setArguments(Map<String,String> s)
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
    public Map<String,String> getArguments()
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
        GAME_CONFIG,
        GAME_JOIN,
        GAME_LIST,
        GAME_START,
        GAME_LEAVE,
        GAME_TURN,
        LOG_IN,
        LOG_OUT,
        EXIT
    }
}