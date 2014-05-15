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

package octavio.engine.entity.pawn;

import octavio.engine.entity.Cell;
import octavio.engine.entity.Legion;

/**
 * Classe représentant un pion.
 * @author Zwyk
 */
public class Pawn
{
    
    // Variables métier
    private Legion legion;
    private Cell cell;
    private boolean deleted = false;
    
    /**
    * Instanciation d'un pion avec la légion.
    * @param legion La légion du pion
    */
    public Pawn(Legion legion)
    {
        this.legion = legion;
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

    /**
     * @return the deleted
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
