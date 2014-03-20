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

package auguste.server.command.client;

import auguste.server.command.server.LogConfirm;
import auguste.server.command.server.MessageError;
import auguste.server.User;
import auguste.server.manager.UserManager;
import auguste.server.util.Db;
import java.sql.Connection;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande d'identification d'un joueur.
 * @author Lzard
 */
public class LogIn extends ClientCommand
{    
    @Override
    public void execute() throws SQLException, JSONException
    {
        // Connexion à la base de données et récupération du nouveau joueur
        User userToLog;
        try (Connection connection = Db.open())
        {
            UserManager manager = new UserManager(connection);
            userToLog = manager.getUser(
                    this.getJSON().getString("name"),
                    this.getJSON().getString("password")
            );
            Db.close(connection);
        }
        
        // Si un joueur a été trouvé, mise à jour du joueur connecté
        if (userToLog != null)
        {
            this.getUser().setId(userToLog.getId());
            this.getUser().setName(userToLog.getName());
            this.getSocket().send((new LogConfirm(this.getUser())).toString());
        }
        else this.getSocket().send((new MessageError("log_error")).toString());
    }
    
}
