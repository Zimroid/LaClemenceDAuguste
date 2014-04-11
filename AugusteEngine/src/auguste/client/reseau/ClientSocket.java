/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.reseau;

import auguste.client.entity.Client;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author Evinrude
 */
public class ClientSocket extends WebSocketClient
{
	private static final String ADDRESS = "130.79.214.172";
	//private static final String PORT = "47135";
	private static final String PORT = "16302";
	private static final String CONNECTION_STRING = "ws://"+ADDRESS+":"+PORT;
	
	private static ClientSocket INSTANCE;
        
	private ClientSocket(URI serverURI)
	{
		super(serverURI);
	}

	private ClientSocket() throws URISyntaxException
	{
		this(new URI(CONNECTION_STRING));
		System.out.println("Client created");
	}
        
    public static ClientSocket getInstance() throws URISyntaxException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new ClientSocket();
        }
        return INSTANCE;
    }

	@Override
	public void onClose(int arg0, String arg1, boolean arg2)
	{
		System.out.println("Connection closed");
	}

	@Override
	public void onError(Exception e)
	{
		System.out.println(e.getMessage());
	}

	@Override
	public void onMessage(String arg0)
	{
		try
		{
			Client.getInstance().messageServerReceive(arg0);
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onOpen(ServerHandshake arg0)
	{
		System.out.println("Connected with " + arg0.getHttpStatusMessage());
	}
}