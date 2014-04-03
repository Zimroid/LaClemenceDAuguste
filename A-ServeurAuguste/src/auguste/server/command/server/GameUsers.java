/*
 * Copyright 2014 Lzard.
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

package auguste.server.command.server;

import auguste.server.Room;

/**
 * Commande de signalisation de la liste des utilisateurs d'un salon.
 * 
 * @author Lzard
 */
public class GameUsers extends ServerCommand
{
    /**
     * Remplit le JSON avec les utilisateurs de la partie.
     * @param room Salon de la partie
     */
    public GameUsers(Room room)
    {
        // Constructeur de la classe mère
        super("game_users", room);
        
        // Remplissage du JSON
        room.addUserList(this.getJSON());
    }
    
}
