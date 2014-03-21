package auguste.client.entity;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.command.manager.CommandServerManager;
import auguste.client.graphical.UpdateListener;
import auguste.client.reseau.ClientSocket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONException;

/**
 * Classe qui contient les référence vers les différents modèles de l'application, 
 * c'est une pierre angulaire
 * @author Joel
 */
public class Client
{
        private final ClientSocket socket;
        private final List<UpdateListener> interfaces;
        private static Client INSTANCE;
        
        private List<Game> gameAvailable;
        private User currentUser;
        private Queue<ChatMessageReceived> chatMessageReceived;
        private String confirmMessage;
        private Game currentGame;
        
        private Client() throws URISyntaxException
        {
            this.socket = ClientSocket.getInstance();
            this.interfaces = new ArrayList<>();
            this.chatMessageReceived = new LinkedList<>();
            this.currentGame = new Game();
            this.currentGame.setId(0);
        }
        
        public static Client getInstance() throws URISyntaxException
        {
            if(INSTANCE == null)
            {
                INSTANCE = new Client();
            }
            return INSTANCE;
        }
        
        public List<UpdateListener> getInterfaces()
        {
            return this.interfaces;
        }
        
        public ClientSocket getClientSocket()
        {
            return this.socket;
        }

        /**
         * Cette méthode lit un message du serveur et l'interprète.
         * @param param
         *      Chaine contenant le nom de la commande ainsi que les paramètres l'accompagant
         */
        public void messageServerReceive(String param)
        {
            CommandServerManager.executeCommand(INSTANCE, param);
        }

        /**
         * Cette méthode contruit un message à envoyer à un serveur et l'envoie.
         * @param params
         *      Tableau de chaine contenant la commande à envoyer ainsi que les paramètre de cette dernière
         * @throws JSONException
         * @throws java.net.URISyntaxException
         */
        public void messageServerSend(String[] params) throws JSONException, URISyntaxException
        {
            CommandClientManager.executeCommand(INSTANCE, params);
        }
        
        public void onMessageFromInterfaceReceived()
        {
            
        }
        
        public List<Game> getGameAvailable()
        {
            return this.gameAvailable;
        }
        
        public void setGameAvailable(List<Game> games)
        {
            this.gameAvailable = games;
        }
        
        public User getUser()
        {
            return this.currentUser;
        }
        
        public void setUser(User user)
        {
            this.currentUser = user;
        }

    /**
     * @return the chatMessageReceived
     */
    public Queue<ChatMessageReceived> getChatMessageReceived() {
        return chatMessageReceived;
    }

    /**
     * @param chatMessageReceived the chatMessageReceived to set
     */
    public void setChatMessageReceived(Queue<ChatMessageReceived> chatMessageReceived) {
        this.chatMessageReceived = chatMessageReceived;
    }

    public String getConfirmMessage() 
    {
        return this.confirmMessage;
    }
    
    public void setConfirmMessage(String confirmMessage)
    {
        this.confirmMessage = confirmMessage;
    }

    /**
     * @return the currentGame
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     * @param currentGame the currentGame to set
     */
    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}