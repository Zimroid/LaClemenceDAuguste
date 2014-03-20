package clientjavaacrobatt;

public class Laurel
{
	/** to the futur rules with several laurel */
	public char laurelId;
	
	public Cell position;

	/**
	 * these fields are useless on the server-side
	 */
	public boolean isClickableForDeparture=false;
	public boolean isClickableForUndo=false;
	public boolean isSemiTransparent=false;
	public boolean isBackgroundVisible=true;
	
	//for serialization
	public Laurel(){}
	
	/**
	 * when a player plan to move the Laurel, we keep the laurel on the departure-cell
	 * and add a copy of it on the arrival-cell
	 * @return Retourne une copie du laurier.
	 */
	public Laurel copy(){
	  	Laurel lauPo=new Laurel();
	  	lauPo.isSemiTransparent=true;
	  	this.isSemiTransparent=true;
	  	lauPo.isClickableForUndo=true;
	  	return lauPo;
	}
}