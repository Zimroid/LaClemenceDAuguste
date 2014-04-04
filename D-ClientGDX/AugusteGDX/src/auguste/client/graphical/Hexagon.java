package auguste.client.graphical;

import java.util.ArrayList;

import auguste.client.engine.Cell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Hexagon {
	private Cell cell;

	private Position centre;	// Position centre
	private float rayon;		// Taille rayon
	private Color color;		// Couleur de la case
	private float sizeBorder;	// Taille de la bordure noire (chevauchement)
		
	/*
	 * Constructor
	 */
	public Hexagon(Cell ce, float x, float y, float r, Color color, float border)
	{
		// Données
		this.cell = ce;		
		
		// Infos graphiques
		this.centre = new Position(x, y);
		this.rayon = r;
		this.color = color;
		this.sizeBorder = border;
	}
	
	/*
	 * Afficher la case (constituée de 2 hexagones et de son contenu)
	 */
	public void drawCase(ShapeRenderer shrd)
	{
		drawHex(shrd, this.centre, this.rayon, this.color, this.sizeBorder);
	}
	
	/*
	 * Dessine un hexagone
	 */
	public static void drawHex(ShapeRenderer shrd, Position c, float r, Color color, float border)
	{
		// Si hexagone secondaire, dessin hexagone primaire
		if(color != Color.BLACK)
		{
			drawHex(shrd, c, r, Color.BLACK, 0); // Dessin premier hexagone (fond)
			r = r - border;						 // Réduction taille hexagone
		}
		
		// Récupère les différents points de l'hexagone
		ArrayList<Position> points = calcPoints(c, r);
		
		// Début du dessin
		shrd.begin(ShapeType.Filled);
		shrd.setColor(color);
		
		// Dessin des triangles
		for(int i = 0; i < points.size(); i++)
		{
			Position p1 = points.get(i);
			Position p2;
			
			if(i+1 < points.size())
			{
				p2 = points.get(i+1);
			}
			else
			{
				p2 = points.get(0);
			}
			
			shrd.triangle(c.getX(), c.getY(), p1.getX(), p1.getY(), p2.getX(), p2.getY());
		}
		
		// Fin du dessin
		shrd.end();
	}
	
	/*
	 * Calcul les points de l'hexagone
	 */
	public static ArrayList<Position> calcPoints(Position centre, float rayon)
	{
		// (Racine de 3) / 2
		float rac = (float) (Math.sqrt(3)/2);
		
		// Positions points hexagone
		ArrayList<Position> points = new ArrayList<Position>();
		points.add(new Position(centre.getX(), centre.getY() - rayon));
		points.add(new Position(centre.getX() + rac * rayon, centre.getY() - 0.5f * rayon));
		points.add(new Position(centre.getX() + rac * rayon, centre.getY() + 0.5f * rayon));
		points.add(new Position(centre.getX(), centre.getY() + rayon));
		points.add(new Position(centre.getX() - rac * rayon, centre.getY() + 0.5f * rayon));
		points.add(new Position(centre.getX() - rac * rayon, centre.getY() - 0.5f * rayon));
		
		return points;
	}
}
