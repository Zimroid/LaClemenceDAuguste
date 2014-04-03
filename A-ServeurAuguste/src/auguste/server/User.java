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
import java.util.HashMap;
import org.apache.commons.codec.binary.Hex;
import org.java_websocket.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe représentant un utilisateur connecté au serveur et authentifié. Gère
 * les données relatives à cet utilisateur, ainsi que la socket avec laquelle il
 * est actuellement connecté. Contient la liste des salons auxquelles
 * l'utilisateur appartient.
 * 
 * Le mot de passe de l'utilisateur est toujours stocké hashé dans une instance
 * de User. Le hashage de mot de passe se fait via la méthode statique
 * hashPassword.
 * 
 * @author Lzard
 */
public class User
{
    // ID d'un utilisateur non-enregistré
    private final static int UNREGISTERED_ID = 0;
    
    /**
     * Retourne la chaine de caractères fournie en paramètre hashée avec
     * l'algorithme configuré.
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
            Log.debug(e);
            return password;
        }
    }
    
    // Attributs
    private int    id;       // Identifiant de l'utilisateur
    private String name;     // Nom de l'utilisateur
    private String password; // Mot de passe hashé de l'utilisateur
    
    // Socket du client de l'utilisateur
    private WebSocket socket = null;
    
    // Salons auxquels l'utilisateur appartient
    private final HashMap<Integer, Room> rooms = new HashMap<>();
    
    /**
     * Instanciation d'un nouvel utilisateur non-enregistré.
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
     * Ajoute au JSON fourni les informations de l'utilisateur.
     * @param json JSON à compléter
     */
    public void addUserInformations(JSONObject json)
    {
        try
        {
            json.put("user_id",   this.id);
            json.put("user_name", this.name);
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
    }
    
    /**
     * Ajoute au JSON fourni les paramètres de l'utilisateur.
     * @param json JSON à compléter
     */
    public void addUserParameters(JSONObject json)
    {
        try
        {
            json.put("user_favourite_pawns", "circle");
            json.put("user_favourite_color", "green");
        }
        catch (JSONException e)
        {
            Log.debug(e);
        }
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
     * Modifie l'identifiant de l'utilisateur.
     * @param id Nouvel identifiant
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * Indique si l'utilisateur est enregistré.
     * @return Utilisateur enregistré
     */
    public boolean isSaved()
    {
        return this.id != User.UNREGISTERED_ID;
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
     * Modifie le nom de l'utilisateur.
     * @param name Nouveau nom
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Retourne le mot de passe hashé de l'utilisateur.
     * @return Mot de passe hashé de l'utilisateur
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * Modifie le mot de passe hashé de l'utilisateur.
     * @param password Nouveau mot de passe
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    /**
     * Retourne la socket du client de l'utilisateur.
     * @return WebSocket de l'utilisateur
     */
    public WebSocket getSocket()
    {
        return this.socket;
    }
    
    /**
     * Modifie la socket du client de l'utilisateur.
     * @param socket Nouvelle socket
     */
    public void setSocket(WebSocket socket)
    {
        this.socket = socket;
    }
    
    /**
     * Liste des salons auxquels l'utilisateur appartient.
     * @return ArrayList des salons 
     */
    public synchronized HashMap<Integer, Room> getRooms()
    {
        return this.rooms;
    }
    
}
