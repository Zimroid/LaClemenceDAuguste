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

package octavio.server.commands.server;

import octavio.server.commands.ServerCommand;
import octavio.server.db.entities.User;
import org.json.JSONException;

/**
 * Commande de confirmation d'authentification. Envoi les données et paramètres
 * de l'utilisateur.
 *
 * @author Lzard
 */
public class LogConfirm extends ServerCommand
{
    /**
     * Remplit le JSON avec les données de l'utilisateur.
     *
     * @param user Utilisateur authentifié
     */
    public LogConfirm(User user) throws JSONException
    {
        // Constructeur de la classe mère
        super("log_confirm");

        // Remplissage du JSON
        this.getJSON().put("user_id", user.getId());
        this.getJSON().put("user_name", user.getName());
    }

}
