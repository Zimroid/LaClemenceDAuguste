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
    
    /**
     * @throws URISyntaxException
     */
    private Client() throws URISyntaxException
    {
        this.socket = ClientSocket.getInstance();
        this.interfaces 	= new ArrayList<>();
        this.games 			= new ArrayList<>();
        
        this.IUM = UserInterfaceManager.getInstance();
        
        this.socket.connect();
    }
    
    /**
     * @return L'instance courante du client.
     * @throws URISyntaxException
     */
    public static Client getInstance() throws URISyntaxException
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Client();
        }
        return INSTANCE;
    }
    
    /**
     * @return La liste des interface utilisateurs qui utilisent le client actuellement.
     */
    public List<UpdateListener> getInterfaces()
    {
        return this.interfaces;
    }
    
    /**
     * @return La socket utilisée pour communiquer avec le serveur.
     */
    public ClientSocket getClientSocket()
    {
        return this.socket;
    }

    /**
     * Cette méthode lit un message du serveur et l'interprète.
     * @param param
     *      Chaine contenant le nom de la commande ainsi que les paramètres l'accompagant
     * @throws URISyntaxException 
     */
    public void messageServerReceive(String param) throws URISyntaxException
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
    
    /**
     * @return La liste des partie disponibles sur le serveur.
     */
    public List<Game> getGameAvailable()
    {
        return this.gameAvailable;
    }
    
    /**
     * @param games La liste des partie disponibles sur le serveur.
     */
    public void setGameAvailable(List<Game> games)
    {
        this.gameAvailable = games;
    }
    
    /**
     * @return L'utilisateur courant.
     */
    public User getUser()
    {
        return this.currentUser;
    }
    
    /**
     * @param user L'utilisateur courant.
     */
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
    
    /**
     * @param id L'identifiant de la bataille à récupérer
     * @return La bataille voulue.
     */
    public Queue<Battle> getBattles(int id)
    {
    	Queue<Battle> res = new LinkedList<>();
    	
    	this.IUM.addQueueBattle(id, res);
    	
    	return res;
    }
    
    /**
     * @param id L'identifiant du mouvement à récupérer.
     * @return Le mouvement voulu.
     */
    public Queue<Move> getMoves(int id)
    {
    	Queue<Move> res = new LinkedList<>();
    	
    	this.IUM.addQueueMove(id, res);
    	
    	return res;
    }
    
    /**
     * @param id L'identifiant de la tenaille à récupérer.
     * @return La tenaille voulue.
     */
    public Queue<Tenaille> getTenailles(int id)
    {
    	Queue<Tenaille> res = new LinkedList<>();
    	
    	this.IUM.addQueueTenaille(id, res);
    	
    	return res;
    }

    /**
     * @return Le message de confirmation.
     */
    public String getConfirmMessage() 
    {
        return this.confirmMessage;
    }
    
    /**
     * @param confirmMessage Le message de confirmation.
     */
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
    
    /**
     * @param game La partie à ajouter au client.
     */
    public void addGame(Game game)
    {
        this.games.add(game);
    }
    
    /**
     * @param id La partie à retirer du client.
     */
    public void removeGame(int id)
    {
        this.games.remove(this.getGame(id));
    }
    
    /**
     * @param id L'identifiant de la partie qu'on veut récupérer.
     * @return La partie voulue.
     */
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
    
    /**
     * @param game La partie à actualiser.
     */
    public void updateGame(Game game)
    {
        if(this.getGame(game.getId()) != null)
        {
            this.removeGame(game.getId());
        }
        this.addGame(game);
    }
    
    /**
     * @param ui L'interface à ajouter au client.
     */
    public void addInterface(UpdateListener ui)
    {
    	this.interfaces.add(ui);
    }
}