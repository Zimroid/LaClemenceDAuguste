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
 * Exception lancée lorsqu'une commande n'est pas reconnue.
 * @author Lzard
 */
public class CommandException extends Exception
{
    // Commande non-reconnue
    private final String command;
    
    /**
     * Enregistre la commande non-reconnue.
     * @param command Commande non-reconnue
     */
    public CommandException(String command)
    {
        this.command = command;
    }
    
    @Override
    public String toString()
    {
        return this.command;
    }
    
}