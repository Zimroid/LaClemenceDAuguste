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
    private int position;
	
	/**
	 * @param id L'identifiant de la légion.
	 * @param player Le joueur auquel est associée la légion.
	 */
	public Legion(int id, Player player) 
	{
		this.player = player;
		this.pawns = new ArrayList<>();
		this.tabPawn = new HashMap<>();
	}
	
	/**
	 * @return La position de la légion.
	 */
	public int getPosition()
	{
		return this.position;
	}
	
	/**
	 * @param pos La position à définir.
	 */
	public void setPosition(int pos)
	{
		this.position = pos;
	}
	
	/**
	 * @return Les pions que contient la légion.
	 */
	public List<Pawn> getPawns()
	{
		return this.pawns;
	}
	
	/**
	 * @return Le joueur qui contrôle la légion.
	 */
	public Player getPlayer()
	{
		return this.player;
	}
	
	/**
	 * @param p Pour ajouter un pion à la légion.
	 */
	public void addPawn(Pawn p)
	{
		this.pawns.add(p);
		this.tabPawn.put(p.getId(), p);
	}
	
	/**
	 * @param id L'identifiant du pion à retirer de la légion.
	 */
	public void removePawn(int id)
	{
		Pawn p = this.getPawn(id);
		
		this.tabPawn.remove(id);
		this.pawns.remove(p);
	}
	
	/**
	 * @param p Le pion à retirer de la légion.
	 */
	public void removePawn(Pawn p)
	{
		int id = p.getId();
		
		this.tabPawn.remove(id);
		this.pawns.remove(p);
	}
	
	/**
	 * @param id L'identifiant du pion voulu.
	 * @return Le pion demandé.
	 */
	public Pawn getPawn(int id)
	{
		return this.tabPawn.get(id);
	}

	/**
	 * @return La couleur de la légion.
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * @param color La couleur à définir pour la légion.
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * @return La forme de la légion.
	 */
	public String getShape()
	{
		return shape;
	}

	/**
	 * @param shape La forme à définir pour la légion.
	 */
	public void setShape(String shape)
	{
		this.shape = shape;
	}

	/**
	 * @return L'identifiant de la légion.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id L'identifiant à définir pour la légion.
	 */
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