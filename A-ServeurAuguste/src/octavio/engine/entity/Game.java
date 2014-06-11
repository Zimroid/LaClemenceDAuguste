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

package octavio.engine.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import octavio.engine.GameListener;
import octavio.engine.GameTimer;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.engine.entity.pawn.Armor;
import octavio.engine.entity.pawn.Laurel;
import octavio.engine.entity.pawn.Pawn;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.entity.pawn.Wall;
import octavio.engine.ia.IA;
import octavio.engine.turnData.Battle;
import octavio.engine.turnData.Move;
import octavio.engine.turnData.Tenaille;

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
    private Laurel laurel;
    private Legion winner = null;
    private Team twinner = null;
    private int pawnsAlive = 0;
    private final ArrayList<Action> actions;
    private Timer timer;
    private int turn = 1;
    
    // Variables pour l'affichage client
    private final GameListener listener;
    private final ArrayList<Move> moves;
    private final ArrayList<Tenaille> tenailles;
    private final ArrayList<Battle> battles;
    
    // Variables métier
    private final ArrayList<Player> players;
    private Board board;
    private IA ia;
    
    /**
    * Instanciation d'une partie avec son gérant.
    * @param listener Game listener
    * @param turnDuration durée d'un tour
    */
    public Game(GameListener listener, long turnDuration)
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
        this.ia = new IA(this);
    }
    
    public Game(GameListener listener, long turnDuration, Board b)
    {
        this(listener,turnDuration);
        this.board = b;
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
        if(winner==null)
        {
            Legion l = a.getLegion();
            if(l.getAction() != null)
            {
                this.actions.remove(l.getAction());
            }
            l.setAction(a);
            this.actions.add(a);
            if (this.actions.size() == nbAliveLegions()) 
            {
                if(this.getListener() != null) this.getListener().onTurnEnd(); // timer.notify();
            }
        }
    }

    /**
     * @return the teams
     */
    public ArrayList<Team> getTeams()
    {
        return teams;
    }
    
    public int nbAliveLegions()
    {
        int res = 0;
        for(Legion l : legions)
        {
            if(l.getLivingPawns().size() > 0) res++;
        }
        return res;
    }

    /**
     * @return the legions
     */
    public ArrayList<Legion> getLegions()
    {
        return legions;
    }

    /**
     * @return the pawnsAlive
     */
    public int getPawnsAlive()
    {
        return pawnsAlive;
    }

    /**
     * @return the timer
     */
    public Timer getTimer()
    {
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(Timer timer)
    {
        this.timer = timer;
    }
    
    public void nextTurn()
    {
        turn++;
        moves.clear();
        tenailles.clear();
        battles.clear();
        if(listener!=null)
        {
            timer.cancel();
            timer.purge();
        }
        turn();
    }
    
    public void turn()
    {
        if(listener!=null)
        {
            timer = new Timer();
            timer.schedule(new GameTimer(this), turnDuration);
        }
        playBots();
    }
    
    private void playBots()
    {
        players.stream().filter((p) -> (p.getBot() != null && !p.isConnected())).forEach((p) ->
        {
            (new Thread() {
                @Override
                public void run() {
                    if(allPlayersAreBots() && listener!=null) try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    ia.activateBot(p.getBot()).stream().forEach((a) ->
                    {
                        synchronized(Game.this) { addAction(a); }
                    });
                }
            }).start();
        });
    }
    
    public boolean allPlayersAreBots()
    {
        boolean res = true;
        for(Player p : players)
        {
            if(p.isConnected() && playerHasLegionLeft(p))
            {
                res = false;
                break;
            }
        }
        return res;
    }
    
    public boolean playerHasLegionLeft(Player p)
    {
        boolean res = false;
        for(Legion l : p.getLegions())
        {
            if(l.getLivingPawns().size() > 0)
            {
                res = true;
                break;
            }
        }
        return res;
    }
    
    /**
    * Applique les actions.
    * @return Legion gagnane (null si partie non terminée)
     * @throws java.lang.InterruptedException
    */
    public boolean applyActions() throws InterruptedException
    {
        calculateMoves();
        boolean ends;
        ends = applyMoves();
        if(ends == false)
        {
            calculateTenailles();
            applyTenailles();
            calculateBattles();
            applyBattles();
            this.actions.stream().forEach((a) -> {
                a.getLegion().setAction(null);
            });
            this.actions.clear();
            teamWinsCheck();
            if(twinner != null) ends = true;
        }
        return ends;
    }
    
    private void teamWinsCheck()
    {
        Team w = null;
        boolean hasLegion;
        int nbT = 0;
        for(Team t : teams)
        {
            hasLegion = false;
            for(Player p : t.getPlayers())
            {
                for(Legion l : p.getLegions())
                {
                    if(l.getLivingPawns().size() > 0)
                    {
                        w = t;
                        nbT++;
                        hasLegion = true;
                        break;
                    }
                }
                if(hasLegion) break;
            }
        }
        if(nbT == 1) twinner = w;
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
                else if(p != null && p instanceof Laurel && canMoveLaurel(a.getLegion(),p) && correctMove(m,true))
                {
                    move = new Move(p.getCell().getP(),c.getP(),false);
                    for(Movement m2 : moveActions)
                    {
                        if(m2 != m && m2.getPawn() == p)
                        {
                            move = null;
                            break;
                        }
                    }
                }

                if(move != null) moves.add(move);
            }
        }
    }
    
    private boolean applyMoves()
    {
        Cell c1;
        Cell c2;
        Pawn p1;
        Pawn p2;
        boolean ends = false;
        for(Move m : moves)
        {
            c1 = board.getCell(m.getP1());
            c2 = board.getCell(m.getP2());
            p1 = c1.getPawn();
            p2 = c2.getPawn();
            
            if(!m.isDies())
            {
                p1.setCell(c2);
                if(p2 != null && p2 instanceof Armor)
                {
                    p2.setCell(null);
                    ((Soldier)p1).setArmored(true);
                }
                else if(p1 instanceof Laurel && c2.getTent() != null)
                {
                    ends = true;
                    winner = c2.getTent();
                    twinner = winner.getPlayer().getTeam();
                }
                c2.setPawn(p1);
                c1.setPawn(null);
            }
            else
            {
                c1.setPawn(null);
                p1.setCell(null);
                p1.setDeleted(true);
                pawnsAlive--;
            }
            if(pawnsAlive == 0) ends = true;
            if(ends == true) break;
        }
        return ends;
    }
    
    /**
    * Calcule les tenailles.
    */
    public void calculateTenailles()
    {
        Pawn p;
        int orientation;
        Cell tc;
        boolean wall;
        boolean exists;
        
        for(Cell c : board.getCells().values())
        {
            if(c.getPawn() instanceof Soldier)
            {
                p = c.getPawn();
                for(Soldier s : nearbyTenailleEnnemies(p))
                {
                    orientation = getOrientation(p.getCell(),s.getCell());
                    tc = s.getCell();
                    wall = false;
                    while(!wall && tc != null && tc.getPawn() != null && tc.getPawn() instanceof Soldier && tc.getPawn().getLegion().getPlayer().getTeam() == s.getLegion().getPlayer().getTeam())
                    {
                        tc = getCell(tc,orientation);
                        if(tc != null && tc.getPawn() != null && tc.getPawn() instanceof Wall)
                        {
                            wall = true;
                        }
                    }
                    if(!wall)
                    {
                        if(tc != null && (tc.getPawn() == null || !(tc.getPawn() instanceof Soldier)))
                        {
                            tc = getCell(tc,orientation);
                        }
                        if(tc != null && tc.getPawn() != null && tc.getPawn() instanceof Soldier && tc.getPawn().getLegion().getPlayer().getTeam() != s.getLegion().getPlayer().getTeam())
                        {
                            exists = false;
                            for(Tenaille t : tenailles)
                            {
                                if(t.getP1().x == p.getCell().getP().x && t.getP1().y == p.getCell().getP().y && t.getP2().x == tc.getP().x && t.getP2().y == tc.getP().y
                                        || t.getP2().x == p.getCell().getP().x && t.getP2().y == p.getCell().getP().y && t.getP1().x == tc.getP().x && t.getP1().y == tc.getP().y)
                                {
                                    exists = true;
                                }
                            }
                            if(!exists)
                            {
                                tenailles.add(new Tenaille(p.getCell().getP(),tc.getP()));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public ArrayList<Soldier> nearbyTenailleEnnemies(Pawn p)
    {
        ArrayList<Soldier> res = new ArrayList<>();
        Cell c1;
        Cell c2;
        
        for(int orientation = 0; orientation < 6; orientation++)
        {
            c1 = getCell(p.getCell(),orientation);
            if(c1 != null)
            {
                if(c1.getPawn() != null && c1.getPawn() instanceof Soldier && c1.getPawn().getLegion().getPlayer().getTeam() != p.getLegion().getPlayer().getTeam())
                {
                    res.add((Soldier)c1.getPawn());
                }
                if(!(c1.getPawn() != null && c1.getPawn() instanceof Wall))
                {
                    c2 = getCell(c1,orientation);
                    if(c2 != null && c2.getPawn() != null && c2.getPawn() instanceof Soldier && c2.getPawn().getLegion().getPlayer().getTeam() != p.getLegion().getPlayer().getTeam())
                    {
                        res.add((Soldier)c2.getPawn());
                    }
                }
            }
        }
        
        return res;
    }
    
    public void applyTenailles()
    {
        Pawn p1;
        Pawn p2;
        Pawn p;
        int orientation;
        for(Tenaille t : tenailles)
        {
            p1 = board.getCell(t.getP1()).getPawn();
            p2 = board.getCell(t.getP2()).getPawn();
            if(p1 != null && p2 != null)
            {
                orientation = getOrientation(p1.getCell(),p2.getCell());
                for(Cell c = p1.getCell(); c != p2.getCell() && c != null; c = getCell(c,orientation))
                {
                    if(c != null && c != p1.getCell() && c.getPawn() != null && c.getPawn() instanceof Soldier)
                    {
                        p = c.getPawn();
                        p.setCell(null);
                        c.setPawn(null);
                        p.setDeleted(true);
                        pawnsAlive--;
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
        int orientation;
        int battleRes;
        boolean exists;
        
        for(Cell c : board.getCells().values())
        {
            if(c.getPawn() != null && c.getPawn() instanceof Soldier)
            {
                p1 = (Soldier) c.getPawn();
                for(Soldier p : nearbyEnnemies(p1))
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
                        battleRes = lineForce(p1,(orientation+3)%6) - lineForce(p,orientation);
                        battles.add(new Battle(p1.getCell().getP(),p.getCell().getP(),battleRes==0?null:battleRes<0?p1.getCell().getP():p.getCell().getP()));
                    }
                }
            }
        }
    }
    
    private int lineForce(Soldier p1, int orientation)
    {
        int res = p1.isArmored()?2:1;
        
        Cell c = getCell(p1.getCell(),orientation);
        Pawn p = c!=null?c.getPawn():null;
        while(p != null && p instanceof Soldier && p.getLegion().getPlayer().getTeam() == p1.getLegion().getPlayer().getTeam())
        {
            res += 1;
            if (getCell(p.getCell(),orientation) != null) p = getCell(p.getCell(),orientation).getPawn();
            else p = null;
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
            dies = b.getDies()!=null?board.getCell(b.getDies()).getPawn():null;
            if(p1 != null && p2 != null)
            {
                if(dies == p1)
                {
                    p1.getCell().setPawn(null);
                    p1.setCell(null);
                    p1.setDeleted(true);
                    pawnsAlive--;
                }
                else if(dies == p2)
                {
                    p2.getCell().setPawn(null);
                    p2.setCell(null);
                    p2.setDeleted(true);
                    pawnsAlive--;
                }
            }
        }
    }
    
    public void initBoard(ArrayList<Pawn> pawns)
    {
        for(Pawn p : pawns) {
            p.getCell().setPawn(p);
            if(p.getLegion() != null) {
                p.getLegion().getPawns().add(p);
                if(p instanceof Soldier) pawnsAlive++;
            }
        }
        for(Legion l: legions)
        {
            switch(board.getSize())
            {
                case 3:
                    board.getCell(board.getRotatedPosition(new Point(-2,-2), l.getPosition())).setTent(l);
                    break;
                case 5:
                    board.getCell(board.getRotatedPosition(new Point(-4,-4), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-4,-3), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-3,-4), l.getPosition())).setTent(l);
                    break;
                case 7:
                    board.getCell(board.getRotatedPosition(new Point(-6,-6), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-6,-5), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-5,-6), l.getPosition())).setTent(l);
                    break;
                case 9:
                    board.getCell(board.getRotatedPosition(new Point(-8,-8), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-8,-7), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-7,-8), l.getPosition())).setTent(l);
                    break;
                case 11:
                    board.getCell(board.getRotatedPosition(new Point(-10,-10), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-10,-9), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-9,-10), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-10,-8), l.getPosition())).setTent(l);
                    board.getCell(board.getRotatedPosition(new Point(-8,-10), l.getPosition())).setTent(l);
                    break;
            }
        }
        turn();
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
        laurel = new Laurel();
        initCell(0,0,laurel,0);        // Laurel
        turn();
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
        if(pawn.getLegion() != null) {
            pawn.getLegion().getPawns().add(pawn);
            if(pawn instanceof Soldier) pawnsAlive++;
        }
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
    
    public boolean canMoveLaurel(Legion l, Pawn laurel)
    {
        boolean res = false;
        for(Cell c : nearbyCells(laurel.getCell()))
        {
            if(c.getPawn() != null && c.getPawn() instanceof Soldier && c.getPawn().getLegion() == l)
            {
                res = true;
                break;
            }
        }
        
        return res;
    }
    
    /**
     * Calcule si le mouvement est correct ou non
     * @param m Mouvement
     * @return Mouvement correct ou non
     */
    public boolean correctMove(Movement m)
    {
        return correctMove(m,false);
    }
    
    public boolean correctMove(Movement m, boolean isLaurel)
    {
        if(isLaurel)
        {
            return nearbyEmptyCells(m.getPawn(),false).contains(m.getCell());
        }
        else
        {
            return nearbyEmptyCells(friendlyGroup(m.getPawn()),true).contains(m.getCell());
        }
    }
    
    public ArrayList<Cell> nearbyEmptyCells(Pawn pawn, boolean armorIsOk)
    {
        ArrayList<Pawn> l = new ArrayList<>();
        l.add(pawn);
        return nearbyEmptyCells(l,armorIsOk);
    }
    
    public ArrayList<Cell> nearbyEmptyCells(ArrayList<Pawn> group, boolean armorIsOk)
    {
        ArrayList<Cell> res = new ArrayList<>();
        
        for(Pawn p : group)
        {
            for(Cell c : nearbyCells(p.getCell()))
            {
                if((c.getPawn() == null || (armorIsOk && c.getPawn() instanceof Armor)) && !res.contains(c))
                {
                    res.add(c);
                }
            }
        }
        return res;
    }
    
    public ArrayList<Pawn> friendlyGroup(Pawn p)
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
                near = nearbyFriends(p1);
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
    
    public ArrayList<Pawn> nearbyFriends(Pawn p)
    {
        ArrayList<Pawn> res = new ArrayList<>();
        ArrayList<Cell> cells = nearbyCells(p.getCell());
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
    
    public ArrayList<Soldier> nearbyEnnemies(Pawn p)
    {
        ArrayList<Soldier> res = new ArrayList<>();
        ArrayList<Cell> cells = nearbyCells(p.getCell());
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
    
    public ArrayList<Cell> nearbyCells(Cell c)
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
                        new Point(x-1,y+(((x*2)-1)%2)),     //new Point(x-1,(x>0?y+1:y-1)),
                        new Point(x+1,y-(((x*2)+1)%2)));    //new Point(x+1,(x<0?y+1:y-1)));

        for(Point p : pArray)
        {
            if(board.getCell(p) != null)
            {
                res.add(board.getCell(p));
            }
        }

        return res;
    }
    
    // NB : Orientation c2 par rapport à c1 de 0 à 5 dans le sens horaires avec 0 = haut gauche
    public int getOrientation(Cell c1, Cell c2)
    {
        int res = -1;
        Point p1 = c1.getP();
        Point p2 = c2.getP();
        
        boolean reverse = false;
        if(p1.x < p2.x) {
            reverse = true;
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
        
        if(p1.x == p2.x)
        {
            if(p1.y < p2.y)
            {
                res = 2;
            }
            else if(p1.y > p2.y)
            {
                res = 5;
            }
        }
        else
        {
            if(p1.x > 0)
            {
                if(p1.x == p2.x+1 && p1.y == p2.y)
                {
                        res = 0;
                }
                else if(p1.x == p2.x+1 && p1.y+1 == p2.y)
                {
                        res = 1;
                }
                else if(p2.x <= 0)
                {
                    if(p1.y < p2.y)
                    {
                        res = 1;
                    }
                    else if(p1.y >= p2.y)
                    {
                        res = 0;
                    }
                }
                else if(p2.x > 0)
                {
                    if(p1.y >= p2.y)
                    {
                        res = 0;
                    }
                    else if(p1.y < p2.y)
                    {
                        res = 1;
                    }
                }
            }
            else if(p1.x < 0)
            {
                if(p1.x == p2.x+1 || p2.x < 0)
                {
                    if(p1.y > p2.y)
                    {
                        res = 0;
                    }
                    else if(p1.y <= p2.y)
                    {
                        res = 1;
                    }
                }
            }
            else
            {
                if(p1.y <= p2.y)
                {
                    res = 1;
                }
                else if(p1.y > p2.y)
                {
                    res = 0;
                }
            }
        }
        
        return res==-1?res:(reverse?(res+3)%6:res);
    }
    
    public Cell getCell(Cell c, int orientation)
    {
        Cell res = null;
        Point p = c.getP();
        
        switch(orientation)
        {
            case 0:
                res = board.getCell(new Point(p.x-1,p.x>0?p.y:p.y-1));
                break;
            case 1:
                res = board.getCell(new Point(p.x-1,p.x>0?p.y+1:p.y));
                break;
            case 2:
                res = board.getCell(new Point(p.x,p.y+1));
                break;
            case 3:
                res = board.getCell(new Point(p.x+1,p.x<0?p.y+1:p.y));
                break;
            case 4:
                res = board.getCell(new Point(p.x+1,p.x<0?p.y:p.y-1));
                break;
            case 5:
                res = board.getCell(new Point(p.x,p.y-1));
                break;
        }
        
        return res;
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
    
    public Legion getLegion(int position)
    {
        for (Legion legion : this.legions)
        {
            if (legion.getPosition() == position) return legion;
        }
        return null;
    }
    
    /**
     * @return the laurel
     */
    public Laurel getLaurel() {
        return laurel;
    }

    /**
     * @param laurel the laurel to set
     */
    public void setLaurel(Laurel laurel) {
        this.laurel = laurel;
    }

    /**
     * @return the winner
     */
    public Legion getWinner() {
        return winner;
    }

    /**
     * @return the twinner
     */
    public Team getTwinner() {
        return twinner;
    }
}
