package auguste.client.graphical;

import java.util.HashMap;
import java.util.Map;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import auguste.client.command.client.*;

public class LogScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
     
    MainGr g;
    
    /*
     * Constructeurs
     */
    public LogScreen(MainGr g){
        create();
        this.g = g;
    }
    public LogScreen(){
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
        
        // Configuration options textButton
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background	= skin.newDrawable("btn", Color.LIGHT_GRAY);
        textFieldStyle.fontColor 	= Color.WHITE;
        textFieldStyle.font 		= skin.getFont("default");
        skin.add("txt", textFieldStyle);

        // Nom d'utilisateur
        final TextField username = new TextField("", skin, "txt");
        username.setSize(400, 50);
        username.setPosition(200, 400);
        
        // Mot de passe
        final TextField password = new TextField("", skin, "txt");
        password.setSize(400, 50);
        password.setPosition(200, 300);
        
        // Création du bouton        
        final TextButton btnConnect = new TextButton("Connexion", skin, "btn");
        btnConnect.setSize(250, 50);
        btnConnect.setPosition(200, 200);
        
        // Ajout sur la page
        stage.addActor(username);
        stage.addActor(password);
        stage.addActor(btnConnect);
        
        // Connexion
        btnConnect.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	Map<String, String> cmd = new HashMap<>();
            	cmd.put(CommandClient.COMMAND, CommandClient.LOG_IN);
            	cmd.put(CommandClient.NAME, username.getText());
            	cmd.put(CommandClient.PASSWORD, password.getText());
            	try
            	{
					g.getCli().sendCommand(cmd);
				}
            	catch (Exception e)
            	{
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Epic fail");
				}
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