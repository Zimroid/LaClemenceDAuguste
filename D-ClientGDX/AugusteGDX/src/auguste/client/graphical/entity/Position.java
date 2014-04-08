package auguste.client.graphical.entity;

/*
 * Classe identifiant une position via ses coordonnées X et Y
 */
public class Position {
	private float x;
	private float y;
	
	/*
	 * Constructor
	 */
	public Position(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
