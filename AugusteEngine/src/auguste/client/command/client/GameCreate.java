/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe qui représente le comportement d'une commande de création de partie.
 * @author Evinrude
 */
public class GameCreate extends CommandClient
{
    public GameCreate() throws URISyntaxException
    {
        super();
    }

    /**
     * 
     * @throws JSONException
     */
    @Override
    public void buildJSON() throws JSONException 
    {
        String game_name = (String) this.getArguments().get(GAME_NAME);
        String game_type = (String) this.getArguments().get(GAME_TYPE);

        JSONObject json = this.getJSON();
        json.put(COMMAND,GAME_CREATE);
        json.put(GAME_NAME,game_name);
        json.put(GAME_TYPE, game_type);
        System.out.println(json.toString());
    }
}