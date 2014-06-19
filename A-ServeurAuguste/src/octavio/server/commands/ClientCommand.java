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

import java.sql.SQLException;
import octavio.server.commands.client.AccountCreate;
import octavio.server.commands.client.ChatSend;
import octavio.server.commands.client.GameConfiguration;
import octavio.server.commands.client.GameMove;
import octavio.server.commands.client.GameStart;
import octavio.server.commands.client.LogIn;
import octavio.server.commands.client.LogOut;
import octavio.server.commands.client.QueryRooms;
import octavio.server.commands.client.RoomCreate;
import octavio.server.commands.client.RoomJoin;
import octavio.server.commands.client.RoomLeave;
import octavio.server.db.entities.User;
import octavio.server.exception.CommandException;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe abstraite des commandes user à exécuter.
 *
 * @author Lzard
 */
public abstract class ClientCommand
{
    /**
     * Retourne l'objet commande correspondant au nom de commande donné.
     *
     *
     * @param msg String du JSON de la commande
     *
     * @return Objet commande correspondant
     *
     * @throws CommandException Nom de commande inconnu
     * @throws JSONException    Erreur JSON
     */
    public static ClientCommand get(String msg) throws CommandException, JSONException
    {
        ClientCommand command;
        JSONObject json = new JSONObject(msg);
        switch (json.getString("command").toLowerCase())
        {
            case "account_create": command = new AccountCreate();
                break;
            case "chat_send": command = new ChatSend();
                break;
            case "game_configuration": command = new GameConfiguration();
                break;
            case "game_move": command = new GameMove();
                break;
            case "game_start": command = new GameStart();
                break;
            case "log_in": command = new LogIn();
                break;
            case "log_out": command = new LogOut();
                break;
            case "query_rooms": command = new QueryRooms();
                break;
            case "room_create": command = new RoomCreate();
                break;
            case "room_join": command = new RoomJoin();
                break;
            case "room_leave": command = new RoomLeave();
                break;
            default: throw new CommandException("Unknown command", "unknown_command");
        }
        command.setJson(json);
        return command;
    }

    /**
     * JSON de la commande.
     */
    private JSONObject json;

    /**
     * Utilisateur ayant émit la commande.
     */
    private User user;
    
    private WebSocket socket;

    /**
     * Exécution de la commande.
     *
     * @throws octavio.server.exception.CommandException
     * @throws JSONException                             Erreur JSON
     * @throws SQLException                              Erreur SQL
     */
    public abstract void execute() throws CommandException, JSONException, SQLException;

    public JSONObject getJSON()
    {
        return this.json;
    }

    public void setJson(JSONObject json)
    {
        this.json = json;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
    
    public WebSocket getSocket()
    {
        return this.socket;
    }
    
    public void setSocket(WebSocket socket)
    {
        this.socket = socket;
    }
}
