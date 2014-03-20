/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.manager;

import auguste.client.command.server.ChatMessage;
import auguste.client.command.server.CommandServer;
import auguste.client.command.server.ConfirmCommand;
import auguste.client.command.server.GameAvailable;
import auguste.client.command.server.LogClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class CommandServerManager 
{
    public static void executeCommand(String param)
    {
        try 
        {
            System.out.println("Message Received : " + param);
            JSONObject json = new JSONObject(param);
            String command_name  = json.getString("command");

            CommandServer command;
            switch (CommandServer.CommandName.valueOf(command_name.toUpperCase()))
            {
                case CHAT_MESSAGE:  command = new ChatMessage(json);    break;
                case CONFIRM_LOG:   command = new LogClient(json);      break;
                case GAME_AVAILABLE:command = new GameAvailable(json);  break;
                case CONFIRM:       command = new ConfirmCommand(json); break;
                default:            command = null;                     break;
            }
            command.execute();

        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Commande inconnue : " + e.getMessage());
        } 
        catch(JSONException e)
        {
            System.out.println("Erreur dans le JSON : " + e.getMessage());
        }
    }
}