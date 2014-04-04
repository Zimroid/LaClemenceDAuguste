package auguste.client.graphical;

import auguste.client.engine.Cell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.List;

public class HexaBoard {
	private final float RAC = (float) (Math.sqrt(3)/2); // (Racine de 3) / 2
	private final float HEXAGON_BORDER_SIZE = 3;		// Taille bordure noire des hexagones
	
	private int boardSize;
	private List<List<Cell>> cells;
	
	/*
	 * Constructeur
	 */
	public HexaBoard(int size, List<List<Cell>> cells)
	{
		this.boardSize = size;
		this.cells = cells;
	}
	
	/*
	 * Dessine le plateau de jeu
	 */
	public void DrawBoard(ShapeRenderer shrd)
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
				Hexagon tempHex = new Hexagon(null, xCase, yLine, radius, Color.WHITE, HEXAGON_BORDER_SIZE);
				tempHex.drawCase(shrd);				
			}
		}
	}
}
