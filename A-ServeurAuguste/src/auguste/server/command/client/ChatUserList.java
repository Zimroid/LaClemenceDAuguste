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

import auguste.server.command.server.ChatUsers;
import auguste.server.exception.AuthentificationException;
import auguste.server.exception.RuleException;
import java.sql.SQLException;
import org.json.JSONException;

/**
 * Commande de demande des utilisateurs d'une salle ou de tous les utilisateurs
 * authentifiés.
 * @author Lzard
 */
public class ChatUserList extends ClientCommand
{
    @Override
    public void execute() throws SQLException, JSONException, RuleException, AuthentificationException
    {
        // Vérification de l'authentification
        this.checkAuth();
        
        // Envoi de la liste
        if (this.getRoom() != null) this.send((new ChatUsers(this.getRoom())).toString());
        else                        this.send((new ChatUsers()).toString());
    }
    
}
