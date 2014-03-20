/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import auguste.client.entity.Client;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class AccountCreate extends CommandClient
{
    public AccountCreate(Client client)
    {
        super(client);
    }
    
    public AccountCreate()
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException
    {
        String login = this.getArguments()[1];
        String password = this.getArguments()[2];
        this.getJSON().put(COMMAND,ACCOUNT_CREATE);
        this.getJSON().put("name", login);
        this.getJSON().put("password",password);
    }
}