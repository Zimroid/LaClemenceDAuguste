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

package octavio.engine.ia.entity;

import java.util.ArrayList;
import octavio.engine.entity.Cell;
import octavio.engine.entity.pawn.Pawn;

/**
 * Classe utilisée par l'IA pour stocker un mouvement possible correspondant à un groupe de pions
 * @author Zwyk
 */
public class GroupMovement
{
    private final ArrayList<Pawn> group;
    private final Cell cell;
    
    /**
     * 
     * @param group
     * @param cell 
     */
    public GroupMovement(ArrayList<Pawn> group, Cell cell) {
        this.group = group;
        this.cell = cell;
    }

    /**
     * @return the cell
     */
    public Cell getCell()
    {
        return cell;
    }

    /**
     * @return the group
     */
    public ArrayList<Pawn> getGroup()
    {
        return group;
    }
    
    
}
