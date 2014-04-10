package auguste.client.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import auguste.client.engine.Battle;
import auguste.client.engine.Move;
import auguste.client.engine.Tenaille;

public class UserInterfaceManager
{
	private Map<Integer, Queue<Tenaille>> tenailles;  //Integer correspond à l'ID de la partie
	private Map<Integer, Queue<Move>> moves;
	private Map<Integer, Queue<Battle>> battles;
	
	private static UserInterfaceManager INSTANCE;

	private UserInterfaceManager()
	{
		this.battles = new HashMap<>();
		this.moves = new HashMap<>();
		this.battles = new HashMap<>();
	}
	
	public static UserInterfaceManager getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new UserInterfaceManager();
		}
		return INSTANCE;
	}
	
	public Queue<Tenaille> getTenailles(int id)
	{
		return this.tenailles.get(id);
	}
	
	public Queue<Move> getMoves(int id)
	{
		return this.moves.get(id);
	}
	
	public Queue<Battle> getBattles(int id)
	{
		return this.battles.get(id);
	}
}