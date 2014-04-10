/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.command.manager;

import auguste.client.command.client.AccountCreate;
import auguste.client.command.client.ChatSend;
import auguste.client.command.client.CommandClient;
import auguste.client.command.client.GameConfig;
import auguste.client.command.client.GameCreate;
import auguste.client.command.client.GameJoin;
import auguste.client.command.client.GameLeave;
import auguste.client.command.client.GameList;
import auguste.client.command.client.GameStart;
import auguste.client.command.client.LogIn;
import auguste.client.command.client.LogOut;
import auguste.client.command.client.QuitGame;
import auguste.client.entity.Client;

import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class CommandClientManager 
{
	public static void executeCommand(Client client, Map<String,?> params) throws JSONException, URISyntaxException
    {
        String command_name = (String) params.get("command");
                    
        CommandClient command;
        try
        {
            switch(CommandClient.CommandName.valueOf(command_name.toUpperCase()))
            {
                case EXIT:          command = new QuitGame();       break;
                case CHAT_SEND:     command = new ChatSend();       break;
                case ACCOUNT_CREATE:command = new AccountCreate();  break;
                case LOG_IN:        command = new LogIn();          break;
                case LOG_OUT:       command = new LogOut();         break;
                case ROOM_CREATE:   command = new GameCreate();     break;
                case GAME_LIST:     command = new GameList();       break;
                case GAME_JOIN:     command = new GameJoin();       break;
                case GAME_START:    command = new GameStart();      break;
                case GAME_LEAVE:    command = new GameLeave();      break;
                case GAME_CONFIG:   command = new GameConfig();     break;
                default:            command = null;
            }

            command.setArguments(params);
            command.buildJSON();
            command.execute();
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Commande inconnue : " + e.getMessage());
        }
        catch(NullPointerException e)
        {
            System.out.println("Lol NullPointerException : " + e.getMessage());
        }
    }
}