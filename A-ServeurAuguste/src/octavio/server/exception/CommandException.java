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

package octavio.server.exception;

import java.util.Properties;
import octavio.server.commands.server.ErrorCommand;

/**
 * Exception émise lorsqu'une commande réalisée par un client est impossible à
 * effectuer. Cette exception stocke également une liste d'arguments pour
 * préciser l'erreur.
 *
 * @author Lzard
 */
public class CommandException extends Exception
{
    /**
     * Code du message d'erreur.
     */
    private final String code;

    /**
     * Arguments du message d'erreur.
     */
    private final Properties args = new Properties();

    /**
     * Création de l'exception avec le message donné.
     *
     * @param message Message d'erreur
     * @param code    Code du message d'erreur à envoyer aux clients
     */
    public CommandException(String message, String code)
    {
        super(message);
        this.code = code;
    }

    /**
     * Ajoute un argument au message d'erreur.
     *
     * @param key   Nom de l'argument
     * @param value Valeur de l'argument
     */
    public void setArgument(String key, String value)
    {
        this.args.setProperty(key, value);
    }

    /**
     * Retourne une nouvelle instance de commande d'erreur à émettre avec cette
     * exception.
     *
     * @return Commande d'erreur décrivant l'exception
     */
    public ErrorCommand getErrorCommand()
    {
        ErrorCommand command = new ErrorCommand(this.code);

        this.args.stringPropertyNames().stream().forEach((key) ->
        {
            command.setArgument(
                    key,
                    this.args.getProperty(key)
            );
        });

        return command;
    }
}
