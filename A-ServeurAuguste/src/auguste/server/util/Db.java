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

package auguste.server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire de connexion à la base de données.
 * @author Lzard
 */
public class Db
{
    /**
     * Ouvre et retourne une connexion à la base de données. Désactive le commit
     * automatique.
     * @return Une instance de Connection ou null en cas d'erreur
     * @throws java.sql.SQLException Erreur SQL
     */
    public static Connection open() throws SQLException
    {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + Configuration.get("db_host") + ":" + Configuration.get("db_port") + "/" + Configuration.get("db_name"),
                Configuration.get("db_login"),
                Configuration.get("db_password")
        );
        connection.setAutoCommit(false);
        Log.debug("Connection to database");
        return connection;
    }
    
    /**
     * Commit des modifications etemerture d'une connexion avec la base de
     * données.
     * @param connection Connexion à fermer
     * @throws SQLException Erreur SQL
     */
    public static void close(Connection connection) throws SQLException
    {
        connection.commit();
        connection.close();
    }
    
}
