package auguste.client.entity;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.command.manager.CommandServerManager;
import auguste.client.graphical.CSL;
import auguste.client.reseau.ClientSocket;
import java.net.URISyntaxException;
import java.util.List;
import org.json.JSONException;

/**
 * Classe qui contient les référence vers les différents modèles de l'application, 
 * c'est une pierre angulaire
 * @author Joel
 */
public class Client
{
        private final ClientSocket socket;
        private final CSL csl;
        
        private List<Game> game_available;
        private User current_user;
        
        public Client() throws URISyntaxException
        {
            this.socket = ClientSocket.getInstance();
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

        /**
         * Cette méthode lit un message du serveur et l'interprète.
         * @param param
         *      Chaine contenant le nom de la commande ainsi que les paramètres l'accompagant
         */
        public void messageServerReceive(String param)
        {
            CommandServerManager.executeCommand(this, param);
        }

        /**
         * Cette méthode contruit un message à envoyer à un serveur et l'envoie.
         * @param params
         *      Tableau de chaine contenant la commande à envoyer ainsi que les paramètre de cette dernière
         * @throws JSONException
         */
        public void messageServerSend(String[] params) throws JSONException
        {
            CommandClientManager.executeCommand(this, params);
        }
        
        public void onMessageFromInterfaceReceived()
        {
            
        }
        
        public List<Game> getGameAvailable()
        {
            return this.game_available;
        }
        
        public void setGameAvailable(List<Game> games)
        {
            this.game_available = games;
        }
        
        public User getUser()
        {
            return this.current_user;
        }
        
        public void setUser(User user)
        {
            this.current_user = user;
        }
}