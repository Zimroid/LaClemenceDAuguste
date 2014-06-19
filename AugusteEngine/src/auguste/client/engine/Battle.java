package auguste.client.engine;

import java.util.ArrayList;
import java.util.List;

public class Battle
{
	private List<UW> fighters;
	private UW loser;
	private Board board;
	
	public Battle(UW uwFighter1, UW uwFighter2, UW uwLoser, Board board)
	{
		this.loser = uwLoser;
		this.fighters = new ArrayList<>();
		this.fighters.add(uwFighter1);
		this.fighters.add(uwFighter2);
		
		this.board = board;
	}
	
	public List<UW> getFighters()
	{
		return this.fighters;
	}
	
	public UW getLoser()
	{
		return this.loser;
	}
	
	public Legionnary applyBattle()
	{
		return (Legionnary) this.board.getCell(this.loser).getPawn();
	}
}