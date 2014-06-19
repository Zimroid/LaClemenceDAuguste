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
    public static final String NAME =   "user_name";
    public static final String ID =		"user_id";
    public LogClient()
    {
        super();
    }

    @Override
    public void execute() throws JSONException 
    {
        User user = new User();
        user.setName(this.getJSON().getString(NAME));
        user.setId(this.getJSON().getInt(ID));
        
        this.getClient().setUser(user);
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.userUpdate();
        }
    }
}