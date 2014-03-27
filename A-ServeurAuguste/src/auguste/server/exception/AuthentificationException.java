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

package auguste.server.exception;

/**
 * Exception lancée lorsque l'utilisateur tente d'effectuer une action qu'il ne
 * peut effectuer sans être authentifié.
 * @author Lzard
 */
public class AuthentificationException extends Exception
{
    /**
     * Enregistre la commande demandée qui nécessite d'être authentifié.
     * @param command Commande demandée
     */
    public AuthentificationException(String command)
    {
        super("Must be logged to use " + command);
    }
    
}
