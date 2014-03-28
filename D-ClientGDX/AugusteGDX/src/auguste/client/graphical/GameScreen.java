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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameScreen implements Screen {
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
    
    // Cr�ation de la page
    public void create()
    {
    	// D�sactive limite image en puissance de 2
    	Texture.setEnforcePotImages(false);
    	
    	// Cr�ation du batch (rendu) et du stage (lecture action)
    	batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // D�finition du skin
        skin = new Skin();
        
        // Designer
        shrd = new ShapeRenderer();
    }
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

	    batch.begin();
		    stage.draw();
		    
			Gdx.gl10.glLineWidth(2);
			//shrd.setProjectionMatrix(camera.combined);
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
		shrd.begin(ShapeType.Line);
		shrd.setColor(0, 0, 0, 1);
		shrd.line(50, 0, 50, Gdx.graphics.getHeight());
		shrd.end();
	}
}
