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
    
    private String name;
    private int board_size;
    private int legion_number;
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
    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setBoardSize(int board_size)
    {
        this.board_size = board_size;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public int getBoardSize()
    {
        return this.board_size;
    }
    
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

	public Client getClient() 
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public Board getBoard() 
	{
		return board;
	}

	public void setBoard(Board board) 
	{
		this.board = board;
	}
	
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
	
	public Queue<Tenaille> getTenailles()
	{
		return this.tenailles;
	}
	
	public Queue<Move> getMoves()
	{
		return this.moves;
	}
	
	public Queue<Battle> getBattles()
	{
		return this.battles;
	}

	public long getTurn_duration() 
	{
		return turn_duration;
	}

	public void setTurn_duration(long turn_duration) 
	{
		this.turn_duration = turn_duration;
	}
	
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
		//Envoyer les Files aux UI et les appliquer pour dégager les morts
		System.out.println("Game.sendTurn non défini");
	}

	public List<User> getUsers()
	{
		return users;
	}

	public void setUsers(List<User> users)
	{
		this.users = users;
	}
}