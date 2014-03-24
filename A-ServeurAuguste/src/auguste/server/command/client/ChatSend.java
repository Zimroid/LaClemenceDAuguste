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

import auguste.server.Server;
import auguste.server.command.server.ChatMessage;
import auguste.server.exception.AuthentificationException;
import org.json.JSONException;

/**
 * Commande d'envoi d'un message.
 * @author Lzard
 */
public class ChatSend extends ClientCommand
{
    @Override
    public void execute() throws AuthentificationException, JSONException
    {
        // Vérification de l'authentification
        this.checkAuth();

        // Choix de la salle
        if (this.getRoom() != null)
        {
            // Envoi du message à la salle
            this.getRoom().broadcast(
                    (new ChatMessage(
                            this.getUser(),
                            this.getRoom(),
                            this.getJSON().getString("message")
                    )).toString()
            );
        }
        else
        {
            // Envoi du message à tous les utilisateurs
            Server.getInstance().broadcast(
                    (new ChatMessage(
                            this.getUser(),
                            this.getJSON().getString("message")
                    )).toString()
            );
        }
    }
    
}
