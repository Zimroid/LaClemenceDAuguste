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

package octavio.server;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import octavio.server.util.Configuration;
import octavio.server.util.Logger;

/**
 * Classe principale lançant le serveur.
 *
 * @author Lzard
 */
public class Launcher
{
    /**
     * Localisation du fichier de configuration du serveur. Le chemin est
     * relatif au répertoire d'exécution du programme.
     */
    private static final String CONFIGURATION_FILE = "properties.conf";

    /**
     * Point d'entrée de l'application. Charge les paramètres du fichier de
     * configurations et les arguments de la ligne de commande.
     *
     * Si le serveur est configuré pour fonctionner en ligne, la présence de
     * l'algorithme de hashage et du driver JDBC ainsi que la possibilité
     * d'établir une connexion à la base de donnée sont vérifiés. Le serveur est
     * ensuite instancié et lancé.
     *
     * En cas d'erreur pendant l'initialisation, un message de description est
     * affiché et le programme est terminé.
     *
     * @param args Arguments de la ligne de commande
     */
    public static void main(String[] args)
    {
        try
        {
            // Début de l'initialisation
            Logger.print("Beginning initialization...");

            // Chargement de la configuration si le fichier existe
            Configuration.load(Launcher.CONFIGURATION_FILE);

            // Modification des paramètres selon les arguments de la commande
            for (int i = 1; i < args.length; i++)
            {
                String[] arg = args[i].split("=");
                Configuration.set(arg[0], arg[1]);
            }

            // Vérification du mode hors-ligne, de l'algorithme de hashage, du driver JDBC et de la BDD si besoin
            if (Configuration.getBoolean("online", false))
            {
                MessageDigest.getInstance(Configuration.get("hash_algorithm", "SHA-1"));
                Class.forName(Configuration.get("db_driver", "com.mysql.jdbc.Driver"));
                Configuration.getDbConnection();
            }

            // Intialisation réussie, lancement du serveur
            Logger.print("Initialization complete");
            Server.getInstance().start();
        }
        catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | SQLException | RuntimeException exception)
        {
            Logger.printError(exception);
            Logger.printError("Initialization failed. Please solve the errors then restart the program.");
        }
    }
}
