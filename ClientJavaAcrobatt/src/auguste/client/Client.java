package auguste.client;
import august.client.command.server.ChatMessage;
import august.client.command.server.CommandServer;
import august.client.command.server.ConfirmCommand;
import august.client.command.server.DefaultCommand;
import august.client.command.server.GameAvailable;
import august.client.command.server.LogClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client extends WebSocketClient
{
        private static final String ADDRESS = "130.79.214.172";
        private static final String PORT = "47135";
        private static final String PORT_TEST = "16302";
        private static final String CONNECTION_STRING = "ws://"+ADDRESS+":"+PORT_TEST;        
	
	public Client(URI serverURI)
	{
		super(serverURI);
	}

	public Client() throws URISyntaxException
	{
		super(new URI(CONNECTION_STRING));
		System.out.println("Client created");
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2)
	{
		System.out.println("Connection closed");
	}

	@Override
	public void onError(Exception e)
	{
		e.printStackTrace();
	}

	@Override
	public void onMessage(String arg0)
	{
            try 
            {
                System.out.println("Message Received : " + arg0);
                JSONObject json = new JSONObject(arg0);
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

	@Override
	public void onOpen(ServerHandshake arg0)
	{
		System.out.println("Connected");
	}
}