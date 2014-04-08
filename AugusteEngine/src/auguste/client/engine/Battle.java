package auguste.client.engine;

import java.util.ArrayList;
import java.util.List;

public class Battle
{
	private List<UW> fighters;
	private UW loser;
	private Board board;
	
	public Battle(int uFighter1, int wFighter1, int uFighter2, int wFighter2, int uLoser, int wLoser, Board board)
	{
		UW uw1 = new UW(uFighter1, wFighter1);
		UW uw2 = new UW(uFighter2, wFighter2);
		this.loser = new UW(uLoser, wLoser);
		
		this.fighters = new ArrayList<>();
		this.fighters.add(uw1);
		this.fighters.add(uw2);
		
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