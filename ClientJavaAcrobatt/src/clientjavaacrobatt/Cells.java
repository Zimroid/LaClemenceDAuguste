package clientjavaacrobatt;

import java.util.ArrayList;

public class Cells
{
	private ArrayList<Cell> theCells;
	
	//for serialization and usage
	public Cells(){
		theCells=new ArrayList<Cell>();
	}

	public Cells(Cell oneCell){
		theCells=new ArrayList<Cell>();
		theCells.add(oneCell);		
	}
	
	public Cells(Cell oneCell,Cell twoCell){
		theCells=new ArrayList<Cell>();
		theCells.add(oneCell);
		theCells.add(twoCell);
	}
	
	public void removeOneCell(Cell cell){
		theCells.remove(cell);
	}
	
	public boolean contains(Cell oneCell){	
		return theCells.contains(oneCell);
	}
	
	public void addNewSoldier(Legion legion){
		for (Cell c:theCells){
			c.soldier=new Soldier(legion.legionInfo.legionId);
		}		
	}
	
	public void setArmoredSoldier(boolean b){		
		for (Cell c:theCells){
			c.soldier.isArmored=b;
		}		
	}
	
	public void setSoldierCanGo(boolean b){		
		for (Cell c:theCells){
			c.soldierCanGo=b;
		}		
	}
	
	public void setWallCanGo(boolean b){
		for(Cell c:theCells){
			c.wallCanGo=b;
		}
	}
	
	public void setLaurelCanGo(boolean b){		
		for (Cell c:theCells){
			c.laurelCanGo=b;
		}		
	}

	public void setSoldierIsClickableForDeparture(boolean b){		
		for (Cell c:theCells){
			 c.soldier.isClickableForDeparture=b;
		}		
	}
	
	public void setArmoredCell(boolean b){		
		for (Cell c:theCells){
			c.hasArmor=b;
		}		
	}
	
	public void setWalledCell(boolean b)
	{
		for(Cell c:theCells)
		{
			c.hasWall=b;
		}
	}
	
	public ArrayList<Cell> get(){
		return theCells;
	}
	
	public Cell get(int i){
		return theCells.get(i);
	}
	
	public void add(Cell c){
		this.theCells.add(c);
	}

	
	public void addAll(Cells cells){
		this.theCells.addAll(cells.get());
	}
	
	public int size(){
		return theCells.size();
	}
	
	@Override
	public String toString() {
		return "Cells [size="+ theCells.size() +"theCells=" + theCells + "]";
	}
}