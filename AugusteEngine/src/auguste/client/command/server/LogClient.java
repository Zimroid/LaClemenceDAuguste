/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.server;

import auguste.client.entity.User;
import auguste.client.interfaces.UpdateListener;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class LogClient extends CommandServer
{    
    public static final String NAME =   "name";
    public LogClient()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        User user = new User();
        user.setName(this.getJSON().getString(NAME));
        
        this.getClient().setUser(user);
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.userUpdate();
        }
    }
}