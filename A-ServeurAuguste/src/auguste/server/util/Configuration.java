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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe utilitaire permettant de charger et de lire la configuration du
 * serveur. La configuration est d'abord chargée via la méthode statique load,
 * puis lue avec les méthodes get, getBoolean, getInt et getLong.
 * @author Lzard
 */
public class Configuration
{
    // Properties de la configuration du serveur
    private static final Properties PROPERTIES = new Properties();
    
    /**
     * Charge la configuration de base du serveur depuis le fichier de
     * configuration spécifié.
     * @param file Chemin du fichier de configuration
     * @throws java.io.FileNotFoundException Fichier non trouvé
     * @throws java.io.IOException           Fichier illisible
     */
    public static void load(String file) throws FileNotFoundException, IOException
    {
        try (FileInputStream stream = new FileInputStream(file))
        {
            Configuration.PROPERTIES.load(stream);
        }
    }
    
    /**
     * Renvoie le paramètre demandé sous forme de chaîne de caractères.
     * @param key Clé du paramètre
     * @return Valeur correspondant à la clé ou chaîne vide si clé inconnue
     */
    public static String get(String key)
    {
        return Configuration.PROPERTIES.getProperty(key, "");
    }
    
    /**
     * Renvoie le paramètre demandé sous forme de booléen.
     * @param key Clé du paramètre
     * @return Valeur correspondant à la clé ou false si clé inconnue
     */
    public static boolean getBoolean(String key)
    {
        return Configuration.PROPERTIES.getProperty(key, "false").equals("true");
    }
    
    /**
     * Renvoie le paramètre demandée sous forme d'entier.
     * @param key Clé de la configuration
     * @return Valeur correspondant à la clé ou 0 si clé inconnue
     */
    public static int getInt(String key)
    {
        return Integer.valueOf(Configuration.PROPERTIES.getProperty(key, "0"));
    }
    
    /**
     * Renvoie le paramètre demandée sous forme d'entier long.
     * @param key Clé de la configuration
     * @return Valeur correspondant à la clé ou 0 si clé inconnue
     */
    public static long getLong(String key)
    {
        return Long.valueOf(Configuration.PROPERTIES.getProperty(key, "0"));
    }
    
}
