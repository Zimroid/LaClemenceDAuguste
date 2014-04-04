package auguste.client.graphical;

import java.net.URISyntaxException;

import auguste.client.entity.Client;
//import auguste.client.

import com.badlogic.gdx.Game;

/*
 * Affichage de l'écran d'accueil
 */
public class MainGr extends Game
{
	private Client cli;
	public static String APP_NAME = "Auguste";

    @Override
    public void create() {
    	cli = null;
    	try
		{
			cli = Client.getInstance();
	    	cli.getInterfaces().add(this);
	    	
			setScreen(new MenuScreen(this));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
    }
}
