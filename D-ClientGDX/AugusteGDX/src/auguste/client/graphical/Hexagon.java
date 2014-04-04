package auguste.client.graphical;

import java.util.ArrayList;

import auguste.client.engine.Cell;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Hexagon {
	private Cell cell;

	private Position center;	// Position centre
	private float radius;		// Taille rayon
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
		this.center = new Position(x, y);
		this.radius = r;
		this.color = color;
		this.sizeBorder = border;
	}
	
	/*
	 * Afficher la case (constituée de 2 hexagones et de son contenu)
	 */
	public void drawCase(ShapeRenderer shrd)
	{
		drawHex(shrd, this.center, this.radius, this.color, this.sizeBorder);
	}
	
	/*
	 * Vérifie si clic dans l'hexagone
	 */
	public boolean checkClic(Position clic)
	{
		boolean isInHex = inCircle(this.center, this.radius, clic);
		
		if(isInHex)
		{
			ArrayList<Position> vertices = calcPoints(center, radius);
			
			// Dessin des triangles
			for(int i = 0; i < vertices.size() && isInHex; i++)
			{
				Position a = vertices.get(i);
				Position b;
				
				if(i+1 < vertices.size())
				{
					b = vertices.get(i+1);
				}
				else
				{
					b = vertices.get(0);
				}

				isInHex = sameSide(a, b, this.center, clic);
			}
		}
		
		return isInHex;
	}
	
	/*
	 * Dessine un hexagone avec sa bordure
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
	public static ArrayList<Position> calcPoints(Position center, float radius)
	{
		// (Racine de 3) / 2
		float rac = (float) (Math.sqrt(3)/2);
		
		// Positions points hexagone
		ArrayList<Position> points = new ArrayList<Position>();
		points.add(new Position(center.getX(), center.getY() - radius));
		points.add(new Position(center.getX() + rac * radius, center.getY() - 0.5f * radius));
		points.add(new Position(center.getX() + rac * radius, center.getY() + 0.5f * radius));
		points.add(new Position(center.getX(), center.getY() + radius));
		points.add(new Position(center.getX() - rac * radius, center.getY() + 0.5f * radius));
		points.add(new Position(center.getX() - rac * radius, center.getY() - 0.5f * radius));
		
		return points;
	}
	
	/*
	 * Vérfie si le clic se trouve dans le cercle circonscrit de l'hexagone
	 */
	public static boolean inCircle(Position o, float radius, Position clic)
	{
		return Math.sqrt((o.getX() - clic.getX()) * (o.getX() - clic.getX()) + (o.getY() - clic.getY()) * (o.getY() - clic.getY())) < radius; 
	}
	
	/*
	 * Vérifie si le centre O et le point cliqué sont du même côté de la droite AB
	 */
	public static boolean sameSide(Position a, Position b, Position o, Position clic)
	{
		return (  (o.getX() - a.getX()) * (b.getY() - a.getY()) - (b.getX() - a.getX()) * (o.getY() - a.getY()) )
			*( (clic.getX() - a.getX()) * (b.getY() - a.getY()) - (b.getX() - a.getX()) * (clic.getY() - a.getY()) ) > 0;
	}
}
