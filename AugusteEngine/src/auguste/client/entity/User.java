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
    private boolean owner;
    
    /**
     * @param name Le nom de l'utilisateur.
     */
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
    
    /**
     * @param id L'identifiant de l'utilisateur.
     */
    public User(int id)
    {
        this.id = id;
        this.name = "";
    }
    
    /**
     * @param id L'identifiant de l'utilisateur.
     * @param name Le nom de l'utilisateur.
     */
    public User(int id, String name)
    {
        this.name = name;
        this.id = id;
    }
    
    /**
     * @param name Le nom de l'utilisateur.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @param id L'identifiant de l'utilisateur.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * @return Le nom de l'utilisateur.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * @return L'identifiant de l'utilisateur.
     */
    public int getId()
    {
        return this.id;
    }
    
    @Override
    public String toString()
    {
    	return this.name;
    }

	/**
	 * @return True si le joueur est responsable de la salle, false sinon.
	 */
	public boolean isOwner()
	{
		return owner;
	}

	/**
	 * @param owner True si le joueur est responsable de la salle, false sinon.
	 */
	public void setOwner(boolean owner)
	{
		this.owner = owner;
	}
}