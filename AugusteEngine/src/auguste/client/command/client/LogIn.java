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

    /**
     * Construit le JSON de login.
     * @throws JSONException
     */
    @Override
    public void buildJSON() throws JSONException 
    {
        String login = (String) this.getArguments().get(NAME);
        String password = (String) this.getArguments().get(PASSWORD);
        password = hashPassword(password);
        
        this.getJSON().put(COMMAND, LOG_IN);
        this.getJSON().put(NAME,login);
        this.getJSON().put(PASSWORD,password);
        
        System.out.println(this.getJSON().toString());
    }
    
    /**
     * Crypte une chaine de caractère pour qu'elle puisse être lue par le serveur.
     * @param password La chaine à crypter.
     * @return La chaine cryptée.
     */
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