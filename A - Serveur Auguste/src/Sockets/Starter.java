package Sockets;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Vr4el / Zim
 * Ceci est le main du serveur de calcul
 */
public class Starter
{
    // On déclare la socket et un thread et on initialise la socket à null
    public static ServerSocket ss = null;
    public static Thread t;

    public static void main(String[] args)
    {
        // On tente d'écouter sur un port (9000 pour l'instant)
        try
        {
            ss = new ServerSocket(9000);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());

            // Si on arrive effectivement à écouter le port on démarre le serveur dans un nouveau thread
            Server serv = Server.getInstance(ss);
            t = new Thread(serv);
            t.start();
        }
        catch (IOException e)
        {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
        
    }
}