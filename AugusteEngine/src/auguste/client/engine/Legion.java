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
    private int legion_id;
	
	public Legion(int id, Player player) 
	{
		this.player = player;
		this.pawns = new ArrayList<>();
		this.tabPawn = new HashMap<>();
		this.legion_id = id;
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
	
	public int getId()
	{
		return this.legion_id;
	}
}