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
    // Types de l'exceptions
    public static final int INEXISTANT_ROOM  = 0;
    public static final int NOT_IN_THIS_ROOM = 1;
    public static final int UNAVAILABLE_ROOM = 2;
    public static final int ABSENT_USER      = 3;
    
    private final RoomExceptionType type; // Type de l'exception
    
    /**
     * Enregistre le type de l'exception.
     * @param type Type d'exception
     */
    public RoomException(RoomExceptionType type)
    {
        super(type.toString().toLowerCase().replace('_', ' '));
        this.type = type;
    }
    
    /**
     * Indique le type de l'erreur.
     * @return Type de l'erreur
     */
    public RoomExceptionType getType()
    {
        return this.type;
    }
    
    /**
     * Types de RoomException possibles.
     */
    public enum RoomExceptionType
    {
        INEXISTANT_ROOM,
        NOT_IN_THIS_ROOM,
        UNAVAILABLE_ROOM,
        ABSENT_USER
    }
    
}
