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

import auguste.server.Server;
import auguste.server.command.server.GameTurn;
import auguste.server.User;
import auguste.server.exception.RuleException;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande pour commencer une partie.
 * @author Lzard
 */
public class GameStart extends ClientCommand
{
    @Override
    public void execute() throws SQLException, JSONException, RuleException
    {
        if (this.getUser().isIdentified() && this.getUser().isInGame())
        {
            for (User user : Server.getInstance().getUsers().values())
            {
                if (user.getGame() == this.getUser().getGame()) this.getSocket().send((new GameTurn()).toString());
            }
        }
    }
    
}
