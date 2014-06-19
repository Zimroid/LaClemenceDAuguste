package auguste.client.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	private Map<Integer, Queue<ChatMessageReceived>> messages;
	
	private Map<Integer, List<Queue<Tenaille>>> allTenailles;
	private Map<Integer, List<Queue<Move>>> allMoves;
	private Map<Integer, List<Queue<Battle>>> allBattles;
	private Map<Integer, List<Queue<ChatMessageReceived>>> allMessages;
	
	private static UserInterfaceManager INSTANCE;

	private UserInterfaceManager()
	{
		this.battles 	= new HashMap<>();
		this.moves 		= new HashMap<>();
		this.tenailles 	= new HashMap<>();
		this.messages 	= new HashMap<>();
		
		this.allBattles 	= new HashMap<>();
		this.allTenailles 	= new HashMap<>();
		this.allMoves		= new HashMap<>();
		this.allMessages	= new HashMap<>();
	}
	
	public static UserInterfaceManager getInstance()
	{
		if(INSTANCE == null)
		{
			INSTANCE = new UserInterfaceManager();
		}
		return INSTANCE;
	}
	
	/*
	 * Fonctions pour ajouter un élément à une file en fonction de la partie
	 */
	public void addTenaille(int gameId, Tenaille tenaille)
	{
		if(this.getTenailles(gameId) == null)
		{
			Queue<Tenaille> newQueue = new LinkedList<>();
			this.tenailles.put(gameId, newQueue);
			newQueue.add(tenaille);
		}
		else
		{
			Queue<Tenaille> queueTenaille = this.getTenailles(gameId);
			queueTenaille.add(tenaille);
		}
	}
	
	public void addMoves(int gameId, Move move)
	{
		if(this.getMoves(gameId) == null)
		{
			Queue<Move> newQueue = new LinkedList<>();
			this.moves.put(gameId, newQueue);
			newQueue.add(move);
		}
		else
		{
			Queue<Move> queueMove = this.getMoves(gameId);
			queueMove.add(move);
		}
	}
	
	public void addBattles(int gameId, Battle battle)
	{
		if(this.getBattles(gameId) == null)
		{
			Queue<Battle> newQueue = new LinkedList<>();
			this.battles.put(gameId, newQueue);
			newQueue.add(battle);
		}
		else
		{
			Queue<Battle> queueBattle = this.getBattles(gameId);
			queueBattle.add(battle);
		}
	}
	
	public void addMessageChat(int gameId, ChatMessageReceived message)
	{
		if(this.getChatMessageReceived(gameId) == null)
		{
			Queue<ChatMessageReceived> newQueue = new LinkedList<>();
			this.messages.put(gameId, newQueue);
			newQueue.add(message);
		}
		else
		{
			Queue<ChatMessageReceived> queueMessage = this.getChatMessageReceived(gameId);
			queueMessage.add(message);
		}
	}
	
	/*
	 * Fonctions pour récupérer les files originales
	 */
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
	
	public Queue<ChatMessageReceived> getChatMessageReceived(int id)
	{
		return this.messages.get(id);
	}
	
	/*
	 * Fonction pour ajouter les files des différentes interfaces aux lists
	 */
	public void addQueueTenaille(int id, Queue<Tenaille> queue)
	{
		if(this.allTenailles.get(id) == null)
		{
			List<Queue<Tenaille>> list = new ArrayList<>();
			list.add(queue);
			this.allTenailles.put(id, list);
		}
		else
		{
			List<Queue<Tenaille>> list = this.allTenailles.get(id);
			list.add(queue);
		}
	}
	
	public void addQueueBattle(int id, Queue<Battle> queue)
	{
		if(this.allBattles.get(id) == null)
		{
			List<Queue<Battle>> list = new ArrayList<>();
			list.add(queue);
			this.allBattles.put(id, list);
		}
		else
		{
			List<Queue<Battle>> list = this.allBattles.get(id);
			list.add(queue);
		}
	}
	
	public void addQueueMove(int id, Queue<Move> queue)
	{
		if(this.allMoves.get(id) == null)
		{
			List<Queue<Move>> list = new ArrayList<>();
			list.add(queue);
			this.allMoves.put(id, list);
		}
		else
		{
			List<Queue<Move>> list = this.allMoves.get(id);
			list.add(queue);
		}
	}
	
	public void addQueueMessageChat(int id, Queue<ChatMessageReceived> queue)
	{
		if(this.allMessages.get(id) == null)
		{
			List<Queue<ChatMessageReceived>> list = new ArrayList<>();
			list.add(queue);
			this.allMessages.put(id, list);
		}
		else
		{
			List<Queue<ChatMessageReceived>> list = this.allMessages.get(id);
			list.add(queue);
		}
	}
	
	/*
	 * Fonction pour remplir les Queue des interface à partir de la queue originale du moteur
	 */
	
	public void fillQueueTenaille(int id)
	{
		Queue<Tenaille> queue = this.getTenailles(id);
		List<Queue<Tenaille>> list = allTenailles.get(id);
		
		if(queue != null)
		{
			while(!queue.isEmpty())
			{
				Tenaille o = queue.remove();
				
				for(Queue<Tenaille> lq : list)
				{
					lq.add(o);
				}
			}
		}
	}
	
	public void fillQueueMove(int id)
	{
		Queue<Move> queue = this.getMoves(id);
		List<Queue<Move>> list = allMoves.get(id);
		
		if(queue != null)
		{
			while(!queue.isEmpty())
			{
				Move o = queue.remove();
				
				for(Queue<Move> lq : list)
				{
					lq.add(o);
				}
			}
		}
	}
	
	public void fillQueueBattle(int id)
	{
		Queue<Battle> queue = this.getBattles(id);
		List<Queue<Battle>> list = allBattles.get(id);
		
		if(queue != null)
		{
			while(!queue.isEmpty())
			{
				Battle o = queue.remove();
				
				for(Queue<Battle> lq : list)
				{
					lq.add(o);
				}
			}
		}
	}
	
	public void fillQueueChatMessage(int id)
	{
		Queue<ChatMessageReceived> queue = this.getChatMessageReceived(id);
		List<Queue<ChatMessageReceived>> list = allMessages.get(id);
		
		if(queue != null)
		{
			while(!queue.isEmpty())
			{
				ChatMessageReceived o = queue.remove();
				
				for(Queue<ChatMessageReceived> lq : list)
				{
					lq.add(o);
				}
			}
		}
	}
}