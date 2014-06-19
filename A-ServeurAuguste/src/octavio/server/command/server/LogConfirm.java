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

package octavio.server.command.server;

import octavio.server.User;
import octavio.server.command.ServerCommand;

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
    public LogConfirm(User user)
    {
        // Constructeur de la classe mère
        super("log_confirm");

        // Remplissage du JSON
        user.addUserInformations(this.getJSON());
        user.addUserParameters(this.getJSON());
    }

}
