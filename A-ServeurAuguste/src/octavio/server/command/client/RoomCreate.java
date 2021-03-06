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

package octavio.server.command.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import octavio.server.Room;
import octavio.server.Server;
import octavio.server.command.ClientCommand;
import octavio.server.exception.InexistantRoomException;
import octavio.server.exception.NotInThisRoomException;
import octavio.server.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Commande de création d'une partie. Instancie le salon, ajoute l'utilisateur
 * et le définie comme propriétaire, puis confirme le salon à l'utilisateur.
 *
 * @author Lzard
 */
public class RoomCreate extends ClientCommand
{
    @Override
    public boolean checkRoom()
    {
        return false;
    }

    @Override
    public void execute() throws InexistantRoomException, JSONException, NotInThisRoomException
    {
        // Création du salon
        Room newRoom = Server.getInstance().createRoom(
                this.getJSON().getString("game_name")
        );
        if (this.getJSON().getString("game_type").equals("fast"))
        {
            try
            {
                newRoom.setConfiguration(new JSONObject(new JSONTokener(new FileInputStream("modes/fast.json"))));
            }
            catch (FileNotFoundException e)
            {
                Log.debug(e);
            }
        }
        Server.getInstance().joinRoom(this.getUser(), newRoom);
        newRoom.setOwner(this.getUser());
        newRoom.updateConfiguration();
    }

}
