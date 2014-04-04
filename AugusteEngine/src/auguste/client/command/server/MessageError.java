package auguste.client.command.server;

import org.json.JSONException;

import auguste.client.interfaces.UpdateListener;

public class MessageError extends CommandServer
{
	public static final String TYPE = "type";
	public MessageError() 
	{
		super();
	}

	@Override
	public void execute() throws JSONException 
	{
		String error_type = this.getJSON().getString(TYPE);
		for(UpdateListener ul : this.getClient().getInterfaces())
		{
			ul.error(error_type);
		}
	}
}