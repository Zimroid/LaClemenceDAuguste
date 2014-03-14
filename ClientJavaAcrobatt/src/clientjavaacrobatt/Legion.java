package clientjavaacrobatt;
/**
 * This class belong to a loop  of class :
 * legion > positions > cell > legion
 * So the auto generated method ToString can produce an infinite loop 
 * @author vigon
 *
 */
public class Legion
{
	public LegionInfo legionInfo;
	
	public String legionColor;	

	/**
	 * All the positions of the soldiers of this legions are referenced here. 
	 * This avoid a search on all the Cells 
	 */
	public Cells positions=new Cells();
	
	private String[] legionColors={
			"#FF0033",//red
			"#3366FF",//blue
			"#FFFF33",//yellow
			"#99FF33",//green
			"#FF6600",//orange
			"#9900CC",//violet
			"#66CC99",//green-blue
			"#FF33CC",//pink
			"#000066",//blue dark
			"#996633",//brown
			"#CCCCCC",//grey bright
			"#666666",//grey black
			"#666666",//green yellow			
	};
	
	public Legion(LegionInfo legionInf){
		legionInfo = legionInf;
		legionColor=legionColors[legionInfo.legionId % legionColors.length];		
	}
	
	//For serialization
	public Legion(){}
}