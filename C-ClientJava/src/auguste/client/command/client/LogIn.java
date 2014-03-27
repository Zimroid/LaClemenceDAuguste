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
 *
 * @author Evinrude
 */
public class LogIn extends CommandClient
{
    public LogIn() throws URISyntaxException
    {
        super();
    }

    @Override
    public void buildJSON() throws JSONException 
    {
        String login = this.getArguments().get("login");
        String password = this.getArguments().get("password");
        password = hashPassword(password);
        
        this.getJSON().put(COMMAND, LOG_IN);
        this.getJSON().put("name",login);
        this.getJSON().put("password",password);
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