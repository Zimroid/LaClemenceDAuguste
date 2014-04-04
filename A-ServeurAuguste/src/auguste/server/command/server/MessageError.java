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
import auguste.server.util.Log;
import org.json.JSONException;

/**
 * Commande de signalisation qu'une erreur est survenue.
 * 
 * @author Lzard
 */
public class MessageError extends ServerCommand
{
    /**
     * Remplit le JSON avec le type d'erreur.
     * @param type Type de l'erreur
     */
    public MessageError(String type)
    {
        // Constructeur de la classe mère
        super("message_error");
        
        // Création du JSON
        try
        {
            this.getJSON().put("type", type);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
}
