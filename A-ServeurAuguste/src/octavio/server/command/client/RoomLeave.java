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

import octavio.server.Server;
import octavio.server.command.ClientCommand;
import org.json.JSONException;

/**
 * Commande pour quitter une partie. Retire l'utilisateur du salon puis
 * envoi une confirmation aux utilisateurs du salon et à l'utilisateur
 * ayant envoyé la commande.
 *
 * @author Lzard
 */
public class RoomLeave extends ClientCommand
{
    @Override
    public void execute() throws JSONException
    {
        synchronized (this.getRoom())
        {
            // Retrait de l'utilisateur du salon
            Server.getInstance().leaveRoom(this.getUser(), this.getRoom());
            this.confirm("room_left");
        }
    }

}
