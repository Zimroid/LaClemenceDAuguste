package auguste.user.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import auguste.client.command.client.CommandClient;
import auguste.client.entity.Client;
import auguste.user.error.ErrorWindow;

public class SendMessageListener implements ActionListener
{
	private Map<String, Object> command;
	private Client engine;
	private ChatWindow cw;
	
	public SendMessageListener(Client engine, ChatWindow cw)
	{
		this.command = new HashMap<>();
		this.engine = engine;
		this.cw = cw;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(!cw.getMessage().equals(""))
		{
			this.command.put(CommandClient.COMMAND, CommandClient.CHAT_SEND);
			this.command.put(CommandClient.MESSAGE, cw.getMessage());
			this.command.put(CommandClient.ROOM_ID, cw.getRoomId());
			this.cw.clearChatText();
			try
			{
				this.engine.sendCommand(this.command);
			}
			catch (Exception e)
			{
				new ErrorWindow(e);
				e.printStackTrace();
			}
		}
	}
}