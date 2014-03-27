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
public class GameStart extends CommandClient
{
    public GameStart() throws URISyntaxException
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        this.getJSON().put(COMMAND,GAME_START);
    }
}