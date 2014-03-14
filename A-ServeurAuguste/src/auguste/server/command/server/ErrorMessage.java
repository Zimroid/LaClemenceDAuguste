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
 * Commande de signalisation qu'une erreur est survenue.
 * @author Lzard
 */
public class ErrorMessage extends ServerCommand
{
	// Déclaration des types d'erreurs
	public static final String TYPE_LOG_ERROR     = "log_error";
	public static final String TYPE_COMMAND_ERROR = "command_error";
	public static final String TYPE_RULE_ERROR    = "rule_error";
	
	/**
	 * Création du JSON de la commande
	 * @param type Type de l'erreur
	 * @throws JSONException
	 */
	public ErrorMessage(String type) throws JSONException
	{
		// Création du JSON
		this.getJSON().put("command", "error_message");
		this.getJSON().put("type", type);
	}
}
