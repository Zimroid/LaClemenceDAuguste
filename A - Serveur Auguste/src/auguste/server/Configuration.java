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

import java.util.Properties;

/**
 * Classe utilitaire permettant de charger la configuration du serveur.
 * @author Lzard
 */
public class Configuration
{
	// Collection de la configuration du serveur
	private static final Properties CONFIGURATION = new Properties();
	
	/**
	 * Charge la configuration de base du serveur
	 */
	public static void load()
	{
		// Configuration du serveur
		Configuration.CONFIGURATION.setProperty("server_host_name", "localhost");
		Configuration.CONFIGURATION.setProperty("server_host_port", "9000");

		// Configuration de la connexion à la base de données
		Configuration.CONFIGURATION.setProperty("db_host",     "localhost");
		Configuration.CONFIGURATION.setProperty("db_port",     "3306");
		Configuration.CONFIGURATION.setProperty("db_name",     "auguste");
		Configuration.CONFIGURATION.setProperty("db_login",    "root");
		Configuration.CONFIGURATION.setProperty("db_password", "");
	}
	
	/**
	 * Renvoie la configuration demandée.
	 * @param key Clé de la configuration
	 * @return Valeur correspondant à la clé, chaîne vide si clé inconnue
	 */
	public static String get(String key)
	{
		return Configuration.CONFIGURATION.getProperty(key, "");
	}
	
	/**
	 * Renvoie la configuration demandée sous forme d'entier.
	 * @param key Clé de la configuration
	 * @return Valeur correspondant à la clé ou 0 si clé inconnue
	 */
	public static int getInt(String key)
	{
		return Integer.valueOf(Configuration.CONFIGURATION.getProperty(key, "0"));
	}
}
