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

import java.awt.Point;
import java.util.ArrayList;

/**
 * Classe représentant un plateau.
 * @author Zwyk
 */
public class Board
{
    
    // Variables de classe
    private int size;
    
    // Variables métier
    private final Game game;
    private final ArrayList<Cell> cells;
    
    /**
    * Instanciation d'un plateau avec sa taille et la partie relative.
    * @param size La taille du plateau
    * @param game La partie relative au plateau
    */
    public Board(int size, Game game)
    {
        this.size = size;
        this.game = game;
        this.cells = new ArrayList<>();
        this.fillCells();
    }

    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @return the game
     */
    public Game getGame()
    {
        return game;
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
            if(c.getP() == p)
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
        for(int y = -(size - 1); y < getSize(); y++ ) {
            for(int x = -(size - 1); x < (getSize() - Math.abs(y)); x++) {
                this.cells.add(new Cell(this,new Point(x,y)));
            }
        }
    }
    
    /**
     * Transforme un point utilisant l'ancienne méthode (0,0 en haut à gauche, 2,0 en haut à droite)
     * @param p Le point à transformer
     * @return Le point transformé
     */
    public Point oldFromNew(Point p)
    {
        return new Point(p.y + (getSize() -1),p.x + (getSize() -1));
    }
    
    /**
     * Transforme un point utilisant la nouvelle méthode (-2,-2 en haut à gauche, -2,0 en haut à droite)
     * @param p Le point à transformer
     * @return Le point transformé
     */
    public Point newFromOld(Point p)
    {
        return new Point(p.y - (getSize() -1),p.x - (getSize() -1));
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

                if(pos.x < getSize() - 1 && pos.y <= getSize() - 1)
                {
                        int destLine =  2 * (getSize() - 1) - Math.abs(getSize() - pos.x - 1);
                        x = destLine - pos.y;
                        y = pos.x;
                }
                else if (pos.x >= getSize() - 1 && pos.y < getSize() - 1)
                {
                        int startLine = 2 * (getSize() - 1) - Math.abs(getSize() - pos.y - 1);
                        int destLine =  2 * (getSize() - 1) - Math.abs(getSize() - pos.x - 1);
                        x = destLine - (startLine - pos.x);
                        y = pos.x;
                }
                else if (pos.x > getSize() - 1 && pos.y >= getSize() - 1)
                {
                        int startLine = 2 * (getSize() - 1) - Math.abs(getSize() - pos.y - 1);
                        x = 2 * (getSize() - 1) - pos.y;
                        y = 2 * (getSize() - 1) - (startLine - pos.x);
                }
                else if (pos.x + pos.y - getSize() + 1 >= getSize() && pos.y > getSize() - 1)
                {
                        x = 2 * (getSize() - 1) - pos.y;
                        y = getSize() - 1 + pos.x - x;
                }
                else
                {
                        x = pos.x;
                        y = (getSize() - 1 + pos.x) - (2 * (getSize() - 1) - pos.y);
                }
                res = getRotatedPositionOld(new Point(x, y), nbRotations - 1);
        }
        return res;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }
}
