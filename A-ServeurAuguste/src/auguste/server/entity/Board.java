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

package auguste.server.entity;

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
     * Crée les cases du plateau
     */
    private void fillCells() {
        // TODO
    }
}
