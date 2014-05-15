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

import octavio.server.command.ClientCommand;
import octavio.server.Server;
import octavio.server.command.server.ListUsers;
import org.json.JSONException;

/**
 * Commande de demande des utilisateurs de tous les utilisateurs authentifiés.
 * 
 * @author Lzard
 */
public class QueryUsers extends ClientCommand
{
    @Override
    public boolean checkRoom()
    {
        return false;
    }
    
    @Override
    public void execute() throws JSONException
    {
        if (this.getJSON().has("on_update") && this.getJSON().getString("on_update").equals("true")) Server.getInstance().getRoomsWatchers().add(this.getUser());
        else this.send((new ListUsers()).toString());
    }
    
}
