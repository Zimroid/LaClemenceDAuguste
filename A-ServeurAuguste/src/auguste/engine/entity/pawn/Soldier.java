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

package auguste.engine.entity.pawn;

import auguste.engine.entity.Legion;

/**
 * Classe représentant un soldat.
 * @author Zwyk
 */
public class Soldier extends Pawn
{
    private boolean armored = false;
    
    /**
    * Instanciation d'un soldat.
    */
    public Soldier()
    {
        super(null);
    }
    
    /**
    * Instanciation d'un soldat avec légion.
     * @param l Legion du pion
    */
    public Soldier(Legion l)
    {
        super(l);
    }
    
    @Override
    public String toString()
    {
        return "Soldat - " + this.getLegion();
    }

    /**
     * @return the armored
     */
    public boolean isArmored() {
        return armored;
    }

    /**
     * @param armored the armored to set
     */
    public void setArmored(boolean armored) {
        this.armored = armored;
    }
}
