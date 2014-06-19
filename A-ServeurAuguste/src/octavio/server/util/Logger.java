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

package octavio.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import octavio.server.db.entities.Room;
import octavio.server.db.entities.Session;
import octavio.server.db.entities.User;

/**
 * Classe utilitaire de journal des messages du serveur. Met à disposition du
 * programme des méthodes statiques pour enregistrer des messages indiquant
 * le bon fonctionnement du serveur où la présence d'erreurs.
 *
 * @author Lzard
 */
public class Logger
{
    /**
     * Affiche un message indiquant un comportement normal du serveur. L'heure
     * du message est également affichée.
     *
     * @param message Message à afficher
     */
    public static void print(String message)
    {
        System.out.println(
                "    "
                + ((new SimpleDateFormat("HH:mm:ss:SSS")).format(new Date())) + " "
                + message
        );
    }

    /**
     * Affiche un message indiquant une erreur du programme. L'heure de l'erreur
     * est également affichée.
     *
     * @param message Message d'erreur à afficher
     */
    public static void printError(String message)
    {
        System.err.println(
                "/!\\ "
                + ((new SimpleDateFormat("HH:mm:ss:SSS")).format(new Date())) + " "
                + message
        );
    }

    /**
     * Affiche un message signalant une exception lancée par le programme.
     * L'heure de l'émission est également affichée.
     *
     * @param exception Exception à afficher
     */
    public static void printError(Exception exception)
    {
        Logger.printError(
                exception.getClass().getSimpleName() + ": "
                + exception.getMessage()
                + Logger.getStackTraceString(exception)
        );
    }

    /**
     * Ecrit la stack trace d'une exception dans un nouvel objet String.
     *
     * @param exception Exception à analyser
     *
     * @return String représentant la stack trace
     */
    public static String getStackTraceString(Exception exception)
    {
        String stackTrace = new String();

        for (StackTraceElement element : exception.getStackTrace())
        {
            stackTrace = stackTrace.concat(
                    "\n\t at "
                    + element.getClassName() + "."
                    + element.getMethodName() + " ("
                    + element.getFileName() + ":"
                    + element.getLineNumber() + ")"
            );
        }

        return stackTrace;
    }
}
