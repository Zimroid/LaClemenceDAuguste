package console.client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTextArea;

/*
 * @author Vincent
 */

public class SocketHandler {
    private static final String ip = "127.0.0.1";
    private static int port = 9000;
    
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatWindow chat;

    // Constructeur
    SocketHandler (ChatWindow chat) {
        try {
            this.socket = new Socket(ip, port);
            this.out = new PrintWriter(this.socket.getOutputStream());
            this.in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            this.chat = chat;
            
            Reader r = new Reader();
            Thread t = new Thread(r);
            t.start();
            
        }
        catch (IOException e) { 
            System.out.println(e.getMessage());
            JTextArea showMessage = chat.getShowMessage();
            showMessage.append(e.getMessage());
        }
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
                        JTextArea showMessage = chat.getShowMessage();
                        showMessage.append(this.format(message) + "\r\n");
                    }
                }
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        public String format(String message) {
            message = message.replace("{", "");
            message = message.replace("}", "");
            message = message.replace("\"", "");
            String[] hash = message.split(",");
            
            ArrayList<String[]> data = new ArrayList<> ();
            for(String h : hash) {
                String[] temp = h.split(":");
                data.add(temp);
            }
            
            String newMess = data.get(1)[1] + " [" + data.get(2)[1] + ":" + data.get(2)[2] + "] : " + data.get(3)[1];
            return newMess;
        }
    }
}