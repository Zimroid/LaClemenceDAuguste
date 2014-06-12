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

package octavio.engine.ia;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import octavio.engine.entity.Bot;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.Legion;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.engine.entity.pawn.Armor;
import octavio.engine.entity.pawn.Laurel;
import octavio.engine.entity.pawn.Pawn;
import octavio.engine.entity.pawn.Soldier;
import octavio.engine.ia.entity.GroupMovement;
import octavio.engine.util.RandomCollection;

/**
 *
 * @author Zwyk
 */
public class IA {
    
    // Variables de classe
    private final Game game;
    private final Random rand = new Random();
    
    // Variables utiles
    private ArrayList<ArrayList<Pawn>> groups;
    
    // Weights
    public double pawnWeight(Pawn p) {
        return 1.0;
    }
    public double pawnArrivalWeight(Cell c) {
        return Math.exp(game.getBoard().getSize()/(double)distance(c,game.getLaurel().getCell()));
    }
    public double laurelWeight(Legion l) {
        return l.getLivingPawns().size()*1000;
    }
    public double laurelArrivalWeight(Legion l, Cell c) {
        Laurel lau = game.getLaurel();
        Cell tent = legionsTent(l);
        double cellToTent = (double)distance(c,tent);
        double laurelToTent = (double)distance(lau.getCell(),tent);
        return Math.exp(laurelToTent-cellToTent*2);
    }
    
    public final double cartesianWeight(double pawnWeight, double arrivalWeight) {
        return pawnWeight*arrivalWeight;
    }
    
    public IA(Game g)
    {
        this.game = g;
    }
    
    public ArrayList<Action> activateBot(Bot b)
    {
        ArrayList<Action> res = new ArrayList<>();
        Movement m;
        groups = getGroups();
        
        for(Legion l : b.getPlayer().getLegions())
        {
            if(l.getLivingPawns().size() > 0)
            {
                switch(b.getStrategy()) {
                    case random:
                        m = randomMove(b,l);
                        break;
                    case pseudoRandom:
                        m = pseudoRandomMove(b,l);
                        break;
                    case distributed:
                        m = distributedRandomMove(b,l);
                        break;
                    default:
                        m = pseudoRandomMove(b,l);
                        break;
                }
                res.add(new Action(l,m));
            }
        }
        
        botTurnEnd(b);
        return res;
    }
    
    private void botTurnEnd(Bot b)
    {
        b.setPlayedLaurel(false);
    }
    
    private Movement randomMove(Bot b, Legion l)
    {
        ArrayList<Pawn> pawns = l.getLivingPawns();
        if(pawns.size() > 0) {
            
            Pawn pawn;
            boolean armorOk;
            ArrayList<Cell> cells;
            
            pawn = pawns.get(rand.nextInt(pawns.size()));
            armorOk = (pawn instanceof Soldier)?!((Soldier)pawn).isArmored():false;
            cells = game.nearbyEmptyCells(game.friendlyGroup(pawn),armorOk);
            
            if(cells.isEmpty()) return new Movement(pawn,pawn.getCell());
            else return new Movement(pawn,cells.get(rand.nextInt(cells.size())));
        }
        else return null;
    }
    
    private Movement pseudoRandomMove(Bot b, Legion l)
    {
        ArrayList<Pawn> pawns = l.getLivingPawns();
        if(pawns.size() > 0) {
            
            Pawn pawn;
            boolean armorOk;
            ArrayList<Cell> cells;
            
            if(!b.getPlayedLaurel() && game.canMoveLaurel(l,game.getLaurel()) && !game.nearbyEmptyCells(game.getLaurel(),false).isEmpty() && rand.nextInt(100)<75) {
                pawn = game.getLaurel();
                armorOk = false;
                cells = game.nearbyEmptyCells(pawn,armorOk);
                b.setPlayedLaurel(true);
            }
            else {
                pawn = pawns.get(rand.nextInt(pawns.size()));
                armorOk = (pawn instanceof Soldier)?!((Soldier)pawn).isArmored():false;
                cells = game.nearbyEmptyCells(game.friendlyGroup(pawn),armorOk);
            }
            
            if(cells.isEmpty()) return new Movement(pawn,pawn.getCell());
            else return new Movement(pawn,cells.get(rand.nextInt(cells.size())));
        }
        else return null;
    }
    
    private Movement distributedRandomMove(Bot b, Legion l) {
        HashMap<Pawn,Double> pawnPoss = getPawnPoss(l);
        HashMap<GroupMovement,Double> arrivalPoss = getArrivalPoss(l);
        
        RandomCollection movementPoss = getMovePoss(pawnPoss,arrivalPoss);
        
        Movement m = null;
        boolean ok = false;
        if(movementPoss.getTotal()>0) {
            while(!ok) {
                m = (Movement) movementPoss.next();
                ok = m.getPawn() != game.getLaurel() || !b.getPlayedLaurel();
            }
        }
        return m;
    }
    
    private RandomCollection getMovePoss(HashMap<Pawn,Double> pawnPoss, HashMap<GroupMovement,Double> arrivalPoss) {
        RandomCollection res = new RandomCollection();
        
        pawnPoss.keySet().stream().forEach((p) ->
        {
            arrivalPoss.keySet().stream().filter((gm) -> (gm.getGroup().contains(p))).forEach((gm) ->
            {
                if(p instanceof Laurel || (p instanceof Soldier && !(((Soldier)p).isArmored() && (gm.getCell().getPawn() != null && gm.getCell().getPawn() instanceof Armor)))) {
                    res.add(cartesianWeight(pawnPoss.get(p),arrivalPoss.get(gm)), new Movement(p,gm.getCell()));
                }
            });
        });
        
        return res;
    }
    
    private HashMap<GroupMovement,Double> getArrivalPoss(Legion l) {
        HashMap<GroupMovement,Double> res = new HashMap<>();
        HashMap<ArrayList<Pawn>, ArrayList<Cell>> poss = movementPossibilities(groups,l);
        
        poss.keySet().stream().forEach((group) ->
        {
            poss.get(group).stream().forEach((c) ->
            {
                res.put(new GroupMovement(group,c), pawnArrivalWeight(c));
            });
        });
        
        if(game.canMoveLaurel(l, game.getLaurel())) {
            ArrayList<Pawn> al = new ArrayList<>();
            al.add(game.getLaurel());
            ArrayList<Cell> ac = game.nearbyEmptyCells(al, false);
            ac.stream().forEach((c) ->
            {
                res.put(new GroupMovement(al,c), laurelArrivalWeight(l,c));
            });
        }
        
        return res;
    }
    
    private HashMap<Pawn,Double> getPawnPoss(Legion l) {
        HashMap<Pawn,Double> res = new HashMap<>();
        
        l.getLivingPawns().stream().forEach((p) ->
        {
            res.put(p, pawnWeight(p));
        });
        
        if(game.canMoveLaurel(l, game.getLaurel())) {
            res.put(game.getLaurel(), laurelWeight(l));
        }
        
        return res;
    }
    
    private HashMap<ArrayList<Pawn>, ArrayList<Cell>> movementPossibilities(ArrayList<ArrayList<Pawn>> groups, Legion l) {
        HashMap<ArrayList<Pawn>, ArrayList<Cell>> res = new  HashMap<>();
        
        /*groups.stream().filter((ap) -> (ap.get(0).getLegion() == l)).forEach((ap) ->*/
        
        for(ArrayList<Pawn> ap : groups)
        {
            if(ap.get(0).getLegion() == l)
            {
                res.put(ap, game.nearbyEmptyCells(ap, true));
            }
        }
        
        return res;
    }
    
    private ArrayList<ArrayList<Pawn>> getGroups() {
        ArrayList<ArrayList<Pawn>> res = new ArrayList<>();
        ArrayList<Pawn> group;
        
        for(Legion l : game.getLegions()) {
            for(Pawn p : l.getLivingPawns()) {
                if(!pawnInGroups(res,p)) {
                    group = game.friendlyGroup(p);
                    res.add(group);
                }
            }
        }
        return res;
    }
    
    private Cell legionsTent(Legion l) {
        return game.getBoard().getCell(game.getBoard().getRotatedPosition(new Point(-(game.getBoard().getSize()-1),-(game.getBoard().getSize()-1)), l.getPosition()));
    }
    
    private boolean pawnInGroups(ArrayList<ArrayList<Pawn>> groups, Pawn p) {
        boolean res = false;
        
        for(ArrayList<Pawn> ap : groups) {
            for(Pawn pawn : ap) {
                if(pawn == p) {
                    res = true;
                    break;
                }
            }
            if(res) break;
        }
        
        return res;
    }
    
    public int nbUneplayedLegionsAroundLaurel(Legion leg) {
        Laurel l = game.getLaurel();
        int i = 0;
        for(Soldier s : game.nearbySoldiers(l)) {
            if(!leg.getPlayer().getLegions().contains(s.getLegion())) i++;
        }
        return i;
    }
    
    public ArrayList<Cell> shortestPath(Cell c1, Cell c2) {
        ArrayList<Cell> res = new ArrayList<>();
        Cell temp = c1;
        
        while(temp != c2)
        {
            temp = game.getCell(temp,game.getOrientation(temp,c2));
            if(temp == null) System.out.println(c1.getP() + " ; " + c2.getP());
            res.add(temp);
        }
        
        return res;
    }
    
    public int distance(Cell c1, Cell c2) {
        /*int x1 = c1.getP().x;
        int y1 = c1.getP().y;
        int x2 = c2.getP().x;
        int y2 = c2.getP().y;
        
        return Math.abs(x2 - x1) + Math.abs(y2 - y1); // FALSE*/
        return shortestPath(c1,c2).size();
    }
}
