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
import octavio.server.command.server.ChatMessage;
import java.util.Date;
import org.json.JSONException;

/**
 * Commande d'envoi d'un message. Envoi du message au salon si précisé ou au
 * canal général sinon.
 * 
 * @author Lzard
 */
public class ChatSend extends ClientCommand
{
    @Override
    public boolean checkRoom()
    {
        // Vérification du salon si il est précisé
        return this.getJSON().has("room_id");
    }
    
    @Override
    public void execute() throws JSONException
    {
        // Choix du salon
        if (this.getRoom() != null)
        {
            // Envoi du message au salon
            this.getRoom().broadcast(
                    (new ChatMessage(
                            this.getUser(),
                            this.getRoom(),
                            new Date(),
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
                            new Date(),
                            this.getJSON().getString("message")
                    )).toString()
            );
        }
    }
    
}
