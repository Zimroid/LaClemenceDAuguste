/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.tests;

/**
 *
 * @author Zwyk
 */
import auguste.engine.entity.*;
import auguste.engine.entity.action.Action;
import auguste.engine.entity.action.Movement;
import auguste.engine.entity.pawn.Armor;
import auguste.engine.entity.pawn.Laurel;
import auguste.engine.entity.pawn.Soldier;
import java.awt.Point;
public class AugusteTests {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Team tOne = new Team();
        tOne.setNum(1);
        Team tTwo = new Team();
        tTwo.setNum(2);
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
        
        Game g = new Game(2000);
        g.addPlayer(pOne);
        g.addPlayer(pTwo);
        Board b = new Board(5);
        g.setBoard(b);
        
        g.initBoard();
        showBoard(b);
        
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
    }
    
    private static void showBoard(Board b){
        for(Cell c : b.getCells()) {
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
        System.out.println();
    }
}
