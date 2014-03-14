/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package august.client.command.server;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class DefaultCommand extends CommandServer
{
    @Override
    public void execute() throws JSONException 
    {
        System.out.println("Commande serveur inconnue");
    }
}