package auguste.client.graphical;

import java.net.URISyntaxException;

import auguste.client.entity.Client;
import auguste.client.interfaces.UpdateListener;

import com.badlogic.gdx.Game;

/*
 * Affichage de l'écran d'accueil
 */
public class MainGr extends Game implements UpdateListener
{
	private Client cli;
	public static String APP_NAME = "Auguste";

    @Override
    public void create() {
    	setCli(null);
    	try
		{
    		this.cli = Client.getInstance();
	    	this.cli.getInterfaces().add(this);
	    	
			setScreen(new MenuScreen(this));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
    }

	private void setCli(Client instance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chatUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createGameUpdate(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listGameUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmMessageUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameTurnUpdate(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String errorType) {
		// TODO Auto-generated method stub
		
	}

	public Client getCli() {
		return cli;
	}
}
