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
    private int turnDuration;
    private final ArrayList<Team> teams;
    private final ArrayList<Legion> legions;
    
    private final ArrayList<Action> actions;
    private GameTimer timer;
    
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
    
    /**
    * Applique les actions.
    */
    public void applyActions()
    {
        applyMoves();
        applyTenailles();
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
    * Applique les mouvements.
    */
    public void applyMoves()
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
        boolean moveOk;
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
                    p.getCell().setPawn(null);
                    moveOk = true;
                    for(Movement m2 : moveActions)
                    {
                        if(m2 != m && m2.getCell() == c)
                        {
                            p.setCell(null);
                            move.setDies(true);
                            moveOk = false;
                            break;
                        }
                    }
                    if(moveOk)
                    {
                        p.setCell(c);
                        if(tp != null && tp instanceof Armor)
                        {
                            tp.setCell(null);
                            ((Soldier)p).setArmored(true);
                        }
                        c.setPawn(p);
                    }
                }

                if(move != null) moves.add(move);
            }
        }
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
                        new Point(x-1,(x>0?x+1:x-1)),
                        new Point(x+1,(x<0?x+1:x-1)));
        
        for(Point p : pArray)
        {
            if(board.getCell(p) != null)
            {
                res.add(board.getCell(p));
            }
        }
        
        return res;
    }
    
    /**
    * Applique les tenailles.
    */
    public void applyTenailles()
    {
        
    }
    
    /**
    * Applique les batailles.
    */
    public void applyBattles()
    {
        
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
    public int getTurnDuration() {
        return turnDuration;
    }

    /**
     * @param turnDuration the turnDuration to set
     */
    public void setTurnDuration(int turnDuration) {
        this.turnDuration = turnDuration;
    }
}
