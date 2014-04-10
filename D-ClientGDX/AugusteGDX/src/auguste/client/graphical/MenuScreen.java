package auguste.client.graphical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
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
     
    MainGr g;
    
    /*
     * Constructeurs
     */
    public MenuScreen(MainGr g){
    	this.g = g;
    	create();
    }
    public MenuScreen(){
        create();
    }
    
    /*
     * Création de la page    
     */
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
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        skin.add("btn", new Texture(pixmap));
 
        // Ajout de la police
        BitmapFont bfont = new BitmapFont();
        bfont.scale(1);
        skin.add("default", bfont);
 
        // Configuration options textButton
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up 		= skin.newDrawable("btn", Color.LIGHT_GRAY);
        textButtonStyle.down 	= skin.newDrawable("btn", Color.DARK_GRAY);
        textButtonStyle.font 	= skin.getFont("default");
        skin.add("btn", textButtonStyle);
        
        // Création du bouton        
        final TextButton btnCreate = new TextButton("Créer une partie", skin, "btn");
        btnCreate.setSize(400, 100);
        btnCreate.setPosition(550, 150);
        
        final TextButton btnJoin = new TextButton("Rejoindre une partie", skin, "btn");
        btnJoin.setSize(400, 100);
        btnJoin.setPosition(550, 350);
        
        final TextButton btnFastStart = new TextButton("Partie rapide", skin, "btn");
        btnFastStart.setSize(400, 100);
        btnFastStart.setPosition(550, 550);
        
        // Ajout sur la page
        stage.addActor(btnFastStart);
        stage.addActor(btnCreate);
        stage.addActor(btnJoin);
        
        // Connexion
        btnJoin.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor)
            {
            	System.out.print("Clic sur bouton ");
            	GameScreen temp = new GameScreen(g);
                g.setScreen(temp);
            }
        });
    }
 
    public void render (float delta)
    {
    	Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        Table.drawDebug(stage);
    }
 
    @Override
    public void resize (int width, int height) {
        //stage.setViewport(width, height, false);
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