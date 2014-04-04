package auguste.client.engine;

public class Legionnary extends Pawn 
{
	private Legion legion;
	private boolean armored;
	
	public Legionnary(UW uw, Legion legion)
	{
		super(uw);
		this.legion = legion;
		this.armored = false;
	}
	
	public Legion getLegion()
	{
		return this.legion;
	}
	
	public boolean isArmored()
	{
		return this.armored;
	}
	
	public void takeArmor()
	{
		this.armored = true;
	}
}