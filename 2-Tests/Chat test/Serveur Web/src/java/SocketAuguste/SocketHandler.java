package SocketAuguste;

import WebSocketClient.JsonObjCust;
import WebSocketClient.JsonObjCustDecoder;
import WebSocketClient.JsonObjCustEncoder;
import WebSocketClient.WSEndpoint;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;

/*
 * @author Vincent
 */

public class SocketHandler {
    private static SocketHandler sockHan = null;
    private static final String ip = "127.0.0.1";
    private static final int port = 9000;
    
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    // Constructeur
    private SocketHandler (String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
        }
        catch (IOException e) { }
    }
    
    // Crée ou récupère le SOcketHandler (singleton)
    public static SocketHandler getSocket () {
        if(sockHan == null || socket.isClosed()) {
            sockHan = new SocketHandler(ip, port);
            System.out.println("Nouveau Socket Handler");
            
            Reader r = sockHan.new Reader();
            Thread t = new Thread(r);
            t.start();
        }
        return sockHan;
    }
  
    // Envoi un message
    public void sendMessage (String message) {
        out.println(message);
        out.flush();
    }
    
    // Fermeture de la connexion
    public void closeConnection () throws IOException {
        this.socket.close();
    }
    
    // Sous classe de lecture des entrées
    private class Reader implements Runnable{
        public Reader () {
        }

        public void run() {
            try {
                while(!socket.isClosed()) {
                    String message = in.readLine();
                    if(message != null)
                    {
                        System.out.println("Réponse de serveur auguste : " + message);
                        
                        try {
                            JsonObjCust obj = JsonObjCustDecoder.speDecode(message);
                            WSEndpoint.broadcast(obj);
                        }
                        catch (EncodeException ex) {
                            System.out.println(ex.getMessage());
                        }
                        catch (DecodeException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}