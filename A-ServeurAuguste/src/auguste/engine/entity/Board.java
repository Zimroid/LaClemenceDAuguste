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

import auguste.engine.entity.pawn.Laurel;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Classe représentant un plateau.
 * @author Zwyk
 */
public class Board
{
    
    // Variables de classe
    private final int size;
    
    // Variables métier
    private final ArrayList<Cell> cells;
    
    /**
    * Instanciation d'un plateau avec sa taille et la partie relative.
    * @param size La taille du plateau
    */
    public Board(int size)
    {
        this.size = size;
        this.cells = new ArrayList<>();
        this.fillCells();
    }
        
    /**
    * Transforme un point utilisant la nouvelle méthode (-2,-2 en haut à gauche, -2,0 en haut à droite)
    * @param p Le point à transformer
    * @return Le point transformé
    */
    public Point oldFromNew(Point p)
    {
        return new Point(p.y + (size -1),p.x + (size -1));
    }
        
    /**
    * Transforme un point utilisant l'ancienne méthode (0,0 en haut à gauche, 2,0 en haut à droite)
    * @param p Le point à transformer
    * @return Le point transformé
    */
    public Point newFromOld(Point p)
    {
        return new Point(p.y - (size -1),p.x - (size -1));
    }
    
    /**
    * Renvoie une position avec X rotations horaires
    * @param pos Le point à transformer
    * @param nbRotations Le nombre de rotations
    * @return Le point transformé
    */
    public Point getRotatedPosition(Point pos, int nbRotations)
    {
        Point res = getRotatedPositionOld(oldFromNew(pos), nbRotations);
        
        return newFromOld(res);
    }
    
    /**
    * Renvoie une position avec X rotations horaires de coins (Point utilisant ancienne méthode)
    * @param pos Le point à transformer
    * @param nbRotations Le nombre de rotations
    * @return Le point transformé
    */
    public Point getRotatedPositionOld(Point pos, int nbRotations)
    {
        Point res;
        if (nbRotations == 0)
        {
                res = pos;
        }
        else
        {
                int x, y;

                if(pos.x < size - 1 && pos.y <= size - 1)
                {
                        int destLine =  2 * (size - 1) - Math.abs(size - pos.x - 1);
                        x = destLine - pos.y;
                        y = pos.x;
                }
                else if (pos.x >= size - 1 && pos.y < size - 1)
                {
                        int startLine = 2 * (size - 1) - Math.abs(size - pos.y - 1);
                        int destLine =  2 * (size - 1) - Math.abs(size - pos.x - 1);
                        x = destLine - (startLine - pos.x);
                        y = pos.x;
                }
                else if (pos.x > size - 1 && pos.y >= size - 1)
                {
                        int startLine = 2 * (size - 1) - Math.abs(size - pos.y - 1);
                        x = 2 * (size - 1) - pos.y;
                        y = 2 * (size - 1) - (startLine - pos.x);
                }
                else if (pos.x + pos.y - size + 1 >= size && pos.y > size - 1)
                {
                        x = 2 * (size - 1) - pos.y;
                        y = size - 1 + pos.x - x;
                }
                else
                {
                        x = pos.x;
                        y = (size - 1 + pos.x) - (2 * (size - 1) - pos.y);
                }
                res = getRotatedPositionOld(new Point(x, y), nbRotations - 1);
        }
        return res;
    }

    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @return the cells
     */
    public ArrayList<Cell> getCells()
    {
        return cells;
    }
    
    /**
     * @param p pos
     * @return the cell
     */
    public Cell getCell(Point p)
    {
        Cell res = null;
        for(Cell c : cells)
        {
            if(c.getP().x == p.x && c.getP().y == p.y)
            {
                res = c;
                break;
            }
        }
        return res;
    }
    
    /**
     * Crée les cases du plateau
     */
    private void fillCells() {
        for(int x = -(size - 1); x < size; x++ ) {
            for(int y = -(size - 1); y < (size - Math.abs(x)); y++) {
                this.cells.add(new Cell(this,new Point(x,y)));
            }
        }
    }
}
