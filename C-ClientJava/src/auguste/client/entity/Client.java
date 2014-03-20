package auguste.client.entity;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.command.manager.CommandServerManager;
import auguste.client.graphical.CSL;
import auguste.client.reseau.ClientSocket;
import java.net.URISyntaxException;
import org.json.JSONException;

public class Client
{
        private final ClientSocket socket;
        private final CSL csl;
        
        public Client() throws URISyntaxException
        {
            this.socket = new ClientSocket();
            this.csl = new CSL(this);
        }
        
        public CSL getCSL()
        {
            return this.csl;
        }
        
        public ClientSocket getClientSocket()
        {
            return this.socket;
        }
        
        public void runCSL() throws JSONException
        {
            this.csl.run();
        }        
        
        // Réception d'un message à traiter
        public void messageServerReceive(String param)
        {
            CommandServerManager.executeCommand(param);
        }
        
        // Message à envoyer au serveur
        public void messageServerSend(String[] params) throws JSONException
        {
            CommandClientManager.executeCommand(this, params);
        }
        
        // 
        public void onMessageFromInterfaceReceived()
        {
            
        }
}