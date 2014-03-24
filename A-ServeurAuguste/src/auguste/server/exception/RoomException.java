/*
 * Copyright 2014 Conseil7.
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

package auguste.server.exception;

/**
 * Exception lancée lorsqu'une commande concerne une salle dont l'utilisateur ne
 * fait pas parti ou n'existe pas.
 * @author Lzard
 */
public class RoomException extends Exception
{
    private final int     id;         // ID de la salle
    private final boolean inexistant; // Salle inexistante ?
    
    /**
     * Enregistre la salle demandée et inexistante.
     * @param id         ID de la salle
     * @param inexistant Salle inexistante ?
     */
    public RoomException(int id, boolean inexistant)
    {
        this.id         = id;
        this.inexistant = inexistant;
    }
    
    /**
     * Indique si la salle demandée existe ou non.
     * @return Booléen indiquant la non-existance de la salle
     */
    public boolean inexistant()
    {
        return this.inexistant;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(this.id) + (this.inexistant ? " (inexistant)" : "");
    }

    public boolean exist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
