/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientjavaacrobatt;

/**
 *
 * @author Evinrude
 */
public class Player 
{
    private String name;
    private int playerId;
    
    public Player(String name, int id)
    {
        this.name = name;
        this.playerId = id;
    }

    Player() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString()
    {
        String res = "name="+this.name+", playerId="+this.playerId;
        
        return res;
    }
}