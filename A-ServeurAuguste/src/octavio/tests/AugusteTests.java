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
import octavio.engine.entity.pawn.Pawn;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.util.RandomCollection;
public class AugusteTests {
    public static Game g;
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
        
        g = new Game(1000);
        g.addPlayer(pOne);
        g.addPlayer(pTwo);
        Board b = new Board(11);
        g.setBoard(b);
        
        g.initBoard();
        showBoard(b);
        
        
        /*addMove(-1, -4, -1, -3);
        next();
        addMove(-1, -3, -1, -2);
        next();
        addMove(-1, -2, -1, -1);
        next();
        addMove(-0, -4, 0, -2);
        next();
        addMove(-0, -2, 0, -1);
        next();
        addMove(-0, -3, 0, -2);
        next();
        addMove(-0, -2, 1, -2);
        next();
        
        addMove(0, 4, -1, 2);
        next();
        addMove(-1, 2, -1, 1);
        next();
        addMove(-1, 1, -2, 0);
        next();
        addMove(-2, 0, -3, -1);
        next();
        
        
        addMove(0, 3, 0, 2);
        next();
        addMove(-0, 2, 1, 1);
        next();
        addMove(1, 1, 2, 0);
        next();
        addMove(2, 0, 2, -1);
        next();
        addMove(2, -1, 2, -2);
        next();
        
        addMove(2, -2, 2, -3);
        next();
        next();
        
        Thread.sleep(10000);
        g.getTimer().cancel();
        g.getTimer().purge();*/
        
        /*addMove(-4, -4, -2, -2);
        next();
        addMove(-2, -2, -1, -1);
        next();
        addMove(-4, -3, -3, -2);
        next();
        addMove(-3, -4, -2, -2);
        next();
        addMove(-2, -2, 0, -1);
        next();
        addMove(0, -1, -1, -2);
        next();
        addMove(-1, -1, -2, -2);
        next();
        
        
        addMove(-4, 0, -4, -2);
        addMove(0, -4, 0, -2);
        next();
        next();
        System.out.println("Pawn at -2,-2 : " + b.getCell(new Point(-2,-2)).getPawn());*/
        
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
        System.out.println("1 : " + un/essais*rc.getTotal() + " - 8 : " + huit/essais*rc.getTotal());*/
        
        //System.out.println(distance(-1,-1,-2,-2,9));
        
        boolean end = false;
        
        pOne.setBot(new Bot(pOne,Bot.Strategy.distribuedRandom));
        pOne.setConnected(false);
        pTwo.setBot(new Bot(pTwo,Bot.Strategy.distribuedRandom));
        pTwo.setConnected(false);
        
        long t = System.currentTimeMillis();
        int i = 1;
        int limit = 0;
        while(!end && (limit>0?i<limit:true)) {
            i++;
            Thread.sleep(10);
            if(g.applyActions() == false){
                g.nextTurn();
            }
            else {
                long tt = System.currentTimeMillis() - t;
                System.out.println("Winner (" + i + " tours en " + (tt-(10*i)) + "ms soit " + (double)(tt-10)/i + "ms/tour) : " + g.getTwinner());
                end = true;
            }
            showBoard(b);
            //System.in.read();
        }
        
        
        /*addMove(-8, 0, -3, 0);
        addMove(0, -8, 0, -3);
        addMove(-8, -8, -3, -3);
        next();
        
        addMove(-3, 0, -2, 0);
        addMove(0, -3, -1, -3);
        addMove(-3,-3, -2, -2);
        next();
        
        addMove(-1, -3, -2, -3);
        next();*/
        
        /*
        g.addAction(new Action(lOne,new Movement(b.getCell(new Point(-4,-4)).getPawn(),b.getCell(new Point(-2,-2))),null));
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
        showBoard(b);
        */
    }
    
    private static int distance(int x1, int y1, int x2, int y2, int xmax) {
        return Math.min(Math.abs(x2 - x1) + Math.abs(y2 - y1), Math.abs(xmax + x2 - x1) + Math.abs(xmax + y2 - y1));
    }
    
    private static void addMove(Legion l, int xd, int yd, int xa, int ya) {
        g.addAction(new Action(l,new Movement(g.getBoard().getCell(new Point(xd,yd)).getPawn(),g.getBoard().getCell(new Point(xa,ya))),null));
    }
    
    private static void addMove(int xd, int yd, int xa, int ya) {
        addMove(g.getBoard().getCell(new Point(xd,yd)).getPawn().getLegion(),xd,yd,xa,ya);
    }
    
    private static void next() throws InterruptedException {
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
