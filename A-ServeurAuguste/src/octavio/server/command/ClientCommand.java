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

package octavio.server.command;

import java.sql.SQLException;
import octavio.server.Room;
import octavio.server.Server;
import octavio.server.User;
import octavio.server.command.client.AccountCreate;
import octavio.server.command.client.ChatSend;
import octavio.server.command.client.FinishTurn;
import octavio.server.command.client.GameConfiguration;
import octavio.server.command.client.GameMove;
import octavio.server.command.client.GameStart;
import octavio.server.command.client.LogIn;
import octavio.server.command.client.LogOut;
import octavio.server.command.client.QueryRooms;
import octavio.server.command.client.QueryUsers;
import octavio.server.command.client.RoomCreate;
import octavio.server.command.client.RoomJoin;
import octavio.server.command.client.RoomLeave;
import octavio.server.command.server.MessageConfirm;
import octavio.server.command.server.MessageError;
import octavio.server.exception.AuthentificationException;
import octavio.server.exception.CommandException;
import octavio.server.exception.InexistantRoomException;
import octavio.server.exception.NotInThisRoomException;
import octavio.server.exception.RuleException;
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
     * @param name Nom de la commande
     *
     * @return Objet commande correspondant
     *
     * @throws CommandException Nom de commande inconnu
     */
    public static ClientCommand get(String name) throws CommandException
    {
        // Identification et instanciation de la commande
        try
        {
            ClientCommand command = null;
            switch (CommandName.valueOf(name.toUpperCase()))
            {
                case ACCOUNT_CREATE: command = new AccountCreate();
                    break;
                case CHAT_SEND: command = new ChatSend();
                    break;
                case FINISH_TURN: command = new FinishTurn();
                    break;
                case GAME_CONFIGURATION: command = new GameConfiguration();
                    break;
                case GAME_MOVE: command = new GameMove();
                    break;
                case GAME_START: command = new GameStart();
                    break;
                case LOG_IN: command = new LogIn();
                    break;
                case LOG_OUT: command = new LogOut();
                    break;
                case QUERY_ROOMS: command = new QueryRooms();
                    break;
                case QUERY_USER8: command = new QueryUsers();
                    break;
                case ROOM_CREATE: command = new RoomCreate();
                    break;
                case ROOM_JOIN: command = new RoomJoin();
                    break;
                case ROOM_LEAVE: command = new RoomLeave();
                    break;
            }
            return command;
        }
        catch (IllegalArgumentException e)
        {
            // Commande inconnue
            throw new CommandException(name);
        }
    }

    /**
     * Envoi d'un message à la socket donnée. Signalisation de l'envoi.
     *
     * @param socket  Socket destinataire
     * @param message Message à envoyer
     */
    public static void send(WebSocket socket, String message)
    {
        Server.getInstance().send(socket, message);
    }

    /**
     * Envoi d'un message de confirmation à la socket spécifiée.
     *
     * @param socket Socket destinataire
     * @param type   Type du message de confirmation
     */
    public static void sendConfirm(WebSocket socket, String type)
    {
        ClientCommand.send(
                socket,
                (new MessageConfirm(type)).toString()
        );
    }

    /**
     * Envoi d'un message d'erreur au client spécifié.
     *
     * @param socket Client destinataire
     * @param type   Type du message d'erreur
     */
    public static void sendError(WebSocket socket, String type)
    {
        ClientCommand.send(
                socket,
                (new MessageError(type)).toString()
        );
    }

    /**
     * Envoi d'un message d'erreur issue d'une exception à un client.
     *
     * @param socket Socket du client
     * @param type   Type d'erreur
     * @param e      Exception
     */
    public static void sendError(WebSocket socket, String type, Exception e)
    {
        ClientCommand.send(
                socket,
                (new MessageError(type, e)).toString()
        );
    }

    // Attributs de la commande
    private WebSocket socket;  // Socket ayant reçu la commande
    private JSONObject json;    // JSON de la commande

    // Données de la commande
    private User user = null; // Utilisateur qui a émit la commande
    private Room room = null; // Salon concerné par la commande

    /**
     * Exécution de la commande.
     *
     * @throws AuthentificationException Utilisateur non-authentifié
     * @throws InexistantRoomException   Salon inexistant
     * @throws NotInThisRoomException    Utilisateur absent du salon
     * @throws RuleException             Règles enfreintes
     * @throws JSONException             Erreur JSON
     * @throws SQLException              Erreur SQL
     */
    public abstract void execute() throws AuthentificationException, InexistantRoomException, NotInThisRoomException, RuleException, JSONException, SQLException;

    /**
     * Envoi d'un message à l'utilisateur ayant émit la commande.
     *
     * @param message Message à envoyer
     */
    public void send(String message)
    {
        ClientCommand.send(this.getSocket(), message);
    }

    /**
     * Envoi d'un message de confirmation au client ayant émit la commande.
     *
     * @param type Type de confirmation
     *
     * @throws JSONException Erreur de création de JSON
     */
    public void confirm(String type) throws JSONException
    {
        ClientCommand.sendConfirm(this.getSocket(), type);
    }

    /**
     * Envoi d'un message d'erreur au client ayant émit la commande.
     *
     * @param type Type d'erreur
     *
     * @throws JSONException Erreur de création de JSON
     */
    public void error(String type) throws JSONException
    {
        ClientCommand.sendError(this.getSocket(), type);
    }

    /**
     * Vérifier ou non que l'utilisateur ayant émit la commande est authentifié.
     *
     * @return Booléen indiquant si l'authentification doit être vérifiée ou non
     */
    public boolean checkAuth()
    {
        return true;
    }

    /**
     * Indique s'il faut vérifier la présence d'un room_id et si l'utilisateur
     * doit être présent dans cette salon.
     *
     * @return Booléen indiquant si l'utilisateur doit être dans la salon
     */
    public boolean checkRoom()
    {
        return true;
    }

    /**
     * Retourne la WebSocket ayant reçu la commande.
     *
     * @return WebSocket ayant reçu la commande
     */
    public WebSocket getSocket()
    {
        return this.socket;
    }

    /**
     * Modifie la WebSocket ayant reçu la commande.
     *
     * @param socket Nouvelle socket
     */
    public void setSocket(WebSocket socket)
    {
        this.socket = socket;
    }

    /**
     * Retourne le JSONObject de la commande.
     *
     * @return JSON de la commande
     */
    public JSONObject getJSON()
    {
        return this.json;
    }

    /**
     * Modifie le JSONObject de la commande.
     *
     * @param command Nouveau JSON
     */
    public void setJSON(JSONObject command)
    {
        this.json = command;
    }

    /**
     * Retourne l'utilisateur ayant émit la commande.
     *
     * @return Utilisateur ayant émit la commande
     */
    public User getUser()
    {
        return this.user;
    }

    /**
     * Modifie l'utilisateur ayant émit la commande.
     *
     * @param user Nouvel utilisateur
     *
     * @throws AuthentificationException Non-authentifié
     * @throws JSONException             Champ "command" absent du JSON
     */
    public void setUser(User user) throws AuthentificationException, JSONException
    {
        if (user == null)
        {
            throw new AuthentificationException(this.json.getString("command"));
        }
        this.user = user;
    }

    /**
     * Retourne la salon concerné par la commande.
     *
     * @return Salon de la commande
     */
    public Room getRoom()
    {
        return this.room;
    }

    /**
     * Modifie la salon concernée par la commande.
     *
     * @param room Nouveau salon
     *
     * @throws InexistantRoomException Salon inexistant
     * @throws NotInThisRoomException  Utilisateur absent du salon
     */
    public void setRoom(Room room) throws InexistantRoomException, NotInThisRoomException
    {
        if (room == null)
        {
            throw new InexistantRoomException(0);
        }
        else if (!room.isInRoom(this.getUser()))
        {
            throw new NotInThisRoomException(room, this.getUser());
        }
        else
        {
            this.room = room;
        }
    }

    /**
     * Liste des commande émises par le user.
     */
    private enum CommandName
    {
        ACCOUNT_CREATE,
        CHAT_SEND,
        FINISH_TURN,
        GAME_CONFIGURATION,
        GAME_MOVE,
        GAME_START,
        LOG_IN,
        LOG_OUT,
        QUERY_ROOMS,
        QUERY_USER8,
        ROOM_CREATE,
        ROOM_JOIN,
        ROOM_LEAVE,
    }

}
