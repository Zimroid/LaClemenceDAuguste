/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.entity;

/**
 *
 * @author Evinrude
 */
public class Game
{
    private int id;
    private String name;
    private int board_size;
    
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
}