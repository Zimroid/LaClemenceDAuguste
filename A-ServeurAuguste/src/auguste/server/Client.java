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

import auguste.server.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Hex;
import org.java_websocket.WebSocket;

/**
 * Classe représentant un client connecté au serveur.
 * @author Lzard
 */
public class Client
{   
    // Valeurs par défaut des champs d'un client non identifié
    public static final int    DEFAULT_ID       = 0;
    public static final String DEFAULT_NAME     = "Anonymous";
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
            Log.debug(ex.toString());
            return new String();
        }
    }
    
    // Attributs
    private int    id;        // ID de l'utilisateur
    private String name;      // Nom de l'utilisateur
    private String password;  // Mot de passe hashé de l'utilisateur
    
    // WebSocket du client
    private final WebSocket socket;
    
    // Salles auxquelles le client est affecté du client
    private final ArrayList<Room> rooms = new ArrayList<>();
    
    /**
     * Instanciation d'un utilisateur avec les valeurs données.
     * @param id       ID de l'utilisateur
     * @param name     Nom de l'utilisateur
     * @param password Mot de passe hashé de l'utilisateur
     * @param socket   Socket du client
     */
    public Client(int id, String name, String password, WebSocket socket)
    {
        this.id       = id;
        this.name     = name;
        this.password = password;
        this.socket   = socket;
    }
    
    /**
     * Instanciation d'un utilisateur à partir d'un résultat de requête.
     * @param set    ResultSet d'une requête
     * @param socket Socket du client
     * @throws java.sql.SQLException Erreur SQL
     */
    public Client(ResultSet set, WebSocket socket) throws SQLException
    {
        this.id       = set.getInt   ("id");
        this.name     = set.getString("name");
        this.password = set.getString("password");
        this.socket   = socket;
    }
    
    /**
     * Instanciation d'un client avec les valeurs par défaut
     * @param socket Socket du client
     */
    public Client(WebSocket socket)
    {
        this.id       = Client.DEFAULT_ID;
        this.name     = Client.DEFAULT_NAME;
        this.password = Client.DEFAULT_PASSWORD;
        this.socket   = socket;
    }
    
    /**
     * Envoi d'un message à la socket du client.
     * @param message Message à envoyer
     */
    public void send(String message)
    {
        this.socket.send(message);
    }
    
    /**
     * Indique si le client est identifié.
     * @return Booléen indiquant si le client est identifié
     */
    public boolean isLogged()
    {
        return this.id != Client.DEFAULT_ID;
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
    
    /**
     * Retourne la liste des salles dans laquelle est le client
     * @return Liste des salles du client
     */
    public ArrayList<Room> getRooms()
    {
        return this.rooms;
    }
    
}
