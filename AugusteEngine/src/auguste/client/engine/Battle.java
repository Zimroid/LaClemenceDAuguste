package auguste.client.engine;

import java.util.ArrayList;
import java.util.List;

public class Battle
{
	private List<UW> fighters;
	private UW loser;
	private Board board;
	
	/**
	 * Créé une instance de la classe Battle avec deux combattants et un perdant.
	 * @param uwFighter1 Les coordonnées d'un combattant.
	 * @param uwFighter2 Les coordonnées d'un autre combattant.
	 * @param uwLoser Les coordonnées du perdant.
	 * @param board Le plateau concerné.
	 */
	public Battle(UW uwFighter1, UW uwFighter2, UW uwLoser, Board board)
	{
		this.loser = uwLoser;
		this.fighters = new ArrayList<>();
		this.fighters.add(uwFighter1);
		this.fighters.add(uwFighter2);
		
		this.board = board;
	}
	
	/**
	 * Pour récupérer la liste des coordonnées des combattants
	 * @return La liste des coordonnées des combattants.
	 */
	public List<UW> getFighters()
	{
		return this.fighters;
	}
	
	/**
	 * Renvoie les coordonnées du perdant de la bataille.
	 * @return Les coordonnées du perdant de la bataille.
	 */
	public UW getLoser()
	{
		return this.loser;
	}
	
	/**
	 * @return Une instance de la classe Legionnary dont les coordonnées correspondent au perdant de la bataille.
	 */
	public Legionnary applyBattle()
	{
		return (Legionnary) this.board.getCell(this.loser).getPawn();
	}
}