/*
 * Copyright 2014 Lzard.
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

/**
 *
 * @author Lzard
 */
public class Manager
{
	// Connexion à la base de données
	private final Connection connection;
	
	/**
	 * Constructeur. Initialisation de la connexion à la base de données.
	 * @param connection Connexion à la base de données
	 */
	public Manager(Connection connection)
	{
		this.connection = connection;
	}

	/**
	 * Retourne la connexion à la base de données.
	 * @return Connexion à la base de données
	 */
	public Connection getConnection()
	{
		return connection;
	}
	
}