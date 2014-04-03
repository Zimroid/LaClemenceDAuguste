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

import auguste.server.Room;
import auguste.server.User;

/**
 * Exception lancée lorsqu'une commande concerne un salon dans lequel
 * l'utilisateur n'est pas présent.
 * 
 * @author Lzard
 */
public class NotInThisRoomException extends Exception
{
    /**
     * Enregistre le salon et l'utilisateur concernés.
     * @param room Salon concerné
     * @param user Utilisateur concerné
     */
    public NotInThisRoomException(Room room, User user)
    {
        super("Not in this room: " + user.getName() + " not in room " + room.getId());
    }
}
