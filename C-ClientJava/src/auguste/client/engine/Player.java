/*
 * Copyright 2014 Evinrude.
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

package auguste.client.engine;

import auguste.client.entity.Game;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Un joueur dans une partie
 * @author Evinrude
 */
public class Player 
{
    private int id;
    private String name;
    private Game game;
    private int pawnShape;
    private int color;
    private final List<Pawn> pawns;
    private Map<Integer,Pawn> tabPawn;
    
    public static final int CIRCLE      = 1;
    public static final int TRIANGLE    = 2;
    public static final int SQUARE      = 3;
    public static final int PENTAGONE   = 4;
    public static final int HEXAGONE    = 5;
    
    public Player(String name)
    {
        this.name = name;
        this.pawns = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * @return the pawnShape
     */
    public int getPawnShape() {
        return pawnShape;
    }

    /**
     * @param pawnShape the pawnShape to set
     */
    public void setPawnShape(int pawnShape) {
        this.pawnShape = pawnShape;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }
    
    public List<Pawn> getPawns()
    {
        return this.pawns;
    }
    
    public void addPawn(Pawn pawn)
    {
        this.pawns.add(pawn);
        this.tabPawn.put(pawn.getId(), pawn);
    }
    
    public void generatePawns()
    {
        
    }
}