/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.entity;

/**
 * Classe qui représente un utilisateur.
 * @author Evinrude
 */
public class User 
{
    private String name;
    private int id;
    
    public User(String name)
    {
        this.name = name;
        this.id = -1;
    }
    
    public User()
    {
        this.name = "";
        this.id = -1;
    }
    
    public User(int id)
    {
        this.id = id;
        this.name = "";
    }
    
    public User(int id, String name)
    {
        this.name = name;
        this.id = id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public int getId()
    {
        return this.id;
    }
}