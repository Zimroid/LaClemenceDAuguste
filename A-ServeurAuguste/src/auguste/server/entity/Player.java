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

import auguste.server.entity.action.Action;
import auguste.server.entity.pawn.Pawn;
import auguste.server.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Hex;


/**
 * Classe représentant un joueur.
 * @author Lzard
 */
public class Player
{
	// Définitions des colonnes
	private static final String COLUMN_ID       = "id";
	private static final String COLUMN_LOGIN    = "login";
	private static final String COLUMN_PASSWORD = "password";
	
	// Valeurs par défaut des champs d'un utilisateur non loggé
	public static final int    DEFAULT_ID       = 0;
	public static final String DEFAULT_LOGIN    = "Anonymous";
	public static final String DEFAULT_PASSWORD = "";
	
	/**
	 * Hashage de mot de passe.
	 * @param password Mot de passe à hacher
	 * @return Mot de passe hashé ou chaîne vide en cas d'erreur
	 */
	public static String hashPassword(String password)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(password.getBytes());
			return new String(Hex.encodeHex(digest.digest()));
		}
		catch (NoSuchAlgorithmException ex)
		{
			// Algorithme indisponible
			Log.error("MessageDigest algorithm unavailable: " + ex);
			return new String();
		}
	}
	
	// Attributs
	private int    id;       // ID du joueur
	private String login;    // Login du joueur
	private String password; // Mot de passe hashé du joueur
        
        // Variables métier
        private final ArrayList<Pawn> pawns;
        private Team team;
        private Action action;
        private Game game;
	
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
                this.pawns = new ArrayList<>();
	}
	
	/**
	 * Instanciation d'un utilisateur à partir d'un résultat de requête.
	 * @param set ResultSet d'une requête
	 * @throws java.sql.SQLException
	 */
	public Player(ResultSet set) throws SQLException
	{
        this.pawns = new ArrayList<>();
		this.id       = set.getInt   (Player.COLUMN_ID);
		this.login    = set.getString(Player.COLUMN_LOGIN);
		this.password = set.getString(Player.COLUMN_PASSWORD);
	}
	
	/**
	 * Indique si le joueur est identifié.
	 * @return Booléen indiquant si le joueur est identifié
	 */
	public boolean isLogged()
	{
		return this.id != Player.DEFAULT_ID;
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
	 * Retourne le mot de passe hashé du joueur.
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
	 * Modifie le login du joueur.
	 * @param login Login à utiliser
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * Modifie le mot de passe hashé du joueur.
	 * @param password Mot de passe à utiliser
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

    /**
     * @return the pawns
     */
    public ArrayList<Pawn> getPawns()
    {
        return pawns;
    }
    
    /**
     * @param pawn the pawn to add
     */
    public void addPawn(Pawn pawn)
    {
        this.pawns.add(pawn);
    }
    
    /**
     * @param pawn the pawn to remove
     */
    public void removePawn(Pawn pawn)
    {
        this.pawns.remove(pawn);
    }

    /**
     * @return the team
     */
    public Team getTeam()
    {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team)
    {
        this.team = team;
    }

    /**
     * @return the action
     */
    public Action getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action)
    {
        this.action = action;
    }

    /**
     * @return the game
     */
    public Game getGame()
    {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game)
    {
        this.game = game;
    }
}
