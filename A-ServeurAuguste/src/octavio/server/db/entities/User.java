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

package octavio.server.db.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import octavio.server.exception.CommandException;
import org.java_websocket.WebSocket;
import org.json.JSONException;

/**
 * Classe représentant un utilisateur connecté au serveur. Contient son
 * identifiant, son nom, ses paramètres et ses droits. Si un paramètre ou un
 * droit n'est pas défini, utiliser le paramètre ou le droit par défaut.
 *
 * Les méthodes statiques permettent de récupérer un utilisateur grâce à une
 * WebSocket, compter les clients connectés et authentifier ou désauthentifier
 * un client.
 *
 * Les utilisateurs sont identifiés par leur WebSocket de connexion. Si
 * l'utilisateur n'est pas identifié, il est considéré comme l'utilisateur
 * ANONYMOUS.
 *
 * @author Lzard
 */
public class User
{
    /**
     * Liste des utilisateurs par socket.
     */
    private static final HashMap<WebSocket, User> users = new HashMap<>();

    /**
     * Représentation d'utilisateur anonyme.
     */
    public static final User ANONYMOUS = new User(0, "Anonymous");

    /**
     * Retourne l'utilisateur correspondant à la socket donnée s'il est
     * authentifié ou l'utilisateur anonyme sinon.
     *
     * @param socket Socket
     *
     * @return Utilisateur de la socket
     */
    public static User getUser(WebSocket socket)
    {
        synchronized (User.users)
        {
            if (User.users.containsKey(socket))
            {
                return User.users.get(socket);
            }
            else
            {
                return User.ANONYMOUS;
            }
        }
    }

    /**
     * Retourne le nombre de clients connectés.
     *
     * @return Nombre de clients connectés
     */
    public static int getUserCount()
    {
        synchronized (User.users)
        {
            return User.users.size();
        }
    }
    
    public static ArrayList<User> getUsers()
    {
        return new ArrayList<>(User.users.values());
    }

    /**
     * Associe un utilisateur à une socket. Ajoute également cette socket à la
     * session de l'utilisateur (la créant si besoin).
     *
     * @param socket Socket à associer
     * @param user   Utilisateur à associer
     *
     * @throws CommandException Erreur lors de l'instanciation de la session
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public static void logIn(WebSocket socket, User user) throws CommandException, JSONException, SQLException
    {
        synchronized (User.users)
        {
            Session.getSession(user).addSocket(socket);
            User.users.put(socket, user);
        }
    }

    /**
     * Dissocie un utilisateur à une socket. Retire également cette socket de
     * la session de l'utilisateur (la terminant si besoin).
     *
     * @param socket Socket à désassocier
     *
     * @throws CommandException Erreur lors de l'instanciation de la session
     * @throws JSONException    Erreur JSON
     * @throws SQLException     Erreur SQL
     */
    public static void logOut(WebSocket socket) throws CommandException, JSONException, SQLException
    {
        synchronized (User.users)
        {
            Session.getSession(User.getUser(socket)).removeSocket(socket);
            User.users.remove(socket);
        }
    }

    /**
     * Identifiant de l'utilisateur.
     */
    private final int id;

    /**
     * Nom de l'utilisateur.
     */
    private String name;

    /**
     * Paramètres de l'utilisateur.
     */
    private final Properties parameters = new Properties();

    /**
     * Droits de l'utilisateur.
     */
    private final Properties rights = new Properties();

    /**
     * Instanciation d'un nouvel utilisateur. Utilise les paramètres par défaut.
     *
     * @param id   Identifiant de l'utilisateur
     * @param name Nom de l'utilisateur
     */
    public User(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Retourne l'identifiant de l'utilisateur.
     *
     * @return Identifiant de l'utilisateur
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return Nom de l'utlisateur
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Modifie le nom de l'utilisateur.
     *
     * @param name Nouveau nom
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retourne les paramètres de l'utilisateur.
     *
     * @return Paramètres de l'utilisateur
     */
    public Properties getParameters()
    {
        return this.parameters;
    }

    /**
     * Retourne un paramètre de l'utilisteur.
     *
     * @param parameter    Paramètre recherché
     * @param defaultValue Valeur par défaut
     *
     * @return Paramètre de l'utilisateur
     */
    public String getParameter(String parameter, String defaultValue)
    {
        return this.parameters.getProperty(parameter, defaultValue);
    }

    /**
     * Ajoute ou modifie un paramètre de l'utilisateur.
     *
     * @param parameter Paramètre à modifier
     * @param value     Nouvelle valeur
     */
    public void setParameter(String parameter, String value)
    {
        this.parameters.setProperty(parameter, value);
    }

    /**
     * Retourne les droits de l'utilisateur.
     *
     * @return Droits de l'utilisateur
     */
    public Properties getRights()
    {
        return this.rights;
    }

    /**
     * Retourne un droit de l'utilisateur.
     *
     * @param right        Droit recherché
     * @param defaultValue Valeur par défaut
     *
     * @return Droit de l'utilisateur
     */
    public String getRight(String right, String defaultValue)
    {
        return this.rights.getProperty(right, defaultValue);
    }

    /**
     * Ajoute ou modifie un droit de l'utilisateur.
     *
     * @param right Droit à modifier
     * @param value Nouvelle valeur
     */
    public void setRight(String right, String value)
    {
        this.rights.setProperty(right, value);
    }

    /**
     * Comparaison d'un objet avec cet objet.
     *
     * @param object Objet à comparer
     *
     * @return Booléen indiquant l'égalité entre les objets
     */
    @Override
    public boolean equals(Object object)
    {
        if (object instanceof User)
        {
            return ((User)object).getId() == this.getId();
        }
        else
        {
            return super.equals(object);
        }
    }

    /**
     * Retourne l'hashCode de l'objet.
     *
     * @return HashCode
     */
    @Override
    public int hashCode()
    {
        return 623 + this.id;
    }
}
