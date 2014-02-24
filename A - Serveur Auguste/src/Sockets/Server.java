package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.json.*;

/**
 * Classe de controle des entrées au serveur
 * @author Vincent
 */

public class Server implements Runnable {
    private ServerSocket socketserver = null;
    private ArrayList<SocketHandler> socketList = null;
    public Thread t1;
    
    public Server (ServerSocket ss) {
	 socketserver = ss;
         socketList = new ArrayList<> ();
    }

    /**
     * Traitement des demandes d'accès au serveur
     */
    @Override
    public void run() {
	try {
            while(true) {
                // Réception des clients
                SocketHandler sh = new SocketHandler(socketserver.accept());
		socketList.add(sh);
		System.out.println("Nouveau client ...");

                // Création d'un gestionnaire pour traiter avec le client
                t1 = new Thread(sh);
                t1.start();
            }
        }
        catch (IOException e) {
            System.err.println("Erreur serveur lors de l'ajout d'un client ...");
	}
    }

    /**
     *  Gestionnaire de socket
     *  -> crée un thread pour traiter les messages reçu par la socket
     *  -> permet d'envoyer des messages depuis la socket
     */
    public class SocketHandler implements Runnable{
        private Socket s = null;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructeur du gestionnaire de la socket
         * @param s : socket à gérer
         */
        public SocketHandler (Socket s) {
            try {
                this.s = s;
                this.out = new PrintWriter(this.s.getOutputStream());
                this.in = new BufferedReader (new InputStreamReader (this.s.getInputStream()));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         *  Thread de traitement des informations reçues
         */
        @Override
        public void run() {
            try {
                while(true) {
                    String message = in.readLine();
                    if(message != null) {
                        // Traitement du message reçu
                        traitement(this, message);
                        
                        // Impression sur le serveur et broadcast du message (v. beta)
                        System.out.println(message);
                        broadcast(message);
                    }
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
        /**
         * Fonction permettant l'envoi d'un message
         * @param message : message à envoyer
         */
        public void send (String message) {
            this.out.println(message);
            this.out.flush();
        }
    }
    
    /**
     * Broadcast d'un message
     * @param message
     */
    public void broadcast (String message)
    {
        for (SocketHandler sh : socketList) {
            sh.send(message);
        }
    }
    
    /**
     * Fonction de traitement des messages recus
     * @param source : source du message, objet de type SocketHandler
     * @param message : donnée recue sérialisé au format JSON
     */
    public static void traitement(SocketHandler source,String message) {
        try {
            JSONObject jso = new JSONObject(message);
            String commande = (String) jso.get("command");
            System.out.println("Réception commande : " + commande);
        }
        catch (JSONException e) {
            System.out.println("Une erreur s'est produite durant le traitement d'un objet JSON :\n" + e.getMessage());
        }
    }
}