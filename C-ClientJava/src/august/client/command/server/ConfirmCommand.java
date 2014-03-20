/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package august.client.command.server;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class ConfirmCommand extends CommandServer
{
    public ConfirmCommand(JSONObject json)
    {
        super(json);
    }
    
    public ConfirmCommand()
    {
        super();
    }

    @Override
    public void execute() throws JSONException
    {
        String confirm_msg = this.getJSON().getString("type");
        System.out.println(confirm_msg);
    }
}