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

import auguste.server.util.Configuration;
import auguste.server.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Hex;

/**
 * Classe représentant un utilisateur authentifié et connecté au serveur. Gère
 * les données relatives à l'utilisateur.
 * @author Lzard
 */
public class User
{
    public final static int UNREGISTERED_ID = 0; // ID d'un utilisateur non-enregistré
    
    /**
     * Hashage de mot de passe.
     * @param password Mot de passe à hacher
     * @return Mot de passe hashé ou chaîne vide en cas d'erreur
     */
    public static String hashPassword(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance(Configuration.get("hash_algorithm"));
            digest.reset();
            digest.update(password.getBytes());
            return new String(Hex.encodeHex(digest.digest()));
        }
        catch (NoSuchAlgorithmException e)
        {
            // Algorithme indisponible
            Log.error("Hash algorithm unavailable");
            Log.debug(e.toString());
            return new String();
        }
    }
    
    // Attributs
    private int    id;        // ID de l'utilisateur
    private String name;      // Nom de l'utilisateur
    private String password;  // Mot de passe hashé de l'utilisateur
    
    /**
     * Instanciation d'un utilisateur à partir de données brutes.
     * @param id       ID de l'utilisateur
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe hashé de l'utilisateur
     */
    public User(int id, String name, String password)
    {
        this.id       = id;
        this.name     = name;
        this.password = password;
    }
    
    /**
     * Instanciation d'un utilisateur à partir d'un résultat de requête.
     * @param set    ResultSet d'une requête
     * @throws java.sql.SQLException Erreur SQL
     */
    public User(ResultSet set) throws SQLException
    {
        this.id       = set.getInt   ("id");
        this.name     = set.getString("name");
        this.password = set.getString("password");
    }

    /**
     * Retourne l'ID de l'utilisateur.
     * @return ID de l'utilisateur
     */
    public int getId()
    {
        return id;
    }

    /**
     * Retourne le nom de l'utilisateur.
     * @return Nom de l'utlisateur
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retourne le mot de passe hashé de l'utilisateur.
     * @return Mot de passe de l'utilisateur
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Modifie l'ID de l'utilisateur.
     * @param id ID à utiliser
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Modifie le nom de l'utilisateur.
     * @param name Nom à utiliser
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Modifie le mot de passe hashé de l'utilisateur.
     * @param password Mot de passe à utiliser
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
}
