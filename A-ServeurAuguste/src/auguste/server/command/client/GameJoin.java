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

package auguste.server.command.client;

import auguste.server.Room;
import auguste.server.Server;
import auguste.server.exception.AuthentificationException;
import auguste.server.exception.RoomException;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande pour rejoindre une partie.
 * @author Lzard
 */
public class GameJoin extends ClientCommand
{
    @Override
    public void execute() throws RoomException, SQLException, JSONException, AuthentificationException
    {
        // Vérification de l'authentification de l'utilisateur
        this.checkAuth();
        
        // Récupération de la salle
        Room room = Server.getInstance().getRoom(this.getJSON().getInt("join_game"));

        // Vérification de la non-présence de l'utilisateur
        if (!room.isInRoom(this.getUser()))
        {
            // Ajout du client à la salle
            room.addUser(this.getSocket(), this.getUser());
            room.confirm();
        }
        else this.error("already_in_this_room");
    }
    
}
