package auguste.client.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
     
    Game g;
    
    /*
     * Constructeurs
     */
    public MenuScreen(Game g){
        create();
        this.g=g;
    }
    public MenuScreen(){
        create();
    }
    
    // Création de la page    
    public void create(){
    	// Désactive limite image en puissance de 2
    	Texture.setEnforcePotImages(false);
    	
    	// Création du batch (rendu) et du stage (lecture action)
    	batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Définition du skin
        skin = new Skin();
        
        // Génération texture pixmap
        Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();
        skin.add("btn", new Texture(pixmap));
 
        // Ajout de la police
        BitmapFont bfont = new BitmapFont();
        bfont.scale(1);
        skin.add("default", bfont);
 
        // Configuration d'un design de textButton
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up 		= skin.newDrawable("btn", Color.DARK_GRAY);
        textButtonStyle.down 	= skin.newDrawable("btn", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("btn", Color.BLUE);
        textButtonStyle.over 	= skin.newDrawable("btn", Color.LIGHT_GRAY);
        textButtonStyle.font 	= skin.getFont("default");
        skin.add("Basic", textButtonStyle);
 
        // Création du bouton
        final TextButton textButton = new TextButton("Create Game", skin, "Basic");
        textButton.setPosition(200, 200);
        stage.addActor(textButton);
        
        // Clic sur le bouton
        textButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                textButton.setText("Game");
                g.setScreen(new GameScreen(g));
            }
        });
    }
 
    public void render (float delta) {
    	//if(Gdx.input.justTouched())
            //g.setScreen(new GameScreen(g));
            
    	Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        Table.drawDebug(stage);
    }
 
    @Override
    public void resize (int width, int height) {
        stage.setViewport(width, height, false);
    }
 
    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
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
}