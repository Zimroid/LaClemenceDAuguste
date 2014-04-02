package auguste.client.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
	private final float RAC = (float) (Math.sqrt(3)/2); // (Racine de 3) / 2
	private final float HEXAGON_BORDER_SIZE = 3;		// Taille bordure noire des hexagones
	
	Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game g;
    
    ShapeRenderer shrd;
    
    /*
     * Constructeurs
     */
    public GameScreen(Game g){
        create();
        this.g=g;
    }
    public GameScreen(){
        create();
    }
    
    // Création de la page
    public void create()
    {
    	// Désactive limite image en puissance de 2
    	Texture.setEnforcePotImages(false);
    	
    	// Création du batch (rendu) et du stage (lecture action)
    	batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Définition du skin
        skin = new Skin();
        
        // Designer
        shrd = new ShapeRenderer();
    }
	
	@Override
	public void render(float delta)
	{
		// Fond
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    batch.begin();
		    stage.draw();
		    
			Gdx.gl10.glLineWidth(2);
			shrd.begin(ShapeType.Line);
				float positionChat = Gdx.graphics.getWidth()*4/5;
				shrd.setColor(0, 0, 0, 1);
				shrd.line(positionChat, 0, positionChat, Gdx.graphics.getHeight());
			shrd.end();
		batch.end();
		
		showBoard(5);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	// Affiche le plateau
	public void showBoard(int boardSize)
	{
		float sizeCanevas = 800;
		
		// Calcul du rayon à partir de la largeur
		float radius = (sizeCanevas - 100) / ((boardSize * 2 - 1) * 2 * RAC);
		
		// Décalage case
		float xSpace = radius * 2 * RAC - HEXAGON_BORDER_SIZE;
		
		// Position de départ
		float xFirst = (boardSize) / 2 * xSpace + 50;
		float yFirst = sizeCanevas - radius - 50;
		
		// Décalage ligne
		float xLineSpace = radius * RAC - 1;
		float yLineSpace = radius * 2 - 0.5f * radius - HEXAGON_BORDER_SIZE;
					
		// Boucle par ligne
		for(int lineNum = 0; lineNum < boardSize * 2 - 1; lineNum ++)
		{
			// Nombre de case par ligne
			int nbCasePerLine = boardSize * 2 - Math.abs(lineNum + 1 - boardSize) - 1;
			
			// Position 1ere case de la ligne
			float xLine = xFirst - xLineSpace * (nbCasePerLine - boardSize);
			float yLine = yFirst - yLineSpace * lineNum;
			
			// Boucle par case
			for(int numCase = 0; numCase < nbCasePerLine; numCase ++)
			{
				// Position x de la case
				float xCase = xLine + xSpace * numCase;

				// Dessine un hexagone
				Hexagon tempHex = new Hexagon(0, 0, xCase, yLine, radius, Color.WHITE, HEXAGON_BORDER_SIZE);
				tempHex.drawCase(shrd);				
			}
		}
	}
}
