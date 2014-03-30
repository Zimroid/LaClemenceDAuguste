package auguste.client.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
	Skin skin;
    Stage stage;
    SpriteBatch batch;
    Game g;
    
    ShapeRenderer shrd;
    ShapeRenderer renderer;
    
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
        renderer = new ShapeRenderer();
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
		
		showPlateau();
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
	
	public void showPlateau()
	{
		drawHexagon(100,500,40);
		drawHexagon(180,500,40);
		drawHexagon(260,500,40);
	}
	
	// Dessine un hexagone en fonction de son centre (x,y) et de son rayon r
	public void drawHexagon(float x, float y, float r)
	{
		float rac = (float) (Math.sqrt(3)/2);
		
		Gdx.gl10.glLineWidth(2);
		shrd.begin(ShapeType.Line);
			shrd.setColor(0, 0, 0, 1);
			
			shrd.polygon(
				new float[] {
					x, 			 y - r,
					x + rac * r, y - 0.5f * r,
					x + rac * r, y + 0.5f * r,
					x, 			 y + r,
					x - rac * r, y + 0.5f * r,
					x - rac * r, y - 0.5f * r
				}
			);
		shrd.end();
	}
}
