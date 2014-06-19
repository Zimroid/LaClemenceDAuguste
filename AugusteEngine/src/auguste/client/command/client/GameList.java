/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class GameList extends CommandClient
{    
    public GameList() throws URISyntaxException
    {
        super();
    }

    /**
     * Construit le JSON qui servira à récupérer les parties disponibles sur le serveur.
     * @throws JSONException
     */
    @Override
    public void buildJSON() throws JSONException 
    {
        this.getJSON().put(COMMAND,GAME_LIST);
    }
}