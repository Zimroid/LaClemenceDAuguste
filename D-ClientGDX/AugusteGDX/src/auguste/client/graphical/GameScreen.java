package auguste.client.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.List;

public class GameScreen implements Screen {
	private Stage stage;
    private SpriteBatch batch;
    private Game g;
    
    private ShapeRenderer shrd;
    private HexaBoard board;
    private int boardRadius;
    private List<List<Cell>> cells;
    
    /*
     * Constructeurs
     */
    public GameScreen(Game g)
    {
        create();
        this.g = g;
        this.boardRadius = 5; // RECUPERER DANS LE MOTEUR
        cells = null;
    }
    
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
				float positionChat = Gdx.graphics.getWidth()*4/5;
				getShrd().setColor(0, 0, 0, 1);
				getShrd().line(positionChat, 0, positionChat, Gdx.graphics.getHeight());
			getShrd().end();
		batch.end();
		
		this.setBoard(new HexaBoard(this.boardRadius, this.cells));
		this.getBoard().DrawBoard(getShrd());
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
