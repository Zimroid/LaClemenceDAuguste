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

package auguste.server.entity.pawn;

import auguste.server.entity.Cell;
import auguste.server.entity.Legion;

/**
 * Classe représentant un pion.
 * @author Zwyk
 */
public class Pawn
{
    
    // Variables métier
    private Legion legion;
    private Cell cell;
    
    /**
    * Instanciation d'un pion avec la case sur laquelle il est situé.
    * @param cell Case sur laquelle est situé le pion
    */
    public Pawn(Cell cell)
    {
        this.cell = cell;
    }

    /**
     * @return the legion
     */
    public Legion getLegion()
    {
        return legion;
    }

    /**
     * @param legion the legion to set
     */
    public void setLegion(Legion legion)
    {
        this.legion = legion;
    }

    /**
     * @return the cell
     */
    public Cell getCell()
    {
        return cell;
    }

    /**
     * @param cell the cell to set
     */
    public void setCell(Cell cell)
    {
        this.cell = cell;
    }
}