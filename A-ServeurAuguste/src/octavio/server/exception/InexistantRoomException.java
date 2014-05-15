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

package octavio.server.exception;

/**
 * Exception lancée lorsqu'une commande concerne un salon qui n'existe pas.
 * 
 * @author Lzard
 */
public class InexistantRoomException extends Exception
{
    /**
     * Enregistre l'identifiant du salon inexistant.
     * @param id Identifiant du salon
     */
    public InexistantRoomException(int id)
    {
        super("Inexistant room: " + id);
    }
    
}
