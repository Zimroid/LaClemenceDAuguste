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

package auguste.server.entity.action;

import auguste.server.entity.Cell;
import auguste.server.entity.pawn.Pawn;

/**
 * Classe représentant un mouvement.
 * @author Zwyk
 */
public class Movement {
    
    // Variable métier
    private final Pawn pawn;
    private final Cell cell;
    
    /**
    * Instanciation d'une action avec le joueur, le mouvement et la carte.
    * @param pawn Le pion réalisant le mouvement
    * @param cell La case d'arrivée
    */
    public Movement(Pawn pawn, Cell cell) {
        this.pawn = pawn;
        this.cell = cell;
    }
    
    /**
     * @return the cell
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * @return the pawn
     */
    public Pawn getPawn() {
        return pawn;
    }
}
