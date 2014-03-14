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

import auguste.server.entity.Player;
import auguste.server.exception.RuleException;
import auguste.server.exception.UnknownCommandException;
import java.sql.SQLException;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe abstraite des commandes client à exécuter.
 * @author Lzard
 */
public abstract class ClientCommand
{
    /**
     * Retourne l'objet commande correspondant au nom de la commande.
     * @param name Nom de la commande
     * @return Objet commande correspondant
     * @throws UnknownCommandException Nom de commande inconnu
     */
    public static ClientCommand get(String name) throws UnknownCommandException
    {
        // Identification et instanciation de la commande
        try
        {
            ClientCommand command = null;
            switch (CommandName.valueOf(name.toUpperCase()))
            {
                case ACCOUNT_CREATE: command = new AccountCreate(); break;
                case CHAT_SEND:      command = new ChatSend();      break;
                case GAME_CREATE:    command = new GameCreate();    break;
                case GAME_JOIN:      command = new GameJoin();      break;
                case GAME_LEAVE:     command = new GameLeave();     break;
                case GAME_LIST:      command = new GameList();      break;
                case GAME_START:     command = new GameStart();     break;
                case LOG_IN:         command = new LogIn();         break;
                case LOG_OUT:        command = new LogOut();        break;
            }
            return command;
        }
        catch(IllegalArgumentException ex)
        {
            // Commande inconnue
            throw new UnknownCommandException(name);
        }
    }
    
    // Attributs
    private JSONObject json;    // JSON de la commande
    private Player     player;  // Joueur qui a émit la commande
    private WebSocket  socket;  // Socket ayant reçu la commande
    
    /**
     * Exécution de la commande.
     * @throws java.sql.SQLException Erreur SQL
     * @throws org.json.JSONException Erreur JSON
     * @throws auguste.server.exception.RuleException Règles enfreintes
     */
    public abstract void execute() throws RuleException, JSONException, SQLException;

    /**
     * Retourne le JSONObject de la commande.
     * @return JSONObject de la json
     */
    public JSONObject getJSON()
    {
        return json;
    }

    /**
     * Retourne le joueur ayant émit la commande.
     * @return Player ayant émit la commande
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Retourne la WebSocket ayant reçu la commande.
     * @return WebSocket ayant reçu la commande
     */
    public WebSocket getSocket() {
        return socket;
    }

    /**
     * Modifie le JSONObject de la commande.
     * @param command JSONObject à utiliser
     */
    public void setJSON(JSONObject command)
    {
        this.json = command;
    }

    /**
     * Modifie le joueur ayant émit la commande.
     * @param player Player à utiliser
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * Modifie la WebSocket ayant reçu la commande.
     * @param socket WebSocket à utiliser
     */
    public void setSocket(WebSocket socket)
    {
        this.socket = socket;
    }
    
    /**
    * Liste des commande émises par le client.
    */
    private enum CommandName
    {
        ACCOUNT_CREATE,
        CHAT_SEND,
        GAME_CREATE,
        GAME_JOIN,
        GAME_LEAVE,
        GAME_LIST,
        GAME_START,
        LOG_IN,
        LOG_OUT
    }

}
