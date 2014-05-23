package auguste.user.userinterface;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import auguste.client.command.client.CommandClient;
import auguste.client.entity.Client;
import auguste.user.error.ErrorWindow;

public class ConnectOnEnter implements KeyListener
{
	private Client engine;
	private ConnexionPanel connexionPanel;

	public ConnectOnEnter(Client engine, ConnexionPanel connexionPanel)
	{
		this.engine = engine;
		this.connexionPanel = connexionPanel;
	}

	@Override
	public void keyPressed(KeyEvent key)
	{
		if(key.getKeyCode() == 10)
		{
			if(this.connexionPanel.getUsername().equals("") || this.connexionPanel.getPassword().equals(""))
			{
				String error = "Nom de compte ou mot de passe vide.";
				new ErrorWindow(error);
			}
			else
			{
				Map<String,Object> command = new HashMap<>();
				
				try
				{
					command.put(CommandClient.COMMAND, CommandClient.LOG_IN);
					command.put(CommandClient.NAME, this.connexionPanel.getUsername());
					command.put(CommandClient.PASSWORD, this.connexionPanel.getPassword());
					this.engine.sendCommand(command);
				}
				catch(Exception e)
				{
					new ErrorWindow(e);
					e.printStackTrace();
				}
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