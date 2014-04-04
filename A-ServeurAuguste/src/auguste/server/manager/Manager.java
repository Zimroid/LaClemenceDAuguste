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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe abstraite des gestionnaires de données à faire persister. Utilise une
 * connexion à la base de données pour effectuer des requêtes dans le but de
 * faire persister un type d'objet précis.
 * 
 * @author Lzard
 */
public abstract class Manager
{
    // Connexion à la base de données
    private final Connection connection;
    
    /**
     * Initialisation de la connexion à la base de données. Le gestionnaire
     * utilisera la connexion fournie.
     * @param connection Connexion à la base de données
     */
    public Manager(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * Prépare une requête à la base de donnée connectée.
     * @param query Requête à préparer
     * @return PreparedStatement de la requête
     * @throws SQLException Erreur dans la requête SQL
     */
    protected PreparedStatement query(String query) throws SQLException
    {
        return this.connection.prepareStatement(query);
    }
    
    /**
     * Prépare une requête avec récupération des clés identifiantes générées.
     * @param query         Requête à préparer
     * @param generatedKeys Retourner les clés identifiantes ?
     * @return PreparedStatement de la requête
     * @throws SQLException Erreur dans la requête SQL
     */
    protected PreparedStatement query(String query, boolean generatedKeys) throws SQLException
    {
        if (generatedKeys) return this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        else               return this.query(query);
    }
    
}
