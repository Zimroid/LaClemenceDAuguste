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

import auguste.server.entity.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manager des entitiés Player. Assurer la gestion des joueurs dans la base de
 * données.
 * @author Lzard
 */
public class PlayerManager extends Manager
{
	// Requête pour récupérer un joueur avec son ID
	private static final String QUERY_PLAYER_FROM_ID =
			"SELECT * FROM player WHERE id = ?";
	
	// Requête pour récupérer un joueur avec son login et mot de passe
	private static final String QUERY_PLAYER_FROM_LOGIN =
			"SELECT * FROM player WHERE login = ? AND password = ?";
	
	// Requête pour ajouter un utilisateur
	private static final String QUERY_ADD_PLAYER =
			"INSERT INTO player(login, password) VALUES(?, ?)";
	
	// Requête pour modifier un utilisateur
	private static final String QUERY_UPDATE_PLAYER =
			"UPDATE player SET login = ?, password = ? WHERE id = ?";
	
	// Requête pour supprimer un utilisateur
	private static final String QUERY_DELETE_PLAYER =
			"DELETE FROM player WHERE id = ?";

	/**
	 * Constructeur. Initialise la connexion à la base de données.
	 * @param connection
	 */
	public PlayerManager(Connection connection)
	{
		super(connection);
	}
	
	/**
	 * Récupération d'un joueur via son ID.
	 * @param id ID du joueur
	 * @return Instance de Player correspondant à l'ID
	 * @throws java.sql.SQLException
	 */
	public Player getPlayer(int id) throws SQLException
	{
		// Préparation et exécution de la requête
		PreparedStatement statement = this.getConnection().prepareStatement(PlayerManager.QUERY_PLAYER_FROM_ID);
		statement.setInt(1, id);
		ResultSet set = statement.executeQuery();
		set.first();

		// Retour du joueur
		return new Player(set);
	}

	/**
	 * Récupère un joueur via son login et son mot de passe.
	 * @param login Login du joueur
	 * @param password Mot de passe du joueur
	 * @return Instance de Player correspondant au joueur ou null si erreur
	 * @throws java.sql.SQLException
	 */
	public Player getPlayer(String login, String password) throws SQLException
	{
		// Préparation et exécution de la requête
		PreparedStatement statement = this.getConnection().prepareStatement(PlayerManager.QUERY_PLAYER_FROM_LOGIN);
		statement.setString(1, login);
		statement.setString(2, Player.hashPassword(password));
		ResultSet set = statement.executeQuery();

		// Si aucun résultat, retourne null, sinon retourne le joueur
		if (set.first()) return new Player(set);
		else             return null;
	}

	/**
	 * Sauvegarde d'un joueur dans la base de données.
	 * @param player Joueur à sauvegarder
	 * @throws java.sql.SQLException
	 */
	public void savePlayer(Player player) throws SQLException
	{
		// Si c'est un nouveau joueur, on l'insère, sinon on le met à jour
		if (player.getId() == Player.DEFAULT_ID)
		{
			// Préparation et exécution de la requête
			PreparedStatement statement = this.getConnection().prepareStatement(PlayerManager.QUERY_ADD_PLAYER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, player.getLogin());
			statement.setString(2, player.getPassword());
			statement.executeUpdate();
			
			// Récupération de l'ID du nouveau joueur
			ResultSet keysSet = statement.getGeneratedKeys();
			keysSet.first();
			player.setId(keysSet.getInt(1));
		}
		else
		{
			// Préparation et exécution de la requête
			PreparedStatement statement = this.getConnection().prepareStatement(PlayerManager.QUERY_UPDATE_PLAYER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, player.getLogin());
			statement.setString(2, player.getPassword());
			statement.setInt   (3, player.getId());
			statement.executeUpdate();
		}
	}
	
	/**
	 * Supprime un joueur de la base de données.
	 * @param player Joueur à supprimer
	 * @throws SQLException
	 */
	public void deletePlayer(Player player) throws SQLException
	{
		// Préparation et exécution de la requête
		PreparedStatement statement = this.getConnection().prepareStatement(PlayerManager.QUERY_UPDATE_PLAYER, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, player.getId());
		statement.executeUpdate();
	}
}
