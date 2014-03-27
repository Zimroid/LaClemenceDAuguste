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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Hex;

/**
 * Classe représentant un utilisateur connecté au serveur et authentifié. Gère
 * les données relatives à l'utilisateur. Contient la liste des salles
 * auxquelles l'utilisateur appartient.
 * @author Lzard
 */
public class User
{
    private final static int UNREGISTERED_ID = 0; // ID d'un utilisateur non-enregistré
    
    /**
     * Renvoie la chaine de caractères fournie en paramètre hashée.
     * @param password Mot de passe à hasher
     * @return Mot de passe hashé ou chaîne vide si algorithme indisponible
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
            return password;
        }
    }
    
    // Attributs
    private int    id;       // Identifiant de l'utilisateur
    private String name;     // Nom de l'utilisateur
    private String password; // Mot de passe hashé de l'utilisateur
    
    // Salles de l'utilisateur
    private final ArrayList<Room> rooms = new ArrayList<>();
    
    /**
     * Création d'un nouvel utilisateur.
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe hashé de l'utilisateur
     */
    public User(String name, String password)
    {
        this.id       = User.UNREGISTERED_ID;
        this.name     = name;
        this.password = password;
    }
    
    /**
     * Instanciation d'un utilisateur à partir d'un résultat de requête.
     * @param set ResultSet d'une requête
     * @throws java.sql.SQLException Champ(s) absent(s) du résultat
     */
    public User(ResultSet set) throws SQLException
    {
        this.id       = set.getInt   ("id");
        this.name     = set.getString("name");
        this.password = set.getString("password");
    }

    /**
     * Retourne l'identifiant de l'utilisateur.
     * @return Identifiant de l'utilisateur
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * Indique si l'utilisateur est enregistré.
     * @return Booléen indiquant si l'utilisateur est enregistré
     */
    public boolean isSaved()
    {
        return this.id == User.UNREGISTERED_ID;
    }

    /**
     * Retourne le nom de l'utilisateur.
     * @return Nom de l'utlisateur
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Retourne le mot de passe hashé de l'utilisateur.
     * @return Mot de passe de l'utilisateur
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Modifie l'identifiant de l'utilisateur.
     * @param id Identifiant à utiliser
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
    
    /**
     * Liste des salles liées à l'utilisateur.
     * @return ArrayList des salles 
     */
    public synchronized ArrayList<Room> getRooms()
    {
        return this.rooms;
    }
    
}
