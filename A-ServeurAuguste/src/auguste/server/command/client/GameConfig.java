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

import org.json.JSONException;

/**
 * Commande de configuration d'une partie. Vérifie si l'utilisateur est le
 * propriétaire de la salon, puis modifie la configuration et envoi une
 * confirmation à tous les utilisateurs.
 * 
 * @author Lzard
 */
public class GameConfig extends ClientCommand
{
    @Override
    public void execute() throws JSONException
    {
        // Vérification du propriétaire
        if (this.getRoom().isOwner(this.getUser()))
        {
            // Modification de la configuration et confirmation
            this.getRoom().readConfiguration(this.getJSON());
            this.getRoom().confirm();
        }
        else this.error("not_owner_of_this_room");
    }
    
}
