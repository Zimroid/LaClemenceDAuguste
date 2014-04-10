package auguste.client.graphical;

import auguste.client.graphical.entity.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen {
	private Stage stage;
    private SpriteBatch batch;
    private MainGr g;
    
    private ShapeRenderer shrd;
    private HexaBoard board;
    
    private Hexagon firstHexa;
    private Hexagon secHexa;
    
    /*
     * Constructeurs
     */
    public GameScreen(MainGr g)
    {
        this.g = g;

        // Création des objets de la page
        create();
	
 	    // ------------------------------------------------------------ TEST
		this.getBoard().testBoard(shrd);
    }
    
    // ------ CONFIG TAILLE DU CANEVAS !!!!
    
    /*
     *  Création de la page
     */
    public void create()
    {
    	// Désactive limite image en puissance de 2
    	Texture.setEnforcePotImages(false);
    	
    	// Création du batch (rendu) et du stage (lecture action)
    	batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        // Instanciation
        this.setShrd(new ShapeRenderer());
        
		// Création du plateau
		this.setBoard(new HexaBoard(g, 800f));
    }
	
    /*
     * Rendu de la page
     * @see com.badlogic.gdx.Screen#render(float)
     */
	@Override
	public void render(float delta)
	{
		// Fond
 		Gdx.gl.glClearColor(1, 1, 1, 1);
 	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

 	    batch.begin();
		    stage.draw();
		    
			Gdx.gl10.glLineWidth(2);
			getShrd().begin(ShapeType.Line);
				// Positionnement chat
				float positionChat = Gdx.graphics.getWidth()*4/5;
				getShrd().setColor(0, 0, 0, 1);
				getShrd().line(positionChat, 0, positionChat, Gdx.graphics.getHeight());
			getShrd().end();
		batch.end();
		
		// Dessin du plateau de jeu
		this.getBoard().DrawBoard(getShrd());
		
		// Clic sur le bouton
		if (Gdx.input.justTouched()) {          
	        System.out.println(Gdx.input.getX() +"-"+ Gdx.input.getY());
	        	        
	        // Si clic dans le grand hexagone
	        if(true)
	        {
	        	Hexagon current = this.board.findClickedHex(new Position(Gdx.input.getX(), Gdx.input.getY()));
	        	if(current != null)
	        	{
	        		if(firstHexa == null)
	        		{
	        			firstHexa = current;
	        			current.changeColor(Color.BLUE);
	        		}
	        		else if (firstHexa == current)
	        		{
	        			firstHexa = null;
	        			current.resetColor();
	        			if (secHexa != null)
	        			{
	        				secHexa.resetColor();
	        				secHexa.drawCase(shrd);
	        				secHexa = null;
	        			}
	        		}
	        		else if (secHexa != current)
	        		{
	        			if (secHexa != null)
	        			{
	        				secHexa.resetColor();
	        				secHexa.drawCase(shrd);
	        			}
	        			
        				current.changeColor(Color.BLUE);
	        			secHexa = current;
	        		}
		        	
		        	// Redessine la case
		        	current.drawCase(shrd);
	        	}
	        }
	    }
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

	public ShapeRenderer getShrd() {
		return shrd;
	}

	private void setShrd(ShapeRenderer shrd) {
		this.shrd = shrd;
	}

	public HexaBoard getBoard() {
		return board;
	}

	private void setBoard(HexaBoard board) {
		this.board = board;
	}
}
