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

package octavio.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe utilitaire permettant de charger et de lire la configuration du
 * serveur. La configuration est d'abord chargée via la méthode statique load,
 * puis lue avec les méthodes get. Si besoin, la méthode set permet de modifier
 * à la volée ces paramètres.
 *
 * @author Lzard
 */
public class Configuration
{
    /**
     * Paramètres de la configuration.
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * Indique si le serveur fonctionne en mode debug.
     */
    private static boolean debug = true;

    /**
     * Charge la configuration de base du serveur depuis le fichier de
     * configuration spécifié. Identifie également si le serveur fonctionne en
     * mode débug.
     *
     * @param file Chemin du fichier de configuration
     *
     * @throws FileNotFoundException Fichier non trouvé
     * @throws IOException           Fichier illisible
     */
    public static void load(String file) throws FileNotFoundException, IOException
    {
        try (FileInputStream stream = new FileInputStream(file))
        {
            Configuration.PROPERTIES.load(stream);
        }

        Configuration.debug = Configuration.getBoolean("debug", false);
    }

    /**
     * Ajoute ou modifie une propriété de la configuration du serveur.
     *
     * @param key   Clé de la configuration à modifier
     * @param value Valeur de la configuration à modifier
     */
    public static void set(String key, String value)
    {
        Configuration.PROPERTIES.setProperty(key, value);
    }

    /**
     * Retourne le paramètre correspondant à la clé donnée.
     *
     * @param key          Clé du paramètre
     * @param defaultValue Valeur renvoyée si la clé est absente
     *
     * @return Valeur du paramètre sous forme de chaîne ou valeur par défaut
     */
    public static String get(String key, String defaultValue)
    {
        if (Configuration.PROPERTIES.containsKey(key))
        {
            return Configuration.PROPERTIES.getProperty(key);
        }
        else
        {
            Logger.printError("Configuration: Absent key " + key);
            return defaultValue;
        }
    }

    /**
     * Retourne le paramètre demandé sous forme de booléen.
     *
     * @param key          Clé du paramètre
     * @param defaultValue Valeur renvoyée si la clé est absente
     *
     * @return Valeur du paramètre sous forme de booléen ou valeur par défaut
     */
    public static boolean getBoolean(String key, boolean defaultValue)
    {
        return Configuration.get(
                key,
                String.valueOf(defaultValue)
        ).equals("true");
    }

    /**
     * Retourne le paramètre demandé sous forme d'entier.
     *
     * @param key          Clé de la configuration
     * @param defaultValue Valeur renvoyée si la clé est absente
     *
     * @return Valeur du paramètre sous forme d'entier ou valeur par défaut
     */
    public static int getInt(String key, int defaultValue)
    {
        return Integer.valueOf(
                Configuration.get(
                        key,
                        String.valueOf(defaultValue)
                )
        );
    }

    /**
     * Retourne le paramètre demandé sous forme d'entier long.
     *
     * @param key          Clé de la configuration
     * @param defaultValue Valeur renvoyée si la clé est absente
     *
     * @return Valeur du paramètre sous forme d'entier long ou valeur par défaut
     */
    public static long getLong(String key, long defaultValue)
    {
        return Long.valueOf(
                Configuration.get(
                        key,
                        String.valueOf(defaultValue)
                )
        );
    }

    /**
     * Retourne si le serveur fonctionne en mode debug ou non.
     *
     * @return Serveur en mode debug ou non
     */
    public static boolean isDebugging()
    {
        return Configuration.debug;
    }

    /**
     * Ouvre et retourne une connexion à la base de données. La fonctionnalité
     * de commit automatique de la connexion ouverte est désactivée.
     *
     * @return Une instance de Connection
     *
     * @throws SQLException Erreur SQL
     */
    public static Connection getDbConnection() throws SQLException
    {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://" + Configuration.get("db_host", "localhost") + ":"
                + Configuration.get("db_port", "3306")
                + "/" + Configuration.get("db_name", "octaviodb"),
                Configuration.get("db_login", "root"),
                Configuration.get("db_password", "")
        );

        connection.setAutoCommit(false);

        return connection;
    }
}
