package auguste.client.engine;

public class Legionnary extends Pawn 
{
	private Legion legion;
	private boolean armored;
	
	/**
	 * @param uw Les coordonnées du légionnaire.
	 * @param legion La légion à laquelle appartient ce légionnaire.
	 */
	public Legionnary(UW uw, Legion legion)
	{
		super(uw);
		this.legion = legion;
		this.armored = false;
	}
	
	/**
	 * @return La légion du légionnaire.
	 */
	public Legion getLegion()
	{
		return this.legion;
	}
	
	/**
	 * @return True si le légionnaire a une armure, false sinon.
	 */
	public boolean isArmored()
	{
		return this.armored;
	}
	
	/**
	 * Le légionnaire prend une armure.
	 */
	public void takeArmor()
	{
		this.armored = true;
	}
}