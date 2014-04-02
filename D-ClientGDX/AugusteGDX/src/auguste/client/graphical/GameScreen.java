package auguste.client.graphical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
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
				
				// Version 2.0 à tester ...
				Hexagon tempHex = new Hexagon(0, 0, xCase, yLine, radius, Color.WHITE, HEXAGON_BORDER_SIZE);
				tempHex.drawCase(shrd);
				
				// Dessine un hexagone
				drawHexagon(xCase, yLine, radius, Color.WHITE);
			}
		}
	}
	
	// Dessine un hexagone en fonction de son centre (x,y) et de son rayon r et de sa couleur de fond
	public void drawHexagon(float x, float y, float r, Color color)
	{
		// Si hexagone secondaire, dessin hexagone primaire
		if(color != Color.BLACK)
		{
			drawHexagon(x, y, r, Color.BLACK);	// Dessin premier hexagone (fond)
			shrd.setColor(color);				// Changement de couleur
			r = r - HEXAGON_BORDER_SIZE;		// Réduction taille hexagone
		}
		
		// (Racine de 3) / 2
		float rac = (float) (Math.sqrt(3)/2);
		
		// Positions points hexagone
		float x1 = x, 			y1 = y - r;
		float x2 = x + rac * r, y2 = y - 0.5f * r;
		float x3 = x + rac * r, y3 = y + 0.5f * r;
		float x4 = x, 			y4 = y + r;
		float x5 = x - rac * r, y5 = y + 0.5f * r;
		float x6 = x - rac * r, y6 = y - 0.5f * r;
		
		shrd.begin(ShapeType.Filled);
		shrd.setColor(color);
		shrd.triangle(x, y,	x1, y1,	x2, y2);
		shrd.triangle(x, y,	x2, y2,	x3, y3);
		shrd.triangle(x, y,	x3, y3,	x4, y4);
		shrd.triangle(x, y,	x4, y4,	x5, y5);
		shrd.triangle(x, y,	x5, y5,	x6, y6);
		shrd.triangle(x, y,	x6, y6, x1, y1);
		shrd.end();
	}
}
