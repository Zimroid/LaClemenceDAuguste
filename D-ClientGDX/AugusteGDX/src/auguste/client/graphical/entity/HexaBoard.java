package auguste.client.graphical.entity;

import auguste.client.engine.Cell;
import auguste.client.graphical.MainGr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class HexaBoard {
	private final float RAC = (float) (Math.sqrt(3)/2); // (Racine de 3) / 2
	private final float HEXAGON_BORDER_SIZE = 3;		// Taille bordure noire des hexagones
	
	private int boardSize;
	private List<Hexagon> hxs;
	
	/*
	 * Constructeur
	 */
	public HexaBoard(MainGr g, float sizeCanevas)
	{
		//List<Game> gameList = g.getCli().getGames();
		//List<List<Cell>> cells = g.getCli().getGames().get(0).getBoard().getCells();
		List<List<Cell>> cells = null;
		
		if(cells != null && cells.get(0) != null)
		{
			this.boardSize = cells.get(0).size();
			
			this.hxs = new ArrayList<>();
			CreateBoard(sizeCanevas, cells);
		}
	}
	
	/*
	 * Création du plateau de jeu
	 */
	public void CreateBoard(float sizeCanevas, List<List<Cell>> cells)
	{
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
		
		// -----------------------------------
		
		// Numéro de la ligne
		int lineNum = 0;
		
		// Boucle par ligne
		for(List<Cell> line : cells)
		{
			// Numéro de la case
			int caseNum = 0;
			
			// Nombre de case par ligne
			int nbCasePerLine = boardSize * 2 - Math.abs(lineNum + 1 - boardSize) - 1;
			
			// Position 1ere case de la ligne
			float xLine = xFirst - xLineSpace * (nbCasePerLine - boardSize);
			float yLine = yFirst - yLineSpace * lineNum;
			
			// Boucle par case
			for(Cell ce : line)
			{
				// Position x de la case
				float xCase = xLine + xSpace * caseNum;

				// Création d'un hexagone
				this.hxs.add(
					new Hexagon(ce, xCase, yLine, radius, Color.WHITE, HEXAGON_BORDER_SIZE)
				);
				
				caseNum++;
			}
			
			lineNum++;
		}
	}
		
	/*
	 * Dessine le plateau de jeu
	 */
	public void DrawBoard(ShapeRenderer shrd)
	{
		if(this.hxs != null)
		{
			for(Hexagon hx : this.hxs)
			{
				hx.drawCase(shrd);				
			}
		}
	}
	
	/*
	 * Détecte l'hexagone cliqué
	 */
	public Hexagon findClickedHex(Position clic)
	{
		clic.setY(Gdx.graphics.getHeight() - clic.getY());
		
		Hexagon res = null;
		
		for(int i = 0; res == null && i < this.hxs.size(); i++)
		{
			if(this.hxs.get(i).checkClic(clic))
			{
				res = this.hxs.get(i);
			}
		}
		
		return res;
	}

	/*
	 * Fabrique le plateau de jeu
	 */
	public void testBoard(ShapeRenderer shrd)
	{
		this.hxs = new ArrayList<>();
		
		int boardSize = 5;
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
				this.hxs.add(new Hexagon(null, xCase, yLine, radius, Color.WHITE, HEXAGON_BORDER_SIZE));
			}
		}
	}
}
