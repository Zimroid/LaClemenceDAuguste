/*
 * Copyright 2014 Zwyk.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package auguste.engine.entity;

import auguste.engine.GameListener;
import auguste.engine.GameTimer;
import auguste.engine.entity.action.Action;
import auguste.engine.entity.action.Movement;
import auguste.engine.entity.pawn.Armor;
import auguste.engine.entity.pawn.Laurel;
import auguste.engine.entity.pawn.Pawn;
import auguste.engine.entity.pawn.Soldier;
import auguste.engine.turnData.Battle;
import auguste.engine.turnData.Move;
import auguste.engine.turnData.Tenaille;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe représentant une partie.
 * @author Zwyk
 */
public class Game
{
    // Variables de classe
    private long turnDuration;
    private final ArrayList<Team> teams;
    private final ArrayList<Legion> legions;
    
    private final ArrayList<Action> actions;
    private GameTimer timer;
    private int turn = 1;
    
    // Variables pour l'affichage client
    private final GameListener listener;
    private final ArrayList<Move> moves;
    private final ArrayList<Tenaille> tenailles;
    private final ArrayList<Battle> battles;
    
    // Variables métier
    private final ArrayList<Player> players;
    private Board board;
    
    /**
    * Instanciation d'une partie avec son gérant.
    * @param listener Game listener
    * @param turnDuration durée d'un tour
    */
    public Game(GameListener listener, int turnDuration)
    {
        this.listener = listener;
        this.turnDuration = turnDuration;
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
        this.legions = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.tenailles = new ArrayList<>();
        this.battles = new ArrayList<>();
        this.timer = new GameTimer(this,turnDuration);
    }
    
    public Game(int turnDuration)
    {
        this(null, turnDuration);
    }
    
    /**
    * Ajoute une action.
     * @param a Action
    */
    public void addAction(Action a)
    {
        Legion l = a.getLegion();
        if(l.getAction() != null)
        {
            this.actions.remove(l.getAction());
        }
        a.getLegion().setAction(a);
        this.actions.add(a);
    }
    
    public void nextTurn()
    {
        turn++;
        moves.clear();
        tenailles.clear();
        battles.clear();
    }
    
    /**
    * Applique les actions.
    */
    public void applyActions()
    {
        calculateMoves();
        applyMoves();
        calculateTenailles();
        applyTenailles();
        calculateBattles();
        applyBattles();
        for(Action a : this.actions)
        {
            a.getLegion().setAction(null);
        }
        this.actions.clear();
        this.timer = new GameTimer(this,turnDuration);
        this.timer.start();
    }
    
    /**
    * Calcule les mouvements et leurs effets (collisions, correct ou non..)
    */
    public void calculateMoves()
    {
        ArrayList<Movement> moveActions = new ArrayList<>();
        for(Action a : actions)
        {
            if(a.getMovement() != null)
            {
                moveActions.add(a.getMovement());
            }
        }
        
        Pawn p;
        Cell c;
        Move move;
        boolean dies;
        Movement m;
        Pawn tp;
        
        for(Action a : actions)
        {
            m = a.getMovement();
            if(a.getMovement() != null)
            {
                p = m.getPawn();
                c = m.getCell();
                move = null;
                tp = c.getPawn();
                if(p != null && p.getLegion() == a.getLegion() && (tp == null || (p instanceof Soldier && ((Soldier)p).isArmored() == false && tp instanceof Armor)) && correctMove(m))
                {
                    move = new Move(p.getCell().getP(),c.getP(),false);
                    dies = false;
                    for(Movement m2 : moveActions)
                    {
                        if(m2 != m)
                        {
                            if(m2.getPawn().getCell() == c)
                            {
                                move = null;
                                break;
                            }
                            else if(m2.getCell() == c)
                            {
                                dies = true;
                            }
                        }
                    }
                    
                    if(dies && move != null)
                    {
                        p.setCell(null);
                        move.setDies(true);
                    }
                }

                if(move != null) moves.add(move);
            }
        }
    }
    
    private void applyMoves()
    {
        Cell c1;
        Cell c2;
        Pawn p1;
        Pawn p2;
        for(Move m : moves)
        {
            c1 = board.getCell(m.getP1());
            c2 = board.getCell(m.getP2());
            p1 = c1.getPawn();
            p2 = c2.getPawn();
            
            p1.setCell(c2);
            if(p2 != null && p2 instanceof Armor)
            {
                p2.setCell(null);
                ((Soldier)p1).setArmored(true);
            }
            c2.setPawn(p1);
            c1.setPawn(null);
        }
    }
    
    /**
    * Calcule les tenailles.
    */
    public void calculateTenailles()
    {
        //TODO
    }
    
    public void applyTenailles()
    {
        Pawn p1;
        Pawn p2;
        int orientation;
        
        for(Tenaille t : tenailles)
        {
            p1 = board.getCell(t.getP1()).getPawn();
            p2 = board.getCell(t.getP2()).getPawn();
            
            if(p1 != null && p2 != null)
            {
                orientation = getOrientation(p1.getCell(),p2.getCell());
                for(Cell c = p1.getCell(); c != p2.getCell(); c = getCell(c,orientation))
                {
                    if(c != p1.getCell())
                    {
                        c.getPawn().setCell(null);
                        c.setPawn(null);
                    }
                }
            }
        }
    }
    
    /**
    * Calcule les batailles.
    */
    public void calculateBattles()
    {
        Soldier p1;
        Soldier p2;
        int orientation;
        int battleRes;
        boolean exists;
        
        for(Cell c : board.getCells())
        {
            if(c.getPawn() != null && c.getPawn() instanceof Soldier)
            {
                p1 = (Soldier) c.getPawn();
                for(Soldier p : nearlyEnnemies(p1))
                {
                    exists = false;
                    for(Battle b : battles)
                    {
                        if((b.getP1().x == p.getCell().getP().x && b.getP1().y == p.getCell().getP().y && b.getP2().x == p1.getCell().getP().x && b.getP2().y == p1.getCell().getP().y)
                            ||(b.getP1().x == p1.getCell().getP().x && b.getP1().y == p1.getCell().getP().y && b.getP2().x == p.getCell().getP().x && b.getP2().y == p.getCell().getP().y))
                        {
                            exists = true;
                            break;
                        }
                    }
                    if(!exists)
                    {
                        orientation = getOrientation(p1.getCell(),p.getCell());
                        battleRes = lineForce(p1,(orientation+3)%6) - lineForce(p,(orientation+3)%6);
                        battles.add(new Battle(p1.getCell().getP(),p.getCell().getP(),battleRes==0?null:battleRes<0?p1.getCell().getP():p.getCell().getP()));
                    }
                }
            }
        }
    }
    
    private int lineForce(Soldier p1, int orientation)
    {
        int res = p1.isArmored()?2:1;
        
        Pawn p = getCell(p1.getCell(),orientation).getPawn();
        while(p != null && p instanceof Soldier && p.getLegion().getPlayer().getTeam() == p1.getLegion().getPlayer().getTeam())
        {
            res += 1;
            p = getCell(p.getCell(),orientation).getPawn();
        }
        
        return res;
    }
    
    public void applyBattles()
    {
        Pawn p1;
        Pawn p2;
        Pawn dies;
        
        for(Battle b : battles)
        {
            p1 = board.getCell(b.getP1()).getPawn();
            p2 = board.getCell(b.getP2()).getPawn();
            dies = b.getDies()!=null?board.getCell(b.getP2()).getPawn():null;
            if(dies == p1)
            {
                p1.getCell().setPawn(null);
                p1.setCell(null);
            }
            else if(dies == p2)
            {
                p2.getCell().setPawn(null);
                p2.setCell(null);
            }
        }
    }
    
    /**
    * Initialise le plateau.
    */
    public void initBoard()
    {
        fillLegions();
        for(Legion l : legions)                                     // Soldiers
        {
            switch(board.getSize())
            {
                case 3:                                                       // Test Board
                    initCell(-2,-2,new Soldier(l),l.getPosition(),true);
                    break;
                case 5:                                                       // Test Board
                    initCell(-4,-4,new Soldier(l),l.getPosition(),true);
                    initCell(-4,-3,new Soldier(l),l.getPosition(),true);
                    initCell(-3,-3,new Soldier(l),l.getPosition());
                    initCell(-3,-4,new Soldier(l),l.getPosition(),true);
                    break;
                case 7:
                    initCell(-6,-6,new Soldier(l),l.getPosition(),true);
                    initCell(-6,-5,new Soldier(l),l.getPosition(),true);
                    initCell(-6,-4,new Soldier(l),l.getPosition());
                    initCell(-5,-6,new Soldier(l),l.getPosition(),true);
                    initCell(-5,-5,new Soldier(l),l.getPosition());
                    initCell(-5,-4,new Soldier(l),l.getPosition());
                    initCell(-4,-6,new Soldier(l),l.getPosition());
                    initCell(-4,-5,new Soldier(l),l.getPosition());
                    initCell(-4,-4,new Soldier(l),l.getPosition());
                    initCell(-4,-3,new Soldier(l),l.getPosition());
                    initCell(-3,-4,new Soldier(l),l.getPosition());
                    initCell(-3,-3,new Soldier(l),l.getPosition());
                    break;
                case 9:
                    initCell(-8,-8,new Soldier(l),l.getPosition(),true);
                    initCell(-8,-7,new Soldier(l),l.getPosition(),true);
                    initCell(-8,-6,new Soldier(l),l.getPosition());
                    initCell(-7,-8,new Soldier(l),l.getPosition(),true);
                    initCell(-7,-7,new Soldier(l),l.getPosition());
                    initCell(-7,-6,new Soldier(l),l.getPosition());
                    initCell(-7,-5,new Soldier(l),l.getPosition());
                    initCell(-6,-8,new Soldier(l),l.getPosition());
                    initCell(-6,-7,new Soldier(l),l.getPosition());
                    initCell(-6,-6,new Soldier(l),l.getPosition());
                    initCell(-6,-5,new Soldier(l),l.getPosition());
                    initCell(-5,-7,new Soldier(l),l.getPosition());
                    initCell(-5,-6,new Soldier(l),l.getPosition());
                    initCell(-5,-5,new Soldier(l),l.getPosition());
                    initCell(-5,-4,new Soldier(l),l.getPosition());
                    initCell(-4,-5,new Soldier(l),l.getPosition());
                    initCell(-4,-4,new Soldier(l),l.getPosition());
                    break;
                case 11:
                    initCell(-10,-10,new Soldier(l),l.getPosition(),true);
                    initCell(-10,-9,new Soldier(l),l.getPosition(),true);
                    initCell(-10,-8,new Soldier(l),l.getPosition(),true);
                    initCell(-10,-7,new Soldier(l),l.getPosition());
                    initCell(-9,-10,new Soldier(l),l.getPosition(),true);
                    initCell(-9,-9,new Soldier(l),l.getPosition());
                    initCell(-9,-8,new Soldier(l),l.getPosition());
                    initCell(-9,-7,new Soldier(l),l.getPosition());
                    initCell(-8,-10,new Soldier(l),l.getPosition(),true);
                    initCell(-8,-9,new Soldier(l),l.getPosition());
                    initCell(-8,-8,new Soldier(l),l.getPosition());
                    initCell(-8,-7,new Soldier(l),l.getPosition());
                    initCell(-7,-10,new Soldier(l),l.getPosition());
                    initCell(-7,-9,new Soldier(l),l.getPosition());
                    initCell(-7,-8,new Soldier(l),l.getPosition());
                    initCell(-7,-7,new Soldier(l),l.getPosition());
                    initCell(-7,-6,new Soldier(l),l.getPosition());
                    initCell(-6,-7,new Soldier(l),l.getPosition());
                    initCell(-6,-6,new Soldier(l),l.getPosition());
                    break;
            }
        }
        for(int i = 0; i <= 5; i++)                                 // Armors
        {
            switch(board.getSize())
            {
                case 5:
                    initCell(-1,-1,new Armor(),i);
                    break;
                case 7:
                    initCell(-4,-2,new Armor(),i);
                    initCell(-2,-1,new Armor(),i);
                    break;
                case 9:
                    initCell(-4,-8,new Armor(),i);
                    initCell(-3,-6,new Armor(),i);
                    initCell(-2,-4,new Armor(),i);
                    initCell(-1,-2,new Armor(),i);
                    break;
                case 11:
                    initCell(-5,-10,new Armor(),i);
                    initCell(-4,-8,new Armor(),i);
                    initCell(-3,-6,new Armor(),i);
                    initCell(-1,-2,new Armor(),i);
                    break;
            }
        }
        board.getCell(new Point(0,0)).setPawn(new Laurel());        // Laurel
        this.timer.start();
    }
    
    /**
     * Calcule si le mouvement est correct ou non
     * @param m Mouvement
     * @return Mouvement correct ou non
     */
    private boolean correctMove(Movement m)
    {
        return nearlyEmptyCells(friendlyGroup(m.getPawn()),true).contains(m.getCell());
    }
    
    private ArrayList<Cell> nearlyEmptyCells(ArrayList<Pawn> group, boolean armorIsOk)
    {
        ArrayList<Cell> res = new ArrayList<>();
        
        for(Pawn p : group)
        {
            for(Cell c : nearlyCells(p.getCell()))
            {
                if((c.getPawn() == null || (armorIsOk && c.getPawn() instanceof Armor)) && !res.contains(c))
                {
                    res.add(c);
                }
            }
        }
        return res;
    }
    
    private ArrayList<Pawn> friendlyGroup(Pawn p)
    {
        ArrayList<Pawn> res = new ArrayList<>();
        ArrayList<Pawn> next = new ArrayList<>();
        ArrayList<Pawn> tempNext;
        ArrayList<Pawn> near;
        next.add(p);
        boolean stop = false;
        
        while(!stop)
        {
            tempNext = new ArrayList<>();
            for(Pawn p1 : next)
            {
                near = nearlyFriends(p1);
                for(Pawn p2 : near)
                {
                    if(!res.contains(p2) && !next.contains(p2) && !tempNext.contains(p2))
                    {
                        tempNext.add(p2);
                    }
                }
                res.add(p1);
            }
            if(tempNext.isEmpty())
            {
                stop = true;
            }
            else
            {
                next = tempNext;
            }
        }
        
        return res;
    }
    
    private ArrayList<Pawn> nearlyFriends(Pawn p)
    {
        ArrayList<Pawn> res = new ArrayList<>();
        ArrayList<Cell> cells = nearlyCells(p.getCell());
        Pawn tPawn;
        for(Cell c : cells)
        {
            tPawn = c.getPawn();
            if(tPawn != null && tPawn.getLegion() == p.getLegion())
            {
                res.add(tPawn);
            }
        }
        
        return res;
    }
    
    private ArrayList<Soldier> nearlyEnnemies(Pawn p)
    {
        ArrayList<Soldier> res = new ArrayList<>();
        ArrayList<Cell> cells = nearlyCells(p.getCell());
        Pawn tPawn;
        for(Cell c : cells)
        {
            tPawn = c.getPawn();
            if(tPawn != null && tPawn instanceof Soldier && tPawn.getLegion().getPlayer().getTeam() != p.getLegion().getPlayer().getTeam())
            {
                res.add((Soldier)tPawn);
            }
        }
        
        return res;
    }
    
    private ArrayList<Cell> nearlyCells(Cell c)
    {
        ArrayList<Cell> res = new ArrayList<>();
        int x = c.getP().x;
        int y = c.getP().y;
        List<Point> pArray =
                Arrays.asList(
                        new Point(x,y-1),
                        new Point(x,y+1),
                        new Point(x-1,y),
                        new Point(x+1,y),
                        new Point(x-1,(x>0?y+1:y-1)),
                        new Point(x+1,(x<0?y+1:y-1)));
        
        for(Point p : pArray)
        {
            if(board.getCell(p) != null)
            {
                res.add(board.getCell(p));
            }
        }
        
        return res;
    }
    
    // NB : Orientation c2 par rapport à c1 de 0 à 5 dans le sens horaires avec 0 = haut droite
    public int getOrientation(Cell c1, Cell c2)
    {
        int res = -1;
        Point p1 = c1.getP();
        Point p2 = c2.getP();
        
        if(p1.x == p2.x)
        {
            if(p1.y == p2.y-1)
            {
                res = 1;
            }
            else if(p1.y == p2.y+1)
            {
                res = 4;
            }
        }
        else if(p1.x == p2.x+1)
        {
            if(p1.x > 0)
            {
                if(p1.y == p2.y)
                {
                    res = 5;
                }
                else if(p1.y == p2.y-1)
                {
                    res = 0;
                }
            }
            else
            {
                if(p1.y == p2.y+1)
                {
                    res = 5;
                }
                else if(p1.y == p2.y)
                {
                    res = 0;
                }
            }
        }
        else if(p1.x == p2.x-1)
        {
            if(p1.x < 0)
            {
                if(p1.y == p2.y)
                {
                    res = 3;
                }
                else if(p1.y == p2.y-1)
                {
                    res = 2;
                }
            }
            else
            {
                if(p1.y == p2.y+1)
                {
                    res = 3;
                }
                else if(p1.y == p2.y)
                {
                    res = 2;
                }
            }
        }
        
        return res;
    }
    
    public Cell getCell(Cell c, int orientation)
    {
        Cell res = null;
        Point p = c.getP();
        
        switch(orientation)
        {
            case 0:
                res = board.getCell(new Point(p.x-1,p.x>0?p.y+1:p.y));
                break;
            case 1:
                res = board.getCell(new Point(p.x,p.y+1));
                break;
            case 2:
                res = board.getCell(new Point(p.x+1,p.x<0?p.y+1:p.y));
                break;
            case 3:
                res = board.getCell(new Point(p.x+1,p.x<0?p.y:p.y-1));
                break;
            case 4:
                res = board.getCell(new Point(p.x,p.y-1));
                break;
            case 5:
                res = board.getCell(new Point(p.x-1,p.x>0?p.y:p.y-1));
                break;
        }
        
        return res;
    }
    
    /**
    * Initialise une case.
     * @param x pos x
     * @param y pos y
     * @param pawn pion
     * @param rotation nombre de rotations
     * @param tent s'il y a une tente
    */
    public void initCell(int x, int y, Pawn pawn, int rotation, boolean tent)
    {
        Cell c = board.getCell(board.getRotatedPosition(new Point(x,y), rotation));
        c.setPawn(pawn);
        pawn.setCell(c);
        if(tent) c.setTent(pawn.getLegion());
    }        
    
    /**
    * Initialise une case sans tente.
     * @param x pos x
     * @param y pos y
     * @param pawn pion
     * @param rotation nombre de rotations
    */
    public void initCell(int x, int y, Pawn pawn, int rotation)
    {
        initCell(x,y,pawn,rotation,false);
        
    }

    /**
    * Remplit la liste légions.
    */
    private void fillLegions()
    {
        for(Team t : teams)
        {
            for(Player p : t.getPlayers())
            {
                for(Legion l : p.getLegions())
                {
                    legions.add(l);
                }
            }
        }
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    
    /**
     * @param player the player to add
     */
    public final void addPlayer(Player player)
    {
        this.players.add(player);
        if(!this.teams.contains(player.getTeam()))
        {
            this.teams.add(player.getTeam());
        }
    }
    
    /**
     * @param player the player to remove
     */
    public void removePlayer(Player player)
    {
        this.players.remove(player);
    }

    /**
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }

    /**
     * @return the actions
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * @return the listener
     */
    public GameListener getListener() {
        return listener;
    }

    /**
     * @return the moves
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * @return the tenailles
     */
    public ArrayList<Tenaille> getTenailles() {
        return tenailles;
    }

    /**
     * @return the battles
     */
    public ArrayList<Battle> getBattles() {
        return battles;
    }

    /**
     * @return the timer
     */
    public GameTimer getTimer() {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(GameTimer timer) {
        this.timer = timer;
    }

    /**
     * @return the turnDuration
     */
    public long getTurnDuration() {
        return turnDuration;
    }

    /**
     * @param turnDuration the turnDuration to set
     */
    public void setTurnDuration(long turnDuration) {
        this.turnDuration = turnDuration;
    }

    /**
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }
}
