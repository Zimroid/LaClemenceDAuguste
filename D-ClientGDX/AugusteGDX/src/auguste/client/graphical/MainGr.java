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
    	setScreen(new GameScreen(this));
    	
    	//Runnable isLogged = new Runnable();
    	
    	setCli(null);
    	try
		{
    		this.cli = Client.getInstance();
	    	this.cli.getInterfaces().add(this);
	    	//setScreen(new GameScreen(this));
			setScreen(new LogScreen(this));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
    }
    
    
	private void setCli(Client instance) {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 1");
	}

	@Override
	public void chatUpdate() {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 2");
	}

	@Override
	public void userUpdate() {
		System.out.println("Accès engine 3");
		
		setScreen(new GameScreen(this));
		//setScreen(new MenuScreen(this)); 
	}

	@Override
	public void createGameUpdate(int id) {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 4");
	}

	@Override
	public void listGameUpdate() {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 5");
	}

	@Override
	public void confirmMessageUpdate() {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 6");
	}

	@Override
	public void gameTurnUpdate(int id) {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 7");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("Accès engine 8");
	}

	@Override
	public void error(String errorType)
	{
		System.out.println("Accès engine 9");
		System.out.println("Erreur engine detectée : " + errorType);
	}

	public Client getCli() {
		return cli;
	}
}