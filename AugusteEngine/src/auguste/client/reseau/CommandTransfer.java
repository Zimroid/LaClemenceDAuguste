package auguste.client.reseau;

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
		client.messageServerReceive(this.json);
	}
	
	public void setJSON(String json)
	{
		this.json = json;
	}
}