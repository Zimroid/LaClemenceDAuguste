package auguste.client.graphical;

import java.util.HashMap;
import java.util.Map;

import auguste.client.command.client.CommandClient;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class CreateGameScreen implements Screen {
	Skin skin;
    Stage stage;
    SpriteBatch batch;
     
    MainGr g;
    
    /*
     * Constructeurs
     */
    public CreateGameScreen(MainGr g){
    	this.g = g;
    	create();
    }
    public CreateGameScreen(){
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
        
        // Configuration options textButton
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background	= skin.newDrawable("btn", Color.LIGHT_GRAY);
        textFieldStyle.fontColor 	= Color.WHITE;
        textFieldStyle.font 		= skin.getFont("default");
        skin.add("txt", textFieldStyle);
        
        
        // Nom de la partie
        final TextField gameName = new TextField("Nom de la partie ?", skin, "txt");
        gameName.setSize(400, 50);
        gameName.setPosition(200, 600);
        
    	// Nombre de joueurs
        final TextField players = new TextField("Nombre de joueurs ?", skin, "txt");
        players.setSize(400, 50);
        players.setPosition(200, 500);
        
        // Nombre d'équipes
        final TextField teams = new TextField("Nombre d'équipes ?", skin, "txt");
        teams.setSize(400, 50);
        teams.setPosition(200, 400);
        
        // Taille d plateau
        final TextField sizeBoard = new TextField("Taille plateau ?", skin, "txt");
        sizeBoard.setSize(400, 50);
        sizeBoard.setPosition(200, 300);
        
        // Création des boutons    
        final TextButton btnMenu = new TextButton("Menu", skin, "btn");
        btnMenu.setSize(200, 80);
        btnMenu.setPosition(30, 690);
        
        final TextButton btnCreate = new TextButton("Créer la partie", skin, "btn");
        btnCreate.setSize(250, 50);
        btnCreate.setPosition(200, 200);
        
        // Ajout sur la page
        stage.addActor(gameName);
        //stage.addActor(players);
        //stage.addActor(teams);
        //stage.addActor(sizeBoard);
        stage.addActor(btnCreate);
        
        
        // Ajout sur la page
        stage.addActor(btnMenu);
        
        // Retour au menu
        btnMenu.addListener(new ChangeListener() {
        	public void changed (ChangeEvent event, Actor actor)
            {
            	MenuScreen temp = new MenuScreen(g);
                g.setScreen(temp);
            }
        });
        
        btnCreate.addListener(new ChangeListener() {
        	public void changed (ChangeEvent event, Actor actor)
            {
        		Map<String, String> cmd = new HashMap<>();
            	cmd.put(CommandClient.COMMAND, CommandClient.GAME_CREATE);
            	cmd.put(CommandClient.GAME_NAME, gameName.getText());
            	cmd.put(CommandClient.GAME_TYPE, "fast");
            	try
            	{
					g.getCli().sendCommand(cmd);
				}
            	catch (Exception e)
            	{
					e.printStackTrace();
					System.out.println("Exception lors de la connexion ...");
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
        
        if(!g.userConnect)
        {
        	g.setScreen(new LogScreen(g));
        }
        
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