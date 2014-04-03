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
import auguste.server.User;
import auguste.server.command.server.MessageConfirm;
import auguste.server.command.server.MessageError;
import auguste.server.exception.AuthentificationException;
import auguste.server.exception.CommandException;
import auguste.server.exception.RoomException;
import auguste.server.exception.RoomException.Type;
import auguste.server.exception.RuleException;
import java.sql.SQLException;
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
     * Retourne l'objet commande correspondant au nom donné.
     * @param name Nom de la commande
     * @return Objet commande correspondant
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
                case ACCOUNT_CREATE: command = new AccountCreate(); break;
                case CHAT_SEND:      command = new ChatSend();      break;
                case CHAT_USERLIST:  command = new ChatUserList();  break;
                case GAME_CONFIG:    command = new GameConfig();    break;
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
        catch(IllegalArgumentException e)
        {
            // Commande inconnue
            throw new CommandException(name);
        }
    }
    
    /**
     * Envoi d'un message à la socket donnée. Signalisation de l'envoi.
     * @param socket  Socket destinataire
     * @param message Message à envoyer
     */
    public static void send(WebSocket socket, String message)
    {
        Server.getInstance().send(socket, message);
    }
    
    /**
     * Envoi d'un message de confirmation à la socket spécifiée.
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
    
    // Attributs de la commande
    private WebSocket  socket;  // Socket ayant reçu la commande
    private JSONObject json;    // JSON de la commande
    
    // Données de la commande
    private User user = null; // Utilisateur qui a émit la commande
    private Room room = null; // salon concernée par la commande
    
    /**
     * Exécution de la commande.
     * @throws auguste.server.exception.AuthentificationException Utilisateur non-authentifié
     * @throws auguste.server.exception.RoomException             Erreur de salon
     * @throws auguste.server.exception.RuleException             Règles enfreintes
     * @throws org.json.JSONException                             Erreur JSON
     * @throws java.sql.SQLException                              Erreur SQL
     */
    public abstract void execute() throws AuthentificationException, RoomException, RuleException, JSONException, SQLException;
    
    /**
     * Envoi d'un message à l'utilisateur ayant émit la commande.
     * @param message Message à envoyer
     */
    public void send(String message)
    {
        ClientCommand.send(this.getSocket(), message);
    }
    
    /**
     * Envoi d'un message de confirmation au client ayant émit la commande.
     * @param type Type de confirmation
     * @throws JSONException Erreur de création de JSON
     */
    public void confirm(String type) throws JSONException
    {
        ClientCommand.sendConfirm(this.getSocket(), type);
    }
    
    /**
     * Envoi d'un message d'erreur au client ayant émit la commande.
     * @param type Type d'erreur
     * @throws JSONException Erreur de création de JSON
     */
    public void error(String type) throws JSONException
    {
        ClientCommand.sendError(this.getSocket(), type);
    }

    /**
     * Vérifier ou non que l'utilisateur ayant émit la commande est authentifié.
     * @return Booléen indiquant si l'authentification doit être vérifiée ou non
     */
    public boolean checkAuth()
    {
        return true;
    }
    
    /**
     * Indique s'il faut vérifier la présence d'un room_id et si l'utilisateur
     * doit être présent dans cette salon.
     * @return Booléen indiquant si l'utilisateur doit être dans la salon
     */
    public boolean checkRoom()
    {
        return true;
    }

    /**
     * Retourne la WebSocket ayant reçu la commande.
     * @return WebSocket ayant reçu la commande
     */
    public WebSocket getSocket()
    {
        return this.socket;
    }
    
    /**
     * Retourne le JSONObject de la commande.
     * @return JSONObject de la json
     */
    public JSONObject getJSON()
    {
        return this.json;
    }

    /**
     * Retourne le user ayant émit la commande.
     * @return User ayant émit la commande
     */
    public User getUser()
    {
        return this.user;
    }
    
    /**
     * Retourne la salon concernée par la commande.
     * @return salon de la commande
     */
    public Room getRoom()
    {
        return this.room;
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
     * Modifie le JSONObject de la commande.
     * @param command JSONObject à utiliser
     */
    public void setJSON(JSONObject command)
    {
        this.json = command;
    }

    /**
     * Modifie le user ayant émit la commande.
     * @param user Utilisateur à utiliser
     * @throws auguste.server.exception.AuthentificationException Non-authentifié
     * @throws org.json.JSONException                             Champ "command" absent du JSON
     */
    public void setUser(User user) throws AuthentificationException, JSONException
    {
        if (user == null) throw new AuthentificationException(this.json.getString("command"));
        this.user = user;
    }
    
    /**
     * Modifie la salon concernée par la commande.
     * @param room salon à utiliser
     * @throws auguste.server.exception.RoomException Utilisateur absent de la salon
     */
    public void setRoom(Room room) throws RoomException
    {
        if (room == null) throw new RoomException(Type.INEXISTANT_ROOM);
        else if (!room.isInRoom(user)) throw new RoomException(Type.NOT_IN_THIS_ROOM);
        else this.room = room;
    }
    
    /**
    * Liste des commande émises par le user.
    */
    private enum CommandName
    {
        ACCOUNT_CREATE,
        CHAT_SEND,
        CHAT_USERLIST,
        GAME_CONFIG,
        GAME_CREATE,
        GAME_JOIN,
        GAME_LEAVE,
        GAME_LIST,
        GAME_START,
        LOG_IN,
        LOG_OUT
    }

}
