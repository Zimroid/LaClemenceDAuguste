package Main;

import Sockets.Server;
import Models.Jdbc;
import Models.UserManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Vr4el / Zim
 * Ceci est le main du serveur de calcul
 */
public class Starter
{
    // Connexion à la BDD
    public final static String BDD_URL = "jdbc:mysql://localhost/auguste";
    public final static String BDD_LOGIN = "root";
    public final static String BDD_PWD = "";
    public static Jdbc jdbc;
    
    
    // On déclare la socket et un thread et on initialise la socket à null
    public static ServerSocket ss = null;
    public static Thread t;

    public static void main(String[] args)
    {
        // Connexion à la BDD
        try
        {
            jdbc = new Jdbc(BDD_URL, BDD_LOGIN, BDD_PWD);
            UserManager usm = new UserManager(jdbc);
        }
        catch (Exception e)
        {
            System.err.println("\nErreur lors de la connexion à la BDD :\n" + e.getMessage());
        }
        
        
        // On tente d'écouter sur un port (9000 pour l'instant)
        try
        {
            ss = new ServerSocket(9000);
            System.out.println("\nLe serveur est à l'écoute du port " + ss.getLocalPort());

            // Si on arrive effectivement à écouter le port on démarre le serveur dans un nouveau thread
            Server serv = Server.getInstance(ss);
            t = new Thread(serv);
            t.start();
        }
        catch (IOException e)
        {
            System.err.println("\nLe port " + ss.getLocalPort() + " est déjà utilisé !");
        }
        
    }
}