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

import auguste.server.util.Configuration;
import auguste.server.util.Log;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe de lancement du serveur.
 * @author Lzard
 */
public class Main
{
    // Fichier de configuration
    private static final String CONFIGURATION_FILE = "properties.conf";
    
    /**
     * Point d'entrée de l'application. Charge la configuration, vérifie
     * la présence du driver JDBC et lance le serveur.
     * @param args Arguments de la commande
     */
    public static void main(String[] args)
    {
        try
        {
            // Chargement de la configuration
            Configuration.load(Main.CONFIGURATION_FILE);
            
            // Vérification de la présence du driver JDBC
            Class.forName(Configuration.get("db_driver"));
            
            // Vérification de la disponibilité de l'algorithme de hashage
            MessageDigest.getInstance(Configuration.get("hash_algorithm"));
            
            // Lancement
            Server.getInstance().start();
        }
        catch (IOException ex)
        {
            // Fichier de configuration inaccessible
            Log.error("Configuration file unavailable");
            Log.debug(ex.toString());
        }
        catch (ClassNotFoundException ex)
        {
            // Driver JDBC introuvable
            Log.error("JDBC driver unavailable");
            Log.debug(ex.toString());
        }
        catch (NoSuchAlgorithmException ex)
        {
            // Algorithme de hashage indisponible
            Log.error("Hash algorithme unavailable");
            Log.debug(ex.toString());
        }
    }
    
}
