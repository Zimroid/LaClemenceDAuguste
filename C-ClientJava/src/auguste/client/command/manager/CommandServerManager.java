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
import auguste.client.command.server.GameConfirm;
import auguste.client.command.server.LogClient;
import auguste.client.entity.Client;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Récupère un message du serveur et l'embranche sur la bonne commande.
 * @author Evinrude
 */
public class CommandServerManager 
{

    /**
     *
     * @param client
     * @param param
     *      La chaine qui contient la commande et ses paramètres.
     */
    public static void executeCommand(Client client, String param)
    {
        try 
        {
            System.out.println("Message Received : " + param);
            JSONObject json = new JSONObject(param);
            String command_name  = json.getString("command");

            CommandServer command;
            switch (CommandServer.CommandName.valueOf(command_name.toUpperCase()))
            {
                case CHAT_MESSAGE:      command = new ChatMessage();    break;
                case LOG_CONFIRM:       command = new LogClient();      break;
                case GAME_AVAILABLE:    command = new GameAvailable();  break;
                case MESSAGE_CONFIRM:   command = new ConfirmCommand(); break;
                case GAME_CONFIRM:      command = new GameConfirm();     break;
                default:                command = null;                 break;
            }
            command.setClient(client);
            command.setJSON(json);
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