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

import auguste.server.command.server.MessageConfirm;
import auguste.engine.entity.Player;
import auguste.server.Server;
import auguste.server.User;
import auguste.server.command.server.MessageError;
import org.json.JSONException;

/**
 * Commande de déconnexion d'un joueur.
 * @author Lzard
 */
public class LogOut extends ClientCommand
{
    @Override
    public void execute() throws JSONException
    {
        // Commande pouvant être exécuté que par des utilisateurs identifiés
        if (this.getUser().isLogged())
        {
            // Désidentification de l'utilisateur
            Server.getInstance().logOut(this.getUser());
            
            // Remise des informations du joueur à celles par défaut
            this.getUser().setId(User.DEFAULT_ID);
            this.getUser().setName(User.DEFAULT_NAME);
            this.getUser().setPassword(User.DEFAULT_PASSWORD);

            // Signalisation
            this.getSocket().send((new MessageConfirm("log_out")).toString());
        }
        else this.getUser().send((new MessageError("not_logged")).toString());
    }
    
}
