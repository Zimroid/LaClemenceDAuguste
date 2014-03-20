/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.manager;

import auguste.client.command.client.AccountCreate;
import auguste.client.command.client.ChatSend;
import auguste.client.command.client.CommandClient;
import auguste.client.command.client.GameCreate;
import auguste.client.command.client.GameJoin;
import auguste.client.command.client.GameList;
import auguste.client.command.client.GameStart;
import auguste.client.command.client.LogIn;
import auguste.client.command.client.LogOut;
import auguste.client.command.client.QuitGame;
import auguste.client.entity.Client;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class CommandClientManager 
{
    public static void executeCommand(Client client, String[] params) throws JSONException
    {
        String command_name = params[0];
                    
        CommandClient command;
        try
        {
            switch(CommandClient.CommandName.valueOf(command_name.toUpperCase()))
            {
                case EXIT:          command = new QuitGame(client);     break;
                case CHAT_SEND:     command = new ChatSend(client);     break;
                case ACCOUNT_CREATE:command = new AccountCreate(client);break;
                case LOG_IN:        command = new LogIn(client);        break;
                case LOG_OUT:       command = new LogOut(client);       break;
                case GAME_CREATE:   command = new GameCreate(client);   break;
                case GAME_LIST:     command = new GameList(client);     break;
                case GAME_JOIN:     command = new GameJoin(client);     break;
                case GAME_START:    command = new GameStart(client);    break;
                default:            command = null;
            }

            command.setArguments(params);
            command.buildJSON();
            command.execute();
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Commande inconnue");
        }
        catch(NullPointerException e)
        {
            System.out.println("Lol NullPointerException : " + e.getMessage());
        }
    }
}