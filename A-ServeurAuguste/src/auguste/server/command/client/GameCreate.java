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

package auguste.server.command.client;

import auguste.server.Room;
import auguste.server.Server;
import auguste.server.exception.AuthentificationException;
import org.json.JSONException;

/**
 * Commande de création d'une partie.
 * @author Lzard
 */
public class GameCreate extends ClientCommand
{
    @Override
    public void execute() throws JSONException, AuthentificationException
    {
        // Vérification de l'authentification de l'utilisateur
        this.checkAuth();
        
        // Création de la salle
        Room newRoom = Server.getInstance().createRoom(
                this.getJSON().getString("game_name")
        );
        newRoom.addUser(this.getSocket(), this.getUser());
        newRoom.setOwner(this.getUser());
        newRoom.confirm();
    }
    
}
