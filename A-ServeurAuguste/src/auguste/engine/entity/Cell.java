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

import auguste.engine.entity.pawn.Pawn;
import java.awt.Point;

/**
 * Classe représentant une case.
 * @author Zwyk
 */
public class Cell
{
    
    // Variables de classe
    private final Point p;
    
    // Variables métier
    private Pawn pawn;
    private final Board board;
    private final Legion tent;
    
    /**
    * Instanciation d'une légion avec le joueur la gérant.
    * @param board Plateau auquel appartient la case
    * @param p Position de la case
    * @param tent Si la case possède une tente la légion la possédant, sinon null
    */
    public Cell(Board board, Point p, Legion tent)
    {
        this.board = board;
        this.p = p;
        this.tent = tent;
    }

    /**
     * @return the pawn
     */
    public Pawn getPawn()
    {
        return pawn;
    }

    /**
     * @param pawn the pawn to set
     */
    public void setPawn(Pawn pawn)
    {
        this.pawn = pawn;
    }

    /**
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * @return the p
     */
    public Point getP()
    {
        return p;
    }

    /**
     * @return the tent
     */
    public Legion getTent() {
        return tent;
    }
}
