package auguste.client.entity;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.command.manager.CommandServerManager;
import auguste.client.engine.Battle;
import auguste.client.engine.Move;
import auguste.client.engine.Tenaille;
import auguste.client.interfaces.UpdateListener;
import auguste.client.reseau.ClientSocket;

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
    
    private List<Game> gameAvailable;
    private List<Game> games;
    private User currentUser;
    
    private UserInterfaceManager IUM;
    
    private String confirmMessage;
    
    private Client() throws URISyntaxException
    {
        this.socket = ClientSocket.getInstance();
        this.interfaces 	= new ArrayList<>();
        this.games 			= new ArrayList<>();
        
        this.IUM = UserInterfaceManager.getInstance();
        
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
    public Queue<ChatMessageReceived> getChatMessage(int id) 
    {
    	Queue<ChatMessageReceived> res = new LinkedList<>();
    	
    	this.IUM.addQueueMessageChat(id, res);
    	
        return res;
    }
    
    public Queue<Battle> getBattles(int id)
    {
    	Queue<Battle> res = new LinkedList<>();
    	
    	this.IUM.addQueueBattle(id, res);
    	
    	return res;
    }
    
    public Queue<Move> getMoves(int id)
    {
    	Queue<Move> res = new LinkedList<>();
    	
    	this.IUM.addQueueMove(id, res);
    	
    	return res;
    }
    
    public Queue<Tenaille> getTenailles(int id)
    {
    	Queue<Tenaille> res = new LinkedList<>();
    	
    	this.IUM.addQueueTenaille(id, res);
    	
    	return res;
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
        this.games.add(game);
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
        	System.out.println("test");
            this.removeGame(game.getId());
        }
        this.addGame(game);
    }
}