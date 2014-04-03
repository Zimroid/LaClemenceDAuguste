package auguste.client.graphical;

import com.badlogic.gdx.Game;

/*
 * Affichage de l'écran d'accueil
 */
public class MainGr extends Game implements UpdateListener {
	public static String APP_NAME = "Auguste";

    @Override
    public void create() {
    	setScreen(new MenuScreen(this));
    	//setScreen(new GameScreen(this));
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
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
