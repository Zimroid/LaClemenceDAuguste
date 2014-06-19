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

package octavio.server.commands;

import octavio.server.db.entities.Room;
import octavio.server.util.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe abstraite des commandes serveurs à envoyer.
 *
 * @author Lzard
 */
public abstract class ServerCommand
{
    // JSON de la commande
    private final JSONObject json = new JSONObject();

    /**
     * Insère dans le JSON le nom de la commande.
     *
     * @param name Nom de la commande
     */
    public ServerCommand(String name)
    {
        try
        {
            this.getJSON().put("command", name);
        }
        catch (JSONException e)
        {
            Logger.printError(e);
        }
    }

    /**
     * Insère dans le JSON le nom de la commande et l'identifiant de la salon.
     *
     * @param name Nom de la commande
     * @param room Salon à destination
     */
    public ServerCommand(String name, Room room)
    {
        try
        {
            this.getJSON().put("command", name);
            this.getJSON().put("room_id", room.getId());
        }
        catch (JSONException e)
        {
            Logger.printError(e);
        }
    }

    /**
     * Retourne le JSON de la commande.
     *
     * @return JSON de la commande
     */
    public final JSONObject getJSON()
    {
        return this.json;
    }

    /**
     * Retourne le JSON en format texte.
     *
     * @return JSON texte
     */
    public String getMessage()
    {
        return this.json.toString();
    }

}
