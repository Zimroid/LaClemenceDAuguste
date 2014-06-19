package auguste.client.reseau;

import java.net.URISyntaxException;

import auguste.client.entity.Client;

public class CommandTransfer implements Runnable
{
	private String json;
	private Client client;
	
	public CommandTransfer(Client client)
	{
		this.client = client;
	}

	@Override
	public void run()
	{
		try
		{
			client.messageServerReceive(this.json);
		}
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setJSON(String json)
	{
		this.json = json;
	}
}