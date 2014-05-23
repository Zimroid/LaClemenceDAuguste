package auguste.user.chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import auguste.client.command.client.CommandClient;
import auguste.client.entity.Client;
import auguste.user.error.ErrorWindow;

public class SendMessageOnEnterListener implements KeyListener
{
	private Map<String, Object> command;
	private Client engine;
	private ChatWindow cw;

	public SendMessageOnEnterListener(Client engine, ChatWindow cw)
	{
		this.command = new HashMap<>();
		this.engine = engine;
		this.cw = cw;
	}

	@Override
	public void keyPressed(KeyEvent key)
	{
		if(key.getKeyCode() == 10 && !cw.getMessage().equals(""))
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

	@Override
	public void keyReleased(KeyEvent arg0)
	{}

	@Override
	public void keyTyped(KeyEvent arg0)
	{}

}