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
import java.io.IOException;

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
     * @throws java.io.IOException Fichier de configuration absent
     * @throws java.lang.ClassNotFoundException Driver JDBC absent
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        // Chargement de la configuration
        Configuration.load(Main.CONFIGURATION_FILE);
        
        // Vérification de la présence du driver JDBC
        Class.forName(Configuration.get("db_driver"));
        
        // Lancement
        Server.getInstance().start();
    }
    
}
