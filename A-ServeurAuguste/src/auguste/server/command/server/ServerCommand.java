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
import org.json.JSONObject;

/**
 * Classe abstraite des commandes serveurs à envoyer.
 * @author Lzard
 */
public abstract class ServerCommand
{
    // JSON de la commande
    private final JSONObject json = new JSONObject();
    
    /**
     * Insère dans le JSON le nom de la commande.
     * @param name Nom de la commande
     * @throws org.json.JSONException Erreur de JSON
     */
    public ServerCommand(String name) throws JSONException
    {
        this.getJSON().put("command", name);
    }
    
    /**
     * Retourne le JSON de la commande.
     * @return JSON de la commande
     */
    public JSONObject getJSON()
    {
        return this.json;
    }
    
    @Override
    public String toString()
    {
        return this.json.toString();
    }
   
}
