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
 * Commande d'envoi d'un message de chat.
 * @author Lzard
 */
public class LogConfirm extends ServerCommand
{
	public LogConfirm() throws JSONException
	{
		// Création du JSON
		this.getJSON().put("command", "log_confirm");
	}
}
