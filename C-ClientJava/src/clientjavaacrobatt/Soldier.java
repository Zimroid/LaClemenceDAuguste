package clientjavaacrobatt;

public class Soldier
{
	public boolean isArmored=false;
	public boolean isKilled=false;	
	public int legionId;
	
	/**
	 * these fields are useless on the server-side
	 */
	public boolean isClickableForDeparture=false;
	public boolean isClickableForUndo=false;
	public boolean isSemiTransparent=false; 
	public boolean isBackgroundVisible=true;
	
	/**
	 * For IA
	 */
	public boolean isAProjection=false;

	//for serialization
	public Soldier(){}
	
	public Soldier(int legionI){
		legionId=legionI;
	}
	
	public Soldier duplicate(){
	  	Soldier newSo=new Soldier(this.legionId);
	  	newSo.isClickableForUndo=true;
	  	newSo.isArmored=this.isArmored;
	  	newSo.isSemiTransparent=true;
	  	this.isSemiTransparent=true;	  	
	  	return newSo;
	}

	@Override
	public String toString() {
		return "Soldier [isArmored=" + isArmored  
				+ ", legionId="
				+ legionId + "]";
	}

	/*public Team getTeam(GameData aGameData){
		Legion legion = aGameData.legions.get(legionId);		
		return legion.legionInfo.team;
	}*/
	
	public Soldier copy(){
		
		Soldier theCopy=new Soldier();
		theCopy.isArmored=isArmored;
		theCopy.isKilled=isKilled;	
		theCopy.legionId=legionId;

		theCopy.isClickableForDeparture=false;
		theCopy.isClickableForUndo=false;
		theCopy.isSemiTransparent=false; 
		theCopy.isBackgroundVisible=true;
		
		return theCopy;
	}
}