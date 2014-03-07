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

package auguste.server.entity;

import auguste.server.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Hex;


/**
 * Classe représentant un joueur.
 * @author Vr4el / Lzard
 */
public class Player
{
	// Définitions des colonnes
	private static final String COLUMN_ID       = "id";
	private static final String COLUMN_LOGIN    = "login";
	private static final String COLUMN_PASSWORD = "password";
	
	private int    id;       // ID du joueur
	private String login;    // Login du joueur
	private String password; // Mot de passe hashé du joueur
	
	/**
	 * Instanciation d'un utilisateur avec les valeurs données.
	 * @param id ID du joueur
	 * @param login Login du joueur
	 * @param password Mot de passe hashé du joueur
	 */
	public Player(int id, String login, String password)
	{
		this.id       = id;
		this.login    = login;
		this.password = password;
	}
	
	/**
	 * Instanciation d'un utilisateur à partir d'un résultat de requête.
	 * @param set ResultSet d'une requête
	 * @throws java.sql.SQLException
	 */
	public Player(ResultSet set) throws SQLException
	{
		this.id       = set.getInt   (Player.COLUMN_ID);
		this.login    = set.getString(Player.COLUMN_LOGIN);
		this.password = set.getString(Player.COLUMN_PASSWORD);
	}

	/**
	 * Retourne l'ID du joueur.
	 * @return ID du joueur
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Retourne le login du joueur.
	 * @return Login du joueur
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * Retourne le mot de passe du joueur.
	 * @return Mot de passe du joueur
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Modifie l'ID du joueur.
	 * @param id ID à utiliser
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Modifie le login du joueur
	 * @param login Login à utiliser
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * Hash et modifie le mot de passe du joueur
	 * @param password Mot de passe à hasher et à utiliser
	 */
	public void setPassword(String password)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(password.getBytes());
			this.password = new String(Hex.encodeHex(digest.digest()));
		}
		catch (NoSuchAlgorithmException ex)
		{
			Log.error("MessageDigest algorithm unavailable: " + ex);
		}
	}
}
