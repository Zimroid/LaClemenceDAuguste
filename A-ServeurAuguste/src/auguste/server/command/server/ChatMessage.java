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

package auguste.server.command.server;

import auguste.server.command.ServerCommand;
import auguste.server.Room;
import auguste.server.User;
import auguste.server.util.Log;
import java.util.Date;
import org.json.JSONException;

/**
 * Commande de transfert d'un message de chat.
 * 
 * @author Lzard
 */
public class ChatMessage extends ServerCommand
{
    /**
     * Remplit le JSON avec les données du message.
     * @param author  Utilisateur auteur du message
     * @param date    Date de réception du message
     * @param message Contenu du message
     */
    public ChatMessage(User author, Date date, String message)
    {
        // Constructeur de la classe mère
        super("chat_message");
        
        // Ajout des informations de l'auteur
        author.addUserInformations(this.getJSON());
        
        // Remplissage du JSON
        try
        {
            this.getJSON().put("date", date.getTime());
            this.getJSON().put("text", message);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }

    /**
     * Remplit le JSON avec les données du message.
     * @param author  Utilisateur auteur du message
     * @param room    Salon à destination du message
     * @param date    Date de réception du message
     * @param message Contenu du message
     */
    public ChatMessage(User author, Room room, Date date, String message)
    {
        // Constructeur de la classe mère
        super("chat_message", room);
        
        // Ajout des informations de l'auteur
        author.addUserInformations(this.getJSON());
        
        // Remplissage du JSON
        try
        {
            this.getJSON().put("date", date.getTime());
            this.getJSON().put("text", message);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }

}
