package clientjavaacrobatt;

import auguste.client.engine.Player;

/**
 * This is a light version of Legion : 
 * it help only to construct @see GameInfo without the help of @see Legion 
 * This is necessary because we want that GameInfo stay as lighter as possible. 
 * @author vigon
 *
 */
public class LegionInfo
{
	//public Team team;
	public Player player;
	public int legionId;

	public LegionInfo(int i){
		legionId=i;
	}
	
	//for serialization
	public LegionInfo(){}
}