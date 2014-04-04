/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.entity;

import auguste.client.engine.Board;
import auguste.client.engine.Pawn;
import auguste.client.engine.Player;
import auguste.client.engine.Team;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui représente une partie
 * @author Evinrude
 */
public class Game
{
    private int id;
    private String name;
    private int board_size;
    private int legion_number;
    private List<Team> teams;
    private Board board;
    private Client client;
    
    /**
     * Crée une partie en spécifiant son id.
     * @param id
     */
    public Game(int id)
    {
        this.id = id;
        this.teams = new ArrayList<>();
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
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @param teams the teams to set
     */
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    /**
     * @return the legion_number
     */
    public int getLegion_number() {
        return legion_number;
    }

    /**
     * @param legion_number the legion_number to set
     */
    public void setLegion_number(int legion_number) {
        this.legion_number = legion_number;
    }

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}