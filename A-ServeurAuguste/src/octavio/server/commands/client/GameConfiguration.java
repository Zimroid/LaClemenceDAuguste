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

package octavio.server.commands.client;

import java.sql.SQLException;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.GameConfirm;
import octavio.server.db.entities.Room;
import octavio.server.exception.CommandException;
import org.json.JSONException;

/**
 * Commande de configuration d'une partie. Vérifie si l'utilisateur est le
 * propriétaire du salon, puis modifie la configuration et envoi une
 * confirmation à tous les utilisateurs.
 *
 * @author Lzard
 */
public class GameConfiguration extends ClientCommand
{
    @Override
    public void execute() throws JSONException, CommandException, SQLException
    {
        // Vérification du propriétaire
        if (Room.getRoom(this.getJSON().getInt("room_id")).getOwner() == this.getUser())
        {
            // Modification de la configuration et confirmation
            Room.getRoom(this.getJSON().getInt("room_id")).setConfiguration(this.getJSON());
            Room.getRoom(this.getJSON().getInt("room_id")).broadcast(new GameConfirm(Room.getRoom(this.getJSON().getInt("room_id"))));
        }
        else
        {
            throw new CommandException(this.getUser().getName() + " is not owner of this room", "not_owner_of_this_room");
        }
    }

}
