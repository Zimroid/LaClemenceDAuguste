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

package auguste.server.manager;

import auguste.server.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manager des entitiés Player. Assurer la gestion des joueurs dans la base de
 * données.
 * @author Lzard
 */
public class UserManager extends Manager
{
    // Requête pour récupérer un utilisateur avec son ID
    private static final String QUERY_USER_FROM_ID =
            "SELECT * FROM user WHERE id = ? LIMIT 1";
    
    // Requête pour récupérer un utilisateur avec son nom et son mot de passe
    private static final String QUERY_USER_BY_LOGIN =
            "SELECT * FROM user WHERE name = ? AND password = ? LIMIT 1";
    
    // Requête pour vérifier qu'un pseudo n'est pas utilisé
    private static final String QUERY_NAME_AVAILABLE =
            "SELECT 1 FROM user WHERE name = ? LIMIT 1";
    
    // Requête pour ajouter un utilisateur
    private static final String QUERY_ADD_USER =
            "INSERT INTO user(name, password) VALUES(?, ?)";
    
    // Requête pour modifier un utilisateur
    private static final String QUERY_UPDATE_USER =
            "UPDATE user SET name = ?, password = ? WHERE id = ?";
    
    // Requête pour supprimer un utilisateur
    private static final String QUERY_DELETE_USER =
            "DELETE FROM user WHERE id = ?";

    /**
     * Initialise la connexion à la base de données.
     * @param connection
     */
    public UserManager(Connection connection)
    {
        super(connection);
    }
    
    /**
     * Récupération d'un joueur via son ID.
     * @param id ID du joueur
     * @return Instance de Player correspondant à l'ID
     * @throws java.sql.SQLException Erreur SQL
     */
    public User getUser(int id) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_USER_FROM_ID);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        set.first();

        // Retour du joueur
        return new User(set);
    }

    /**
     * Récupère un joueur via son login et son mot de passe.
     * @param login    Login du joueur
     * @param password Mot de passe du joueur
     * @return Instance de Player correspondant au joueur ou null si erreur
     * @throws java.sql.SQLException Erreur SQL
     */
    public User getUser(String login, String password) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_USER_BY_LOGIN);
        statement.setString(1, login);
        statement.setString(2, User.hashPassword(password));
        ResultSet set = statement.executeQuery();

        // Si aucun résultat, retourne null, sinon retourne le joueur
        if (set.first()) return new User(set);
        else             return null;
    }
    
    /**
     * Retourne si un nom de joueur est disponible.
     * @param name Nom à vérifier
     * @return Booléen indiquant la disponibilité du nom
     * @throws SQLException Erreur SQL
     */
    public boolean getNameAvailable(String name) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_NAME_AVAILABLE);
        statement.setString(1, name);
        ResultSet set = statement.executeQuery();
        
        // Vrai si le résultat est vide
        return !set.first();
    }

    /**
     * Sauvegarde d'un joueur dans la base de données.
     * @param user Joueur à sauvegarder
     * @throws java.sql.SQLException Erreur SQL
     */
    public void saveUser(User user) throws SQLException
    {
        // Si c'est un nouveau joueur, on l'insère, sinon on le met à jour
        if (user.getId() == User.DEFAULT_ID)
        {
            // Préparation et exécution de la requête
            PreparedStatement statement = this.query(UserManager.QUERY_ADD_USER, true);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            
            // Récupération de l'ID du nouveau joueur
            ResultSet keysSet = statement.getGeneratedKeys();
            keysSet.first();
            user.setId(keysSet.getInt(1));
        }
        else
        {
            // Préparation et exécution de la requête
            PreparedStatement statement = this.query(UserManager.QUERY_UPDATE_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setInt   (3, user.getId());
            statement.executeUpdate();
        }
    }
    
    /**
     * Supprime un joueur de la base de données.
     * @param user Joueur à supprimer
     * @throws SQLException Erreur SQL
     */
    public void deleteUser(User user) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_UPDATE_USER);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
    
}
