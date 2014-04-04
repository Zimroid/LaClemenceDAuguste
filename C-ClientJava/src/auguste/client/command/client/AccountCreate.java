/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.client;

import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
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
        String login = this.getArguments().get(NAME);
        String password = this.getArguments().get(PASSWORD);
        password = hashPassword(password);
        this.getJSON().put(COMMAND,ACCOUNT_CREATE);
        this.getJSON().put(NAME, login);
        this.getJSON().put(PASSWORD,password);
    }
    
    private static String hashPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes());
            return new String(Hex.encodeHex(digest.digest()));
        }
        catch (NoSuchAlgorithmException e)
        {
            // Algorithme indisponible
            System.out.println(e.getMessage());
            return new String();
        }
    }
}