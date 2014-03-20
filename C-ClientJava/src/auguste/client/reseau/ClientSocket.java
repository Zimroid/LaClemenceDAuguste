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
        private static final String PORT = "47135";
        private static final String PORT_TEST = "16302";
        private static final String CONNECTION_STRING = "ws://"+ADDRESS+":"+PORT_TEST;
        private Client client;
	
	public ClientSocket(URI serverURI)
	{
		super(serverURI);
	}

	public ClientSocket() throws URISyntaxException
	{
		this(new URI(CONNECTION_STRING));
		System.out.println("Client created");
	}
        
        public void setClient(Client client)
        {
            this.client = client;
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
            this.client.messageServerReceive(arg0);
	}

	@Override
	public void onOpen(ServerHandshake arg0)
	{
		System.out.println("Connected");
	}
}