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

package octavio.server;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import octavio.server.commands.ClientCommand;
import octavio.server.commands.ServerCommand;
import octavio.server.commands.server.ErrorCommand;
import octavio.server.db.entities.Session;
import octavio.server.db.entities.User;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;

/**
 * Classe du serveur. Hérite de la classe WebSocketServer, permettant de lancer
 * le serveur de WebSocket, et d'effectuer une action lors d'un évènement lié
 * à une WebSocket.
 *
 * Gère la connexion et la déconnexion des clients et les erreurs liées aux
 * WebSockets. Lors de la réception d'un message d'un client, identifie
 * l'utilisateur, la session et la commande, puis lance son exécution.
 *
 * @author Lzard
 */
public class Server extends WebSocketServer
{
    /**
     * Singleton du serveur.
     */
    private static final Server INSTANCE = new Server(
            new InetSocketAddress(
                    Configuration.get("server_host_name", "localhost"),
                    Configuration.getInt("server_host_port", 67471)
            )
    );

    /**
     * Retourne l'instance du serveur.
     *
     * @return Instance du serveur
     */
    public static Server getInstance()
    {
        return Server.INSTANCE;
    }

    /**
     * Instanciation du serveur. Effectue un simple appel au constructeur de la
     * classe mère WebSocketServer en utilisant l'adresse donnée et valide le
     * lancement du serveur.
     *
     * @param address Adresse à laquelle le serveur est attachée
     */
    private Server(InetSocketAddress address)
    {
        super(address);
        Logger.print("Server started on " + address);
    }

    /**
     * Méthode appelée lors de la connexion d'un client. Signale simplement la
     * connexion de l'utilisateur.
     *
     * @param socket    WebSocket créée
     * @param handshake Données handshake générées
     */
    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake)
    {
        Logger.print(socket.getRemoteSocketAddress() + ": Connection opened");
    }

    /**
     * Méthode appelée lors de la déconnexion d'un client. Signale la
     * déconnexion de l'utilisateur et désautentifie l'utilisateur si
     * nécessaire.
     *
     * @param socket    WebSocket fermée
     * @param code      Code décrivant la raison de la fermeture
     * @param reason    Raison de la fermeture
     * @param byRequest Fermeture demandée par le client ?
     */
    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean byRequest)
    {
        // Signalisation
        Logger.print(
                socket.getRemoteSocketAddress() + ": Connection closed"
                + (byRequest ? " by client" : "")
                + (reason.length() > 0 ? ": " + reason : "")
                + " (" + code + ")"
        );

        // Désauthentification du client et retrait de l'utilisateur si nécessaire
        try
        {
            User.logOut(socket);
        }
        catch (CommandException | JSONException | SQLException exception)
        {
            Logger.printError(exception);
        }
    }

    /**
     * Méthode appelée lors de la réception d'un message du client. Procède à
     * l'identification du client et son message, puis lance l'exécution dans
     * le cadre d'une session s'il s'agit d'un client authentifié.
     *
     * Si le message provoque une erreur issue des paramètres d'une commande,
     * celle-ci est transmise au client ayant émit ce message avec une commande
     * erreur. S'il s'agit d'une erreur du serveur, une commande d'erreur
     * particulière est émise, décrivant l'exception si le serveur est en mode
     * debug.
     *
     * @param socket  WebSocket concernée
     * @param message Message reçu
     */
    @Override
    public void onMessage(WebSocket socket, String message)
    {
        try
        {
            User user = User.getUser(socket);
            ClientCommand command = ClientCommand.get(message);
            command.setSocket(socket);

            if (user != User.ANONYMOUS)
            {
                Session.getSession(user).receive(command);
            }
            else
            {
                command.execute();
            }
        }
        catch (CommandException exception)
        {
            Logger.printError(exception);

            ErrorCommand command = exception.getErrorCommand();

            if (Configuration.isDebugging())
            {
                command.setArgument("exception", exception.getClass().getSimpleName());
                command.setArgument("message", exception.getMessage());
                command.setArgument("stacktrace", Logger.getStackTraceString(exception));
            }

            socket.send(command.getMessage());
        }
        catch (JSONException | SQLException | RuntimeException exception)
        {
            Logger.printError(exception);

            ErrorCommand command = new ErrorCommand("server_error");

            if (Configuration.isDebugging())
            {
                command.setArgument("exception", exception.getClass().getSimpleName());
                command.setArgument("message", exception.getMessage());
                command.setArgument("stacktrace", Logger.getStackTraceString(exception));
            }

            socket.send((new ErrorCommand(exception)).getMessage());
        }
    }

    /**
     * Méthode appelée lorsqu'une erreur est provoquée par une WebSocket.
     * Affiche la socket concernée si elle est définie, et décrit l'exception
     * lancée.
     *
     * @param socket WebSocket concernée (null en cas d'erreur serveur)
     * @param e      Exception émise
     */
    @Override
    public void onError(WebSocket socket, Exception e)
    {
        if (socket != null)
        {
            Logger.printError(socket.getRemoteSocketAddress() + ": Error");
        }

        Logger.printError(e);
    }

    /**
     * Envoie une commande à tous les clients connectés.
     *
     * @param command Commande à envoyer
     */
    public void broadcast(ServerCommand command)
    {
        for (WebSocket socket : this.connections())
        {
            socket.send(command.getMessage());
        }
    }

    /**
     * Termine toutes les sessions en cours sur le serveur.
     */
    public void shutdown()
    {
        this.connections().stream().forEach(
                (socket) ->
                {
                    socket.close(0);
                }
        );
    }
}
