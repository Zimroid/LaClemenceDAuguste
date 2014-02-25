package Sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Vr4el / Zim
 * C'est ici que le serveur de calcul recevra et enverra les données
 * Grace à socketList on peut accéder d'ici à la totalité des clients actuellement connecté
 * C'est donc ici que seront traiter les info de SocketHandler
 * Et on enverra nos réponse au bon SocketHandler
 */
public final class Server implements Runnable
{
    private static volatile Server instance = null;
    private ServerSocket socketserver = null;
    private ArrayList<SocketHandler> socketList;
    public Thread t1;
    
    private Server (ServerSocket ss)
    {
	 this.socketserver = ss;
         this.socketList = new ArrayList<>();
    }
    
    /**
     * @param ss ServerSocket la socket du serveur
     * @return Une instance du serveur
     * Permet de retourner une instance du Serveur
     */
    public final static Server getInstance(ServerSocket ss)
    {
         if (Server.instance == null)
         {
            synchronized(Server.class) {
              if (Server.instance == null) {
                Server.instance = new Server(ss);
              }
            }
         }
         return Server.instance;
     }

    /**
     * Fonction qui se lance quand on fait un Server.start();
     */
    @Override
    public void run()
    {
        // Ici on tente de connecter des clients
	try
        {
            // On écoute en boucle si un client essais de se connecter
            while(true)
            {
                // On envois au client this pour qu'il puisse communiquer avec le serveur
                SocketHandler sh = new SocketHandler(socketserver.accept(), this);
                // Une fois le client accepté on sauvegarde sa connexion dans socketList
		socketList.add(sh);
		System.out.println("Nouveau client ...");
                
                // Si un nouveaux client à bien été connecté on lance un nouveau thread pour lui
                t1 = new Thread(sh);
                t1.start();
            }
        }
        catch (IOException e)
        {
            System.err.println("Erreur serveur lors de l'ajout d'un client ...");
	}
    }
    
    /**
     * Cette fonction permetra d'envoyer un message à l'ensemble des clients connecté
     * @param message String le message à broadcaster
     */
    public void broadcast (String message)
    {
        for(SocketHandler sh : socketList)
        {
            sh.send(message);
        }
    }
    
    /**
      * Fonction de traitement des messages recus
      * @param source : source du message, objet de type SocketHandler
      * @param message : donnée recue sérialisé au format JSON
      */
     public static void traitement(SocketHandler source, String message)
     {
         try
         {
             System.out.println("Récéption JSON : " + message);
             JSONObject jso = new JSONObject(message);
             String commande = (String) jso.get("command");
             System.out.println("Réception commande : " + commande);
         }
         catch(JSONException e)
         {
             System.out.println("Une erreur s'est produite durant le traitement d'un objet JSON :\n" + e.getMessage());
         }
     }
}