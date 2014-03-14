package clientjavaacrobatt;

import java.util.HashMap;

public class Cell
{
	public UW uw;
	public boolean hasArmor=false;
	public boolean soldierCanGo=false;
	public boolean laurelCanGo=false;
	public boolean wallCanGo=false;
	public boolean hasWall=false;

	// For test : to write something (eg the UW coordinates) on the cell
	public String text;
	
	public Soldier soldier;
	public Laurel laurel;
	//public boolean hasCollision=false;
	
	/**
	 * when this field is not -1, the cell is a target cell, and the int is the legionId of the legion
	 */
	public int targetLaurel=-1;
	
	//for serialization
	public Cell(){}
	
	public Cell(UW uw){
		this.uw=uw;
		this.text=this.uw.toString();
	}

	/*public Cells voisins(GameData gameData)
        {
		Cells lesVoisins=new Cells();

		UW uwBis;
		Cell cell;

		uwBis=new UW(uw.u+1,uw.w);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		uwBis=new UW(uw.u+1,uw.w+1);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		uwBis=new UW(uw.u,uw.w+1);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		uwBis=new UW(uw.u,uw.w-1);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		uwBis=new UW(uw.u-1,uw.w-1);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		uwBis=new UW(uw.u-1,uw.w);		
		cell=gameData.allCells.extract(uwBis);
		if (cell!=null) lesVoisins.add(cell);

		return lesVoisins;
	}*/

	/*public Cells voisinsLibres(GameData gameData)
        {
		Cells potentiel=this.voisins(gameData);		
		Cells lesLibres=new Cells();
		
		for (Cell c:potentiel.get()){
			if (c.soldier==null&&c.hasArmor==false&& c.laurel==null ){
				lesLibres.add(c);
			}
		}		
		return lesLibres;
	}

	public Cells edge(GameData gameData,boolean withLaurel, boolean withArmor)
	{
		int markA,markB,i;

		Cells group=new Cells();
		Cells bord=new Cells();
		//Cells toBeConsider=new Cells();//mettre ici UNE COPIE tous les soldats de ma couleur

		Legion myLegion=gameData.legions.get(this.soldier.legionId);
		
		//Cells myLegionCells=gameData.allCells.extractCellOf(myLegion);
		Cells myLegionCells=myLegion.positions;

		HashMap <UW,Boolean> toBeConsider= new HashMap <UW,Boolean>();//mettre ici UNE COPIE tous les soldats de ma couleur
		HashMap <UW,Boolean> isFree= new HashMap <UW,Boolean>();// a initialiser

		for (Cell c: myLegionCells.get()){
			toBeConsider.put(c.uw, true);			
		}

		if (withLaurel && withArmor ){
			for (Cell c:gameData.allCells.extractAll().get()){
				if (c.soldier==null && !c.hasWall) isFree.put(c.uw,true);
			}
		}
		else if (!withLaurel && withArmor){
			for (Cell c:gameData.allCells.extractAll().get()){
				if (c.soldier==null && c.laurel==null && !c.hasWall) isFree.put(c.uw,true);
			}			
		}
		else if (withLaurel && !withArmor){
			for (Cell c:gameData.allCells.extractAll().get()){
				if (c.soldier==null && !c.hasArmor && !c.hasWall) isFree.put(c.uw,true);
			}			
		}
		else if (!withLaurel && !withArmor){
			for (Cell c:gameData.allCells.extractAll().get()){
				if (c.soldier==null && !c.hasArmor && c.laurel==null && !c.hasWall) isFree.put(c.uw,true);
			}			
		}

		group.add(this);
		toBeConsider.remove(this.uw);

		markA=-1;
		markB=0;

		while (markB>markA)
		{

			for (i=markA+1;i<=markB;i++)
			{

				//all the neighbors
				Cells voisins=group.get(i).voisins(gameData);
				//all the neighbors that wasn't be still considered 
				Cells voisinsGardes=new Cells();				
				for (Cell c:voisins.get()){
					if (toBeConsider.get(c.uw)!=null) voisinsGardes.add(c);
					toBeConsider.remove(c.uw);

					if (isFree.get(c.uw)!=null) bord.add(c);
					isFree.remove(c.uw);
				}

				group.addAll(voisinsGardes);				
			}
			markA=markB;
			markB=group.size()-1;
		}

		return bord;
	}
        
	public Cells edgeWall(GameData gameData)
	{
		Cells result=new Cells();
		
		for(Cell c:gameData.allCells.extractAll().get())
		{
			if(!c.hasArmor && !c.hasWall && c.soldier==null && c.laurel==null)
			{
				result.add(c);
			}
		}
		
		return result;
	}*/

	/**
	 * Compute the angle between the curent cell and the parameter : otherCell
	 * @param otherCell
	 * @return Renvoie un angle sous forme de nombre décimal.
	 */
	public double angle(Cell otherCell){
		double angle=0;
		int u1=otherCell.uw.u;
		int u0=this.uw.u;
		
		int w1=otherCell.uw.w;
		int w0=this.uw.w;
				
		if (w1-w0 == 0 && u1-u0>0 ) angle=Math.PI/6;
		else if (u1-u0==0 && w1-w0<0) angle= Math.PI/2;
		else if (u1-u0==w1-w0 && u1-u0 < 0) angle= Math.PI*5/6;
		else if (w1-w0==0 && u1-u0<0) angle = - Math.PI *5/6;
		else if (u1-u0==0 && w1-w0>0) angle = - Math.PI/2;
		else if (u1-u0==w1-w0 && u1-u0 >0 ) angle = -Math.PI/6;		

		return angle;
	}

	@Override
	public String toString() {
		return "Cell [uw=" + uw + ", soldier=" + soldier + "]";
	}
}