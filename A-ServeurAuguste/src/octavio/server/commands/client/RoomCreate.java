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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import octavio.server.Server;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.server.GameConfirm;
import octavio.server.commands.server.RoomUsers;
import octavio.server.db.entities.Room;
import octavio.server.db.entities.Session;
import octavio.server.db.managers.RoomManager;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;
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
    public void execute() throws JSONException, SQLException, CommandException
    {
        try (Connection connection = Configuration.getDbConnection())
        {
            // Création du salon
            Room newRoom = (new RoomManager(connection)).insertRoom(
                    this.getJSON().getString("game_name"),
                    this.getUser()
            );
            connection.commit();
            if (this.getJSON().getString("game_type").equals("fast"))
            {
                try
                {
                    newRoom.setConfiguration(new JSONObject(new JSONTokener(new FileInputStream("modes/fast.json"))));
                }
                catch (FileNotFoundException e)
                {
                    Logger.printError(e);
                }
            }
            Session.getSession(this.getUser()).joinRoom(newRoom);
            newRoom.setOwner(this.getUser());
            newRoom.broadcast(new GameConfirm(newRoom));
            newRoom.broadcast(new RoomUsers(newRoom));
            connection.commit();
        }
    }

}
