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
import octavio.server.util.Logger;
import org.json.JSONException;

/**
 * Commande de signalisation qu'une erreur est survenue.
 *
 * @author Lzard
 */
public class ErrorCommand extends ServerCommand
{
    /**
     * Remplit le JSON avec le message d'erreur.
     *
     * @param message Type de l'erreur
     */
    public ErrorCommand(String message)
    {
        // Constructeur de la classe mère
        super("error");

        // Création du JSON
        try
        {
            this.getJSON().put("message", message);
        }
        catch (JSONException e)
        {
            Logger.printError(e);
        }
    }

    public ErrorCommand(Exception message)
    {
        // Constructeur de la classe mère
        super("error");

        // Création du JSON
        try
        {
            this.getJSON().put("message", message);
        }
        catch (JSONException e)
        {
            Logger.printError(e);
        }
    }
    
    public void setArgument(String key, String value)
    {
        
    }

}
