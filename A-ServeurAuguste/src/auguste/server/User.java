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

package auguste.server;

import auguste.server.entity.Player;
import auguste.server.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Hex;

/**
 * Classe représentant un utilisateur connecté au serveur.
 * @author Lzard
 */
public class User
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
    private String name;     // Login du joueur
    private String password; // Mot de passe hashé du joueur
    
    /**
     * Instanciation d'un utilisateur avec les valeurs données.
     * @param id ID du joueur
     * @param login Login du joueur
     * @param password Mot de passe hashé du joueur
     */
    public User(int id, String login, String password)
    {
        this.id       = id;
        this.name    = login;
        this.password = password;
    }
    
    /**
     * Instanciation d'un utilisateur à partir d'un résultat de requête.
     * @param set ResultSet d'une requête
     * @throws java.sql.SQLException
     */
    public User(ResultSet set) throws SQLException
    {
        this.id       = set.getInt   (User.COLUMN_ID);
        this.name    = set.getString(User.COLUMN_LOGIN);
        this.password = set.getString(User.COLUMN_PASSWORD);
    }
    
    /**
     * Indique si le joueur est identifié.
     * @return Booléen indiquant si le joueur est identifié
     */
    public boolean isLogged()
    {
        return this.id != User.DEFAULT_ID;
    }
    
    public boolean isInGame()
    {
        return false;
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
     * Retourne le name du joueur.
     * @return Login du joueur
     */
    public String getName()
    {
        return name;
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
     * Modifie le name du joueur.
     * @param name Login à utiliser
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Modifie le mot de passe hashé du joueur.
     * @param password Mot de passe à utiliser
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
