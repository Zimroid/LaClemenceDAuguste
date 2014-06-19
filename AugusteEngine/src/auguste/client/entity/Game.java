/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.entity;

import auguste.client.engine.Battle;
import auguste.client.engine.Board;
import auguste.client.engine.Legion;
import auguste.client.engine.Move;
import auguste.client.engine.Player;
import auguste.client.engine.Team;
import auguste.client.engine.Tenaille;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Classe qui représente une partie
 * @author Evinrude
 */
public class Game
{
    private int id;
    private List<User> users;
    
    private String name, gameState, gameMode;
    private int board_size, legion_number, numberOfPlayer;
    private long turn_duration;
    
    private List<Team> teams;
    private Board board;
    private Client client;
    
    private Queue<Tenaille> tenailles;
    private Queue<Move> moves;
    private Queue<Battle> battles;
    
    /**
     * Crée une partie en spécifiant son id.
     * @param id
     */
    public Game(int id)
    {
        this.id = id;
        this.teams = new ArrayList<>();
        this.tenailles = new LinkedList<>();
        this.moves = new LinkedList<>();
        this.battles = new LinkedList<>();
        this.users = new ArrayList<>();
    }
    
    /**
     * Crée une partie non configurée.
     */
    public Game()
    {
        this(0);
    }
    
    // *** Getters et setters *** //
    /**
     * @param id L'identifiant à définir.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * @param name Le nom à définir.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @param board_size La taille du plateau.
     */
    public void setBoardSize(int board_size)
    {
        this.board_size = board_size;
    }
    
    /**
     * @return L'identifiant de la partie.
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * @return La tailel du plateau
     */
    public int getBoardSize()
    {
        return this.board_size;
    }
    
    /**
     * @return Le nom de la partie
     */
    public String getName()
    {
        return this.name;
    }
    
    @Override
    public String toString()
    {
        return "ID : "+this.id+"  Name : "+this.name+"  Size : "+this.board_size;
    }

    /**
     * @return the teams
     */
    public List<Team> getTeams() 
    {
        return teams;
    }

    /**
     * @param teams the teams to set
     */
    public void setTeams(List<Team> teams) 
    {
        this.teams = teams;
    }

    /**
     * @return the legion_number
     */
    public int getLegion_number() 
    {
        return legion_number;
    }

    /**
     * @param legion_number the legion_number to set
     */
    public void setLegion_number(int legion_number) 
    {
        this.legion_number = legion_number;
    }

	/**
	 * @return Le client actuel.
	 */
	public Client getClient() 
	{
		return client;
	}

	/**
	 * @param client Le client de la partie.
	 */
	public void setClient(Client client)
	{
		this.client = client;
	}

	/**
	 * @return Le plateau associé à la partie.
	 */
	public Board getBoard() 
	{
		return board;
	}

	/**
	 * @param board Le plateau à associer à la partie.
	 */
	public void setBoard(Board board) 
	{
		this.board = board;
	}
	
	/**
	 * @return La liste des légions de la partie.
	 */
	public List<Legion> getLegions()
	{
		List<Legion> legions = new ArrayList<>();
		
		for(Team team : this.teams)
		{
			for(Player player : team.getPlayers())
			{
				legions.addAll(player.getLegions());
			}
		}
		
		return legions;
	}
	
	/**
	 * @param color La couleur voulue.
	 * @param shape La forme voulue.
	 * @return La légion qui a la forme et la couleur voulue.
	 */
	public Legion getLegion(String color, String shape)
	{
		Legion res = null;
		
		for(Legion legion : this.getLegions())
		{
			if(legion.getColor().equals(color) && legion.getShape().equals(shape))
			{
				res = legion;
			}
		}
		
		return res;
	}
	
	/**
	 * @return La file des tenailles.
	 */
	public Queue<Tenaille> getTenailles()
	{
		return this.tenailles;
	}
	
	/**
	 * @return La file des mouvements.
	 */
	public Queue<Move> getMoves()
	{
		return this.moves;
	}
	
	/**
	 * @return La file des batailles.
	 */
	public Queue<Battle> getBattles()
	{
		return this.battles;
	}

	/**
	 * @return La durée d'un tour.
	 */
	public long getTurn_duration() 
	{
		return turn_duration;
	}

	/**
	 * @param turn_duration La durée d'un tour.
	 */
	public void setTurn_duration(long turn_duration) 
	{
		this.turn_duration = turn_duration;
	}
	
	/**
	 * @param id L'identifiant du joueur voulu.
	 * @return Le joueur voulu.
	 */
	public Player getPlayer(int id)
	{
		Player res = null;
		
		for(Team team : this.teams)
		{
			if(team.getPlayer(id) != null)
			{
				res = team.getPlayer(id);
			}
		}
		
		return res;
	}
	
	public void sendTurn()
	{
		System.out.println("Envoyer les Files aux UI et les appliquer pour dégager les morts");
		System.out.println("Game.sendTurn non défini");
	}

	/**
	 * @return La liste des utilisateurs jouant dans cette partie.
	 */
	public List<User> getUsers()
	{
		return users;
	}

	/**
	 * @param users La liste des utilisateurs qui vont jouer dans cette partie.
	 */
	public void setUsers(List<User> users)
	{
		this.users = users;
	}
	
	/**
	 * @param id L'identifiant de l'utilisateur recherché.
	 * @return L'utilisateur voulu.
	 */
	public User getUser(int id)
	{
		User res = null;
		
		for(User user : this.users)
		{
			if(user.getId() == id)
			{
				res = user;
			}
		}
		
		return res;
	}

	/**
	 * @return l'état de la partie.
	 */
	public String getGameState()
	{
		return gameState;
	}

	/**
	 * @param gameState L'état de la partie.
	 */
	public void setGameState(String gameState)
	{
		this.gameState = gameState;
	}

	/**
	 * @return Le mode de la partie.
	 */
	public String getGameMode()
	{
		return gameMode;
	}

	/**
	 * @param gameMode Le mode de la partie.
	 */
	public void setGameMode(String gameMode)
	{
		this.gameMode = gameMode;
	}

	/**
	 * @return Le nombre de joueur de la partie.
	 */
	public int getNumberOfPlayer()
	{
		return numberOfPlayer;
	}

	/**
	 * @param numberOfPlayer Le nombre de joueur de la partie.
	 */
	public void setNumberOfPlayer(int numberOfPlayer)
	{
		this.numberOfPlayer = numberOfPlayer;
	}
}