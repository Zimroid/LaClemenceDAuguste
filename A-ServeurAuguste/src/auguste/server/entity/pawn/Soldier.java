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

/**
 * Classe représentant un soldat.
 * @author Zwyk
 */
public class Soldier extends Pawn
{

    /**
    * Instanciation d'un soldat avec la case sur laquelle il est situé.
    * @param cell Case sur laquelle est situé le soldat
    */
    public Soldier(Cell cell)
    {
        super(cell);
    }
}
