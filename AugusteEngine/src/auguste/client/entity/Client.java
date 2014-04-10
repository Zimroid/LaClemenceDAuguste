package auguste.client.entity;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.command.manager.CommandServerManager;
import auguste.client.interfaces.UpdateListener;
import auguste.client.reseau.ClientSocket;
import auguste.client.reseau.CommandTransfer;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    
    private volatile List<Game> gameAvailable;
    private volatile User currentUser;
    private volatile Queue<ChatMessageReceived> chatMessageReceived;
    private volatile String confirmMessage;
    private volatile List<Game> games;
    
    private CommandTransfer commandTransfer;
    
    private Client() throws URISyntaxException
    {
    	this.commandTransfer = new CommandTransfer(this);
        this.socket = ClientSocket.getInstance();
        this.socket.setCommandTransfer(this.commandTransfer);
        this.interfaces = new ArrayList<>();
        this.chatMessageReceived = new LinkedList<>();
        this.games = new ArrayList<>();
        this.socket.connect();
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
     * @param command
     *      Map qui contient au moins le nom de la commande et les paramètres associés
     * @throws JSONException
     * @throws java.net.URISyntaxException
     */
    public void sendCommand(Map<String,?> command) throws JSONException, URISyntaxException
    {
        CommandClientManager.executeCommand(INSTANCE, command);
        System.out.println(command.get("command"));
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
    public List<Game> getGames() {
        return this.games;
    }
    
    public void addGame(Game game)
    {
        this.gameAvailable.add(game);
    }
    
    public void removeGame(int id)
    {
        this.games.remove(this.getGame(id));
    }
    
    public Game getGame(int id)
    {
        Game res = null;
        
        for(Game game : this.games)
        {
            if(game.getId() == id)
            {
                res = game;
            }
        }
        
        return res;
    }
    
    public void updateGame(Game game)
    {
        if(this.getGame(game.getId()) != null)
        {
            this.removeGame(game.getId());
        }
        this.addGame(game);
    }
}