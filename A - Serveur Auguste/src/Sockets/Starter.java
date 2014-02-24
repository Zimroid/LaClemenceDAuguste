package Sockets;

import java.io.*;
import java.net.*;

public class Starter {
    public static ServerSocket ss = null;
    public static Thread t;

    public final static int PORT_ECOUTE = 9000;
    
    public static void main(String[] args) {
        // Ouverture du serveur au clients
        try {
            ss = new ServerSocket(PORT_ECOUTE);
            System.out.println("Le serveur est à l'écoute du port " + ss.getLocalPort());

            t = new Thread(new Server(ss));
            t.start();
        }
        catch (IOException e) {
            System.err.println("Le port " + ss.getLocalPort() + " est déjà utilisé !");
        }
    }
}