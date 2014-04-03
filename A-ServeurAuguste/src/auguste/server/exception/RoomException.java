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
 * Exception lancée lorsqu'une commande a provoqué une erreur liée à une salon.
 * 
 * @author Lzard
 */
public class RoomException extends Exception
{
    // Type de l'exception
    private final Type type;
    
    /**
     * Enregistre le type de l'exception.
     * @param type Type d'exception
     */
    public RoomException(Type type)
    {
        super("Room error: " + type.toString().toLowerCase().replace('_', ' '));
        this.type = type;
    }
    
    /**
     * Indique le type de l'exception.
     * @return Type de l'erreur
     */
    public Type getType()
    {
        return this.type;
    }
    
    /**
     * Types de RoomException possibles.
     */
    public enum Type
    {
        INEXISTANT_ROOM,
        NOT_IN_THIS_ROOM,
        UNAVAILABLE_ROOM,
        ABSENT_USER
    }
    
}
