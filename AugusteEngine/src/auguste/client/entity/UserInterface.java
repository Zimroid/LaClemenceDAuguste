package auguste.client.entity;

import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;

import auguste.client.interfaces.UpdateListener;

public abstract class UserInterface implements UpdateListener
{
	private int id;
	private Client client;
	
	public UserInterface(int id, Client client)
	{
		this.id = id;
		this.client = client;
	}
	
	protected int getId()
	{
		return this.id;
	}
	
	protected Client getClient()
	{
		return this.client;
	}
	
	public void sendCommand(Map<String,?> command) throws JSONException, URISyntaxException
	{
		client.sendCommand(command);
	}
	
	/**
	 *  Il faut créer les méthodes d'accès aux données du client de façon à ce qu'un UI ne puisse récupérer que les données qui lui sont dédiées
	 */
}