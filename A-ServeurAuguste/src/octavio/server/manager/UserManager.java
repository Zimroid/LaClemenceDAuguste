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

package octavio.server.manager;

import octavio.server.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Gestionnaire des objets User. Enregistre, charge et supprime les utilisateurs
 * de la base de données. Permet également l'identification d'un utilisaeur via
 * son nom et son mot de passe hashé. Vérifie la disponibilité d'un nom
 * d'utilisateur.
 * 
 * @author Lzard
 */
public class UserManager extends Manager
{
    // Requête pour récupérer un utilisateur avec son nom et son mot de passe
    private static final String QUERY_USER_BY_LOGIN =
            "SELECT * FROM `user` WHERE `name` = ? AND `password` = ? LIMIT 1";
    
    // Requête pour vérifier qu'un pseudo n'est pas utilisé
    private static final String QUERY_NAME_AVAILABLE =
            "SELECT 1 FROM `user` WHERE `name` = ? LIMIT 1";
    
    // Requête pour ajouter un utilisateur
    private static final String QUERY_ADD_USER =
            "INSERT INTO `user` (`name`, `password`) VALUES(?, ?)";
    
    // Requête pour modifier un utilisateur
    private static final String QUERY_UPDATE_USER =
            "UPDATE `user` SET `name` = ?, `password` = ? WHERE `id` = ?";
    
    // Requête pour supprimer un utilisateur
    private static final String QUERY_DELETE_USER =
            "DELETE FROM `user` WHERE `id` = ?";

    /**
     * Initialise la connexion à la base de données. Appel le constructeur de la
     * classe Manager.
     * @param connection Connexion à la base de données
     */
    public UserManager(Connection connection)
    {
        super(connection);
    }

    /**
     * Récupère un utilisateur via son nom et son mot de passe hashé.
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe hashé de l'utilisateur
     * @return Instance d'User correspondant à l'utilisateur trouvé ou null
     * @throws SQLException Erreur SQL
     */
    public User getUser(String name, String password) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_USER_BY_LOGIN);
        statement.setString(1, name);
        statement.setString(2, password);
        ResultSet set = statement.executeQuery();

        // Si aucun résultat, retourne null, sinon retourne l'utilisateur
        if (set.first()) return new User(set);
        else             return null;
    }
    
    /**
     * Indique si un nom d'utilisateur est disponible.
     * @param name Nom à vérifier
     * @return Disponibilité du nom
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
     * Sauvegarde d'un utilisateur dans la base de données. Si l'utilisateur n'a
     * pas d'identifiant attribué, une nouvelle entrée lui est créée dans la
     * table et un nouvel identifiant lui est attribué. Sinon, la ligne de
     * l'utilisateur est mise à jour.
     * @param user Utilisateur à sauvegarder
     * @throws SQLException Erreur SQL
     */
    public void saveUser(User user) throws SQLException
    {
        // Si c'est un nouveau joueur, on l'insère, sinon on le met à jour
        if (!user.isSaved())
        {
            // Préparation et exécution de la requête
            PreparedStatement statement = this.query(UserManager.QUERY_ADD_USER, true);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            
            // Sauvegarde de l'identifiant du nouvel utilisateur
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.first();
            user.setId(generatedKeys.getInt(1));
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
     * Supprime un utilisateur de la base de données.
     * @param user Utilisateur à supprimer
     * @throws SQLException Erreur SQL
     */
    public void deleteUser(User user) throws SQLException
    {
        // Préparation et exécution de la requête
        PreparedStatement statement = this.query(UserManager.QUERY_DELETE_USER);
        statement.setInt(1, user.getId());
        statement.executeUpdate();
    }
    
}
