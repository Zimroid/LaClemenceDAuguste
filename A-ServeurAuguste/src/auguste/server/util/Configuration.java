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
 * serveur.
 * @author Lzard
 */
public class Configuration
{
    // Properties de la configuration du serveur
    private static final Properties CONFIGURATION = new Properties();
    
    /**
     * Charge la configuration de base du serveur.
     * @param file Chemin du fichier de configuration
     * @throws java.io.FileNotFoundException Fichier non trouvé
     * @throws java.io.IOException           Fichier illisible
     */
    public static void load(String file) throws FileNotFoundException, IOException
    {
        // Chargement de la configuration depuis un fichier
        try (FileInputStream stream = new FileInputStream(file))
        {
            Configuration.CONFIGURATION.load(stream);
            stream.close();
        }
    }
    
    /**
     * Renvoie la configuration demandée sous forme de chaîne de caractères.
     * @param key Clé de la configuration
     * @return Valeur correspondant à la clé ou chaîne vide si clé inconnue
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
