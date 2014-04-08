package auguste.client.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Legion 
{
	private List<Pawn> pawns;
	private Player player;
    private Map<Integer,Pawn> tabPawn;
    private String color;
    private String shape;
    private int id;
	
	public Legion(int id, Player player) 
	{
		this.player = player;
		this.pawns = new ArrayList<>();
		this.tabPawn = new HashMap<>();
	}
	
	public List<Pawn> getPawns()
	{
		return this.pawns;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public void addPawn(Pawn p)
	{
		this.pawns.add(p);
		this.tabPawn.put(p.getId(), p);
	}
	
	public void removePawn(int id)
	{
		Pawn p = this.getPawn(id);
		
		this.tabPawn.remove(id);
		this.pawns.remove(p);
	}
	
	public void removePawn(Pawn p)
	{
		int id = p.getId();
		
		this.tabPawn.remove(id);
		this.pawns.remove(p);
	}
	
	public Pawn getPawn(int id)
	{
		return this.tabPawn.get(id);
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getShape()
	{
		return shape;
	}

	public void setShape(String shape)
	{
		this.shape = shape;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Legion)
		{
			Legion l = (Legion) o;
			if(l.getColor().equals(this.getColor()) && l.getShape().equals(this.getShape()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}