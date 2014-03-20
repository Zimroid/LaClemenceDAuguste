package clientjavaacrobatt;
import august.client.command.client.AccountCreate;
import august.client.command.client.ChatSend;
import august.client.command.client.CommandClient;
import august.client.command.client.GameCreate;
import august.client.command.client.GameJoin;
import august.client.command.client.GameList;
import august.client.command.client.GameStart;
import august.client.command.client.LogIn;
import august.client.command.client.LogOut;
import august.client.command.client.QuitGame;
import auguste.client.Client;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import org.json.JSONException;

public class Main
{	
        private static boolean stop;
        private static Client client;
        
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{
		client = new Client();
		client.connect();
                
                stop = false;
                
                while(!stop) //
                {
                    //System.out.println("Enter COMMAND :\n");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    String[] words = line.split(" ");
                    String command_name = words[0];
                    
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
                        
                        command.setArguments(words);
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
        
        public static void setStop(boolean b)
        {
            stop = b;
        }
}