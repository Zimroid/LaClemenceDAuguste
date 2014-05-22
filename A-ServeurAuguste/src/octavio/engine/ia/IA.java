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

import octavio.engine.entity.Bot;
import octavio.engine.entity.Cell;
import octavio.engine.entity.Game;
import octavio.engine.entity.Legion;
import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.Movement;
import octavio.engine.entity.pawn.Pawn;
import octavio.engine.entity.pawn.Soldier;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Zwyk
 */
public class IA {
    
    private Game game;
    private Random rand = new Random();
    
    public IA(Game g)
    {
        this.game = g;
    }
    
    public ArrayList<Action> activateBot(Bot b)
    {
        ArrayList<Action> res = new ArrayList<>();
        Movement m;
        for(Legion l : b.getPlayer().getLegions())
        {
            switch(b.getStrategy()) {
                case random:
                    m = randomMove(b,l);
                    break;
                case pseudoRandom:
                    m = pseudoRandomMove(b,l);
                    break;
                default:
                    m = pseudoRandomMove(b,l);
                    break;
            }
            res.add(new Action(l,m));
        }
        endBotTurn(b);
        return res;
    }
    
    private void endBotTurn(Bot b)
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
    
    private int cellsDistance(Cell c1, Cell c2) {
        int x1 = c1.getP().x;
        int y1 = c1.getP().y;
        int x2 = c2.getP().x;
        int y2 = c2.getP().y;
        
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
