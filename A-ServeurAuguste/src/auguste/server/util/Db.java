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
	 * Ouvre et retourne une connexion à la base de données.
	 * @return Une instance de Connection ou null en cas d'erreur
	 */
	public static Connection open()
	{
		// Vérification du driver et connexion à la base
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://" + Configuration.get("db_host") + ":" + Configuration.get("db_port") + "/" + Configuration.get("db_name"),
					Configuration.get("db_login"),
					Configuration.get("db_password")
			);
			connection.setAutoCommit(false);
			return connection;
		}
		catch (SQLException ex)
		{
			// Erreur de base de données
			Log.error("Unable to connect to database: " + ex);
			return null;
		}
		catch (ClassNotFoundException ex)
		{
			// Driver JDBC absent
			Log.error("Missing JDBC MySQL Driver: " + ex);
			return null;
		}
	}
	
	/**
	 * Femerture d'une connexion avec la base de données.
	 * @param connection Connexion à fermer
	 * @throws SQLException
	 */
	public static void close(Connection connection) throws SQLException
	{
		// Commit et fermeture de la connexion
		connection.commit();
		connection.close();
	}
}