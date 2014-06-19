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

package octavio.server.db.managers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import octavio.server.db.entities.User;
import octavio.server.exception.CommandException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;
import org.apache.commons.codec.binary.Hex;

/**
 * Gestionnaire des objets utilisateur. Enregistre, récupère et supprime les
 * utilisateurs de la base de données. Permet également l'identification d'un
 * utilisaeur via son nom et son mot de passe hashé. Peut vérifier la
 * disponibilité d'un nom d'utilisateur.
 *
 * @author Lzard
 */
public class UserManager extends Manager
{
    /**
     * Requête pour récupérer un utilisateur.
     */
    private static final String QUERY_USER
                                = "SELECT `id`, `name` FROM `user` WHERE `id` = ?";

    /**
     * Requête pour récupérer un utilisateur avec son nom et son mot de passe.
     */
    private static final String QUERY_USER_BY_LOGIN
                                = "SELECT `id`, `name` FROM `user` WHERE `name` = ? AND `password` = ? LIMIT 1";

    /**
     * Requête pour vérifier qu'un pseudo n'est pas utilisé.
     */
    private static final String QUERY_NAME_AVAILABLE
                                = "SELECT 1 FROM `user` WHERE `name` = ? LIMIT 1";

    /**
     * Requête pour les paramètres d'un utilisateur.
     */
    private static final String QUERY_USER_PARAMETERS
                                = "SELECT `parameter`, `value` FROM `user_parameters` WHERE `user_id` = ?";

    /**
     * Requête pour récupérer les droits d'un utilisateur.
     */
    private static final String QUERY_USER_RIGHTS
                                = "SELECT `right`, `value` FROM `user_rights` WHERE `user_id` = ?";

    /**
     * Requête pour ajouter un utilisateur.
     */
    private static final String INSERT_USER
                                = "INSERT INTO `user` (`name`, `password`) VALUES(?, ?)";

    /**
     * Requête pour modifier les paramètre d'un utilisateur.
     */
    private static final String INSERT_USER_PARAMETERS
                                = "INSERT INTO `user_parameters` (`user_id`, `parameter`, `value`) VALUES(?, ?, ?)";

    /**
     * Requête pour modifier les droits d'un utilisateur.
     */
    private static final String INSERT_USER_RIGHTS
                                = "INSERT INTO `user_rights` (`user_id`, `right`, `value`) VALUES(?, ?, ?)";

    /**
     * Requête pour modifier un utilisateur.
     */
    private static final String UPDATE_USER_NAME
                                = "UPDATE `user` SET `name` = ? WHERE `id` = ?";

    /**
     * Requête pour modifier un utilisateur.
     */
    private static final String UPDATE_USER_PASSWORD
                                = "UPDATE `user` SET `password` = ? WHERE `id` = ?";

    /**
     * Requête pour supprimer un utilisateur.
     */
    private static final String DELETE_USER
                                = "DELETE FROM `user` WHERE `id` = ?";

    /**
     * Requête pour supprimer les paramètres d'un utilisateur.
     */
    private static final String DELETE_USER_PARAMETERS
                                = "DELETE FROM `user_parameters` WHERE `user_id` = ?";

    /**
     * Requête pour supprimer les droits d'un utilisateur.
     */
    private static final String DELETE_USER_RIGHTS
                                = "DELETE FROM `user_rights` WHERE `user_id` = ?";

    /**
     * Retourne la chaine de caractères fournie en paramètre hashée avec
     * l'algorithme configuré.
     *
     * @param password Mot de passe à hasher
     *
     * @return Mot de passe hashé ou chaîne vide si algorithme indisponible
     */
    public static String hashPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance(
                    Configuration.get("hash_algorithm", "SHA-1")
            );
            digest.reset();
            digest.update(
                    password.getBytes()
            );

            return new String(
                    Hex.encodeHex(
                            digest.digest()
                    )
            );
        }
        catch (NoSuchAlgorithmException exception)
        {
            Logger.printError(exception);
            return password;
        }
    }

    /**
     * Initialise la connexion à la base de données. Appel le constructeur de la
     * classe Manager pour récupérer la connexion à la base de données.
     *
     * @param connection Connexion à la base de données
     */
    public UserManager(Connection connection)
    {
        super(connection);
    }

    /**
     * Récupère un utilisateur via son nom et son mot de passe hashé.
     *
     * @param id Identifiant de l'utilisateur
     *
     * @return Instance d'User correspondant
     *
     * @throws CommandException Erreur de login
     * @throws SQLException     Erreur SQL
     */
    public User getUser(int id) throws CommandException, SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_USER);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();

        // Retourne l'utilisateur trouvé
        if (set.first())
        {
            User user = new User(
                    set.getInt("id"),
                    set.getString("name")
            );
            return user;
        }
        else
        {
            CommandException exception = new CommandException("Unknown username", "unknown_user");
            exception.setArgument("id", String.valueOf(id));
            throw exception;
        }
    }

    /**
     * Récupère un utilisateur via son nom et son mot de passe hashé.
     *
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe hashé de l'utilisateur
     *
     * @return Instance d'User correspondant à l'utilisateur trouvé ou null
     *
     * @throws CommandException Erreur de login
     * @throws SQLException     Erreur SQL
     */
    public User getUser(String name, String password) throws CommandException, SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_USER_BY_LOGIN);
        statement.setString(1, name);
        statement.setString(2, password);
        ResultSet set = statement.executeQuery();

        // Retourne l'utilisateur si le nom existe
        if (set.first())
        {
            User user = new User(
                    set.getInt("id"),
                    set.getString("name")
            );
            
            statement = this.query(UserManager.QUERY_USER_PARAMETERS);
            statement.setInt(1, user.getId());
            set = statement.executeQuery();
            while (set.next())
            {
                user.setParameter(
                        set.getString("parameter"),
                        set.getString("value")
                );
            }
            
            statement = this.query(UserManager.QUERY_USER_RIGHTS);
            statement.setInt(1, user.getId());
            set = statement.executeQuery();
            while (set.next())
            {
                user.setRight(
                        set.getString("right"),
                        set.getString("value")
                );
            }
            
            return user;
        }
        else
        {
            CommandException exception = new CommandException("Wrong username or password", "log_error");
            exception.setArgument("name", name);
            throw exception;
        }
    }

    /**
     * Indique si un nom d'utilisateur est disponible.
     *
     * @param name Nom à vérifier
     *
     * @return Disponibilité du nom
     *
     * @throws SQLException Erreur SQL
     */
    public boolean getNameAvailability(String name) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_NAME_AVAILABLE);
        statement.setString(1, name);
        ResultSet set = statement.executeQuery();

        // Vrai si le résultat est vide
        return !set.first();
    }

    /**
     * Insère un nouvel utilisateur dans la base de données.
     *
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe clair de l'utilisateur
     *
     * @throws SQLException Erreur SQL
     */
    public void insertUser(String name, String password) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.INSERT_USER);
        statement.setString(1, name);
        statement.setString(2, UserManager.hashPassword(password));
        statement.executeUpdate();
    }

    /**
     * Sauvegarde d'un utilisateur dans la base de données. Si l'utilisateur n'a
     * pas d'identifiant attribué, une nouvelle entrée lui est créée dans la
     * table et un nouvel identifiant lui est attribué. Sinon, la ligne de
     * l'utilisateur est mise à jour.
     *
     * @param user Utilisateur à sauvegarder
     *
     * @throws SQLException Erreur SQL
     */
    public void updateUserName(User user) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.UPDATE_USER_NAME);
        statement.setString(1, user.getName());
        statement.setInt(2, user.getId());
        statement.executeUpdate();
    }

    /**
     * Modifie le mot de passe d'un utilisateur.
     *
     * @param user     Utilisateur
     * @param password Nouveau mot de passe clair
     *
     * @throws SQLException Erreur SQL
     */
    public void updateUserPassword(User user, String password) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.UPDATE_USER_PASSWORD);
        statement.setString(1, UserManager.hashPassword(password));
        statement.setInt(2, user.getId());
        statement.executeUpdate();
    }

    /**
     * Modifie les paramètres de l'utilisateur.
     *
     * @param user Utilisateur à mettre à jour
     *
     * @throws SQLException Erreur SQL
     */
    public void updateUserParameters(User user) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.DELETE_USER_PARAMETERS);
        statement.setInt(1, user.getId());
        statement.executeUpdate();

        for (String parameter : user.getParameters().stringPropertyNames())
        {
            statement = this.query(UserManager.INSERT_USER_PARAMETERS);
            statement.setString(1, parameter);
            statement.setString(2, user.getParameter(parameter, ""));
            statement.setInt(3, user.getId());
        }
    }

    /**
     * Modifie les droits de l'utilisateur.
     *
     * @param user Utilisateur à mettre à jour
     *
     * @throws SQLException Erreur SQL
     */
    public void updateUserRights(User user) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.DELETE_USER_RIGHTS);
        statement.setInt(1, user.getId());
        statement.executeUpdate();

        for (String right : user.getRights().stringPropertyNames())
        {
            statement = this.query(UserManager.INSERT_USER_RIGHTS);
            statement.setString(1, right);
            statement.setString(2, user.getRight(right, ""));
            statement.setInt(3, user.getId());
        }
    }

    /**
     * Supprime un utilisateur de la base de données.
     *
     * @param user Utilisateur à supprimer
     *
     * @throws SQLException Erreur SQL
     */
    public void deleteUser(User user) throws SQLException
    {
        PreparedStatement statement = this.query(UserManager.DELETE_USER);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
}
