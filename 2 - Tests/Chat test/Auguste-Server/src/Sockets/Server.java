package Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Vincent
 */

public class Server implements Runnable{
    private ServerSocket socketserver = null;
    private ArrayList<SocketHandler> socketList = null;
    public Thread t1;
    
    public Server (ServerSocket ss) {
	 socketserver = ss;
         socketList = new ArrayList<> ();
    }
	
    public void run() {
	try {
            while(true) {
                SocketHandler sh = new SocketHandler(socketserver.accept());
		socketList.add(sh);
		System.out.println("Nouveau client ...");

                t1 = new Thread(sh);
                t1.start();
            }
        }
        catch (IOException e) {
            System.err.println("Erreur serveur lors de l'ajout d'un client ...");
	}
    }
    
    public class SocketHandler implements Runnable{
        private Socket s = null;
        private BufferedReader in;
        private PrintWriter out;

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

        public void run() {
            try {
                while(true) {
                    String message = in.readLine();
                    if(message != null) {
                        System.out.println(message);
                        broadcast(message);
                    }
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void send (String message) {
            this.out.println(message);
            this.out.flush();
        }
    }
    
    public void broadcast (String message)
    {
        for (SocketHandler sh : socketList) {
            sh.send(message);
        }
    }
}