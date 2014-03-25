/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import org.json.JSONException;

/**
 * Classe qui représente le comportement d'une commande de création de compte.
 * @author Evinrude
 */
public class AccountCreate extends CommandClient
{    
    public AccountCreate() throws URISyntaxException
    {
        super();
    }

    /**
     * Construit un JSON avec en paramètre :
     * command : account_create,
     * name: login du compte,
     * password : le mot de passe du compte
     * @throws JSONException
     */
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