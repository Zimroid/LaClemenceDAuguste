package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Vr4el / Zim
 * Ici on gère un client connecté
 * On peut recevoir des info et on peut en envoyer
 */
public class SocketHandler implements Runnable 
{
    private Socket s = null;
    private BufferedReader in;
    private PrintWriter out;
    private Server ss;

    public SocketHandler (Socket s, Server ss)
    {
        // On définie les points d'entrée et sortie du client
        try
        {
            this.s = s;
            this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
            this.out = new PrintWriter(this.s.getOutputStream());
            this.ss = ss;
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Fonction qui se lance quand on fait un Server.start();
     */
    @Override
    public void run()
    {
        // Ici on tente de lire les messages venant du client
        try
        {
            // On écoute en boucle le client
            while(true)
            {
                String message = in.readLine();
                if(message != null)
                {
                    // Traitements du serveur
                    Server.traitement(this, message);
                    
                    // Broadcast (v. beta)
                    System.out.println(message);
                    ss.broadcast(message);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param message String le message à envoyer au client
     * Permet d'envoyer un message à un client
     */
    public void send (String message)
    {
        this.out.println(message);
        this.out.flush();
    }
}