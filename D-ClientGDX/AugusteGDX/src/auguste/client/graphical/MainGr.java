package auguste.client.graphical;

import com.badlogic.gdx.Game;

/*
 * Affichage de l'écran d'accueil
 */
public class MainGr extends Game {
	public static String APP_NAME = "Auguste";

    @Override
    public void create() {
    	setScreen(new MenuScreen(this));
    	setScreen(new GameScreen(this));
    }
}
