/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package octavio.tests;

/**
 *
 * @author Zwyk
 */
import java.awt.Point;
import java.io.IOException;
import java.util.Random;
import octavio.engine.entity.Board;
import octavio.engine.entity.Bot;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.Legion;
import octavio.engine.entity.Player;
import octavio.engine.entity.Team;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.engine.entity.pawn.Armor;
import octavio.engine.entity.pawn.Laurel;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.util.RandomCollection;
public class AugusteTests {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        Team tOne = new Team(1);
        Team tTwo = new Team(2);
        Player pOne = new Player(1);
        Player pTwo = new Player(2);
        pOne.setTeam(tOne);
        pTwo.setTeam(tTwo);
        Legion lOne = new Legion(pOne);
        pOne.addLegion(lOne);
        lOne.setPosition(0);
        Legion lTwo = new Legion(pOne);
        pOne.addLegion(lTwo);
        lTwo.setPosition(2);
        Legion lThree = new Legion(pOne);
        pOne.addLegion(lThree);
        lThree.setPosition(4);
        Legion lFour = new Legion(pTwo);
        pTwo.addLegion(lFour);
        lFour.setPosition(1);
        Legion lFive = new Legion(pTwo);
        pTwo.addLegion(lFive);
        lFive.setPosition(3);
        Legion lSix = new Legion(pTwo);
        pTwo.addLegion(lSix);
        lSix.setPosition(5);
        
        Game g = new Game(1);
        g.addPlayer(pOne);
        g.addPlayer(pTwo);
        Board b = new Board(3);
        g.setBoard(b);
        
        /*RandomCollection rc = new RandomCollection(new Random());
        Board nb = new Board(1);
        Board nbh = new Board(8);
        rc.add(1, nb);
        rc.add(8, nbh);
        rc.add(1, nb);
        double cinq = 0;
        double huit = 0;
        double un = 0;
        Board res;
        int essais = 1000000;
        for(int i = 0 ; i < essais ; i++) {
            res = (Board)rc.next();
            if(res == nb) un++;
            else huit++;
        }
        System.out.println("5 : " + cinq/essais*rc.getTotal() + " - 1 : " + un/essais*rc.getTotal() + " - 8 : " + huit/essais*rc.getTotal());*/
        
        boolean end = false;
        
        pOne.setBot(new Bot(pOne,Bot.Strategy.pseudoRandom));
        pOne.setConnected(false);
        pTwo.setBot(new Bot(pTwo,Bot.Strategy.pseudoRandom));
        pTwo.setConnected(false);
        
        g.initBoard();
        showBoard(b);
        long t = System.currentTimeMillis();
        int i = 0;
        int limit = 0;
        while(!end && (limit>0?i<limit:true)) {
            i++;
            if(g.applyActions()==false) {
                g.nextTurn();
            }
            else {
                long tt = System.currentTimeMillis() - t;
                System.out.println("Winner (" + i + " tours en " + tt + "ms soit " + (double)tt/i + "ms/tour) : " + g.getWinner());
                end = true;
            }
            showBoard(b);
            //System.in.read();
        }
        
        /*g.addAction(new Action(lOne,new Movement(b.getCell(new Point(-4,-4)).getPawn(),b.getCell(new Point(-2,-2))),null));
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-4,0)).getPawn(),b.getCell(new Point(-2,0))),null));
        g.addAction(new Action(lSix,new Movement(b.getCell(new Point(0,-3)).getPawn(),b.getCell(new Point(0,-2))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        
        g.addAction(new Action(lOne,new Movement(b.getCell(new Point(-4,-3)).getPawn(),b.getCell(new Point(-2,-1))),null));
        g.addAction(new Action(lSix,new Movement(b.getCell(new Point(0,-2)).getPawn(),b.getCell(new Point(0,-1))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        
        g.addAction(new Action(lOne,new Movement(b.getCell(new Point(-2,-2)).getPawn(),b.getCell(new Point(-1,-1))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        
        g.addAction(new Action(lOne,new Movement(b.getCell(new Point(-1,-1)).getPawn(),b.getCell(new Point(-2,-2))),null));
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-2,0)).getPawn(),b.getCell(new Point(-1,0))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(0,0)).getPawn(),b.getCell(new Point(-1,-1))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-1,-1)).getPawn(),b.getCell(new Point(-2,-1))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-2,-1)).getPawn(),b.getCell(new Point(-3,-1))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-3,0)).getPawn(),b.getCell(new Point(-2,0))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-3,-1)).getPawn(),b.getCell(new Point(-3,0))),null));
        g.applyActions();
        g.nextTurn();
        showBoard(b);
        g.addAction(new Action(lFour,new Movement(b.getCell(new Point(-3,0)).getPawn(),b.getCell(new Point(-4,0))),null));
        System.out.println(g.applyActions());
        g.nextTurn();
        showBoard(b);*/
    }
    
    private static void addMove(Game g, Legion l, int xd, int yd, int xa, int ya) {
        g.addAction(new Action(l,new Movement(g.getBoard().getCell(new Point(xd,yd)).getPawn(),g.getBoard().getCell(new Point(xa,ya))),null));
    }
    
    private static void next(Game g) {
        g.applyActions();
        g.nextTurn();
        showBoard(g.getBoard());
    }
    
    private static void showBoard(Board b){
        Cell c;
        for(int x = -(b.getSize() - 1); x < b.getSize(); x++ ) {
            for(int y = -(b.getSize() - 1); y < (b.getSize() - Math.abs(x)); y++) {
                c = b.getCell(new Point(x,y));
                if(c.getP().y == -(b.getSize()-1)) {
                    for(int i = 0;i <= Math.abs(c.getP().x);i++) {
                        System.out.print(" ");
                    }
                }
                System.out.print((c.getPawn() instanceof Soldier)?(((Soldier)c.getPawn()).isArmored()?"S":c.getTent()!=null?"T":"s"):(c.getPawn() instanceof Laurel)?"L":(c.getPawn() instanceof Armor)?"A":c.getTent()!=null?"t":".");
                if(c.getP().y+Math.abs(c.getP().x) == b.getSize()-1) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }
}
