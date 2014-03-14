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

import org.json.JSONException;

/**
 * Commande de confirmation d'une action quelconque.
 * @author Lzard
 */
public class MessageConfirm extends ServerCommand
{
    /**
     * Remplit le JSON avec les paramètres fournis.
     * @param type Type de confirmation
     * @throws JSONException Erreur de JSON
     */
    public MessageConfirm(String type) throws JSONException
    {
        // Constructeur de la classe mère
        super("message_confirm");
        
        // Création du JSON
        this.getJSON().put("type", type);
    }
    
}
