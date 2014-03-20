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
 * Classe abstraite des Managers de données.
 * @author Lzard
 */
public abstract class Manager
{
    // Connexion à la base de données
    private final Connection connection;
    
    /**
     * Initialisation de la connexion à la base de données.
     * @param connection Connexion à la base de données
     */
    public Manager(Connection connection)
    {
        this.connection = connection;
    }

    /**
     * Prépare une requête à la connexion.
     * @param query Requête demandée
     * @return PreparedStatement de la requête
     * @throws java.sql.SQLException Erreur SQL
     */
    public PreparedStatement query(String query) throws SQLException
    {
        return this.connection.prepareStatement(query);
    }
    
    /**
     * Prépare une requête avec récupération des identifiants générés.
     * @param query         Requête demandée
     * @param generatedKeys Retourner les identifiants ?
     * @return PreparedStatement de la requête
     * @throws SQLException Erreur SQL
     */
    public PreparedStatement query(String query, boolean generatedKeys) throws SQLException
    {
        if (generatedKeys) return this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        else               return this.query(query);
    }
    
}
