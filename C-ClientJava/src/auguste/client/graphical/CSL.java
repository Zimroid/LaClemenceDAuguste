/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.graphical;

import auguste.client.command.client.CommandClient;
import auguste.client.entity.ChatMessageReceived;
import auguste.client.entity.Client;
import auguste.client.entity.Game;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import org.json.JSONException;

/**
 * Cette classe s'occupe de l'affichage dans la console des données reçues
 * par le client
 * @author Evinrude
 */
public class CSL implements UpdateListener
{
    private final Client client;
    private boolean stop;
    
    /**
     *
     * @throws java.net.URISyntaxException
     */
    public CSL() throws URISyntaxException
    {
        this.client = Client.getInstance();
    }
    
    /**
     * Cette fonction permet de récupérer des commandes client depuis la console
     * @throws JSONException
     * @throws java.net.URISyntaxException
     */
    public void run() throws JSONException, URISyntaxException
    {
        stop = false;

        while(!stop)
        {
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            String[] words = line.split(" ");
            Map<String,String> command;
            switch(words[0])
            {
                case CommandClient.LOG_IN:
                    command = login(words);
                    break;
                case CommandClient.ACCOUNT_CREATE:
                    command = accountCreate(words);
                    break;
                case CommandClient.EXIT:
                    command = exit();
                    break;
                case CommandClient.CHAT_SEND:
                    command = msg(words);
                    break;
                case CommandClient.GAME_LIST:
                    command = listGame();
                    break;
                case CommandClient.GAME_CREATE:
                    command = gameCreate(words);
                    break;
                case CommandClient.GAME_JOIN:
                    command = gameJoin(words);
                    break;
                case CommandClient.GAME_LEAVE:
                    command = gameLeave(words);
                    break;
                case CommandClient.GAME_CONFIG:
                    command = gameConfig(words);
                    break;
                default:
                    command = null;
                    break;
            }
            this.client.sendCommand(command);
        }
    }
    
    private Map<String,String> gameCreate(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, args[0]);
        res.put(CommandClient.GAME_NAME, args[1]);
        
        return res;
    }
    
    private Map<String,String> login(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, args[0]);
        res.put(CommandClient.NAME, args[1]);
        res.put(CommandClient.PASSWORD, args[2]);
        
        return res;
    }
    
    private Map<String,String> accountCreate(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, args[0]);
        res.put(CommandClient.NAME, args[1]);
        res.put(CommandClient.PASSWORD, args[2]);
        
        return res;
    }
    
    private Map<String,String> gameJoin(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, args[0]);
        res.put(CommandClient.ROOM_ID, args[1]);
        
        return res;
    }
    
    private Map<String,String> exit()
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, "exit");
        return res;
    }
    
    private Map<String,String> msg(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        String message = "";
        
        for(int i = 2; i < args.length; i++)
        {
            message += args[i];
        }
        
        res.put(CommandClient.COMMAND, args[0]);
        res.put(CommandClient.ROOM_ID, args[1]);
        res.put(CommandClient.MESSAGE, message);
        
        return res;
    }
    
    private Map<String,String> gameLeave(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, "game_leave");
        res.put(CommandClient.ROOM_ID, args[1]);
        
        return res;
    }
    
    private Map<String,String> listGame()
    {
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, "game_list");
        
        return res;
    }
    
    /**
     * Cette fonction arrête le run de CSL
     * 
     */
    @Override
    public void stop()
    {
        this.stop = true;
    }

    /**
     * Se déclenche sur la réception d'un message de chat par le client
     */
    public void sendChat() 
    {
        Queue<ChatMessageReceived> allCMR = client.getChatMessageReceived();
        for(int i = 0; i<allCMR.size(); i++)
        {
            ChatMessageReceived cmr = allCMR.remove();
            
            String author = cmr.getAuthor();
            String message = cmr.getMessage();
            SimpleDateFormat date = new SimpleDateFormat("hh:mm:ss");
            String dateFormat = date.format(cmr.getDate());
            
            System.out.println("<<"+author+">>["+dateFormat+"]"+message);
        }
    }

    /**
     * Se déclenche sur réception d'une commande de type
     * confirmation de la part du serveur
     */
    public void confirmCommand() 
    {
        String confirm_msg = client.getConfirmMessage();
        System.out.println("Retour du serveur : " + confirm_msg);
    }

    /**
     * Se déclenche sur réception d'une liste de partie
     */
    public void gameAvailable() 
    {
        System.out.println("Liste des parties disponibles :");
        List<Game> games = client.getGameAvailable();
        for(Game g : games)
        {
            System.out.println(g);
        }
    }

    /**
     * Se déclenche sur connexion d'un joueur
     */
    public void logClient()
    {
        System.out.println("Bienvenue "+client.getUser().getName());
    }

    @Override
    public void chatUpdate() 
    {
        this.sendChat();
    }

    @Override
    public void userUpdate() 
    {
        this.logClient();
    }

    @Override
    public void createGameUpdate(int id) 
    {
        Game game = client.getGame(id);
        System.out.println("Partie "+game.getName()+" créée.");
        System.out.println("Id de la partie : "+game.getId());
    }

    @Override
    public void listGameUpdate() 
    {
        this.gameAvailable();
    }

    @Override
    public void confirmMessageUpdate() 
    {
        this.confirmCommand();
    }

    private Map<String, String> gameConfig(String[] words) 
    {
        Map<String,String> params = new HashMap<>();
        
        for(int i = 1; i<words.length; i++)
        {
            String[] param = words[i].split(":");
            params.put(param[0], param[1]);
        }
        
        Map<String,String> res = new HashMap<>();
        
        res.put(CommandClient.COMMAND, CommandClient.GAME_CONFIG);
        res.put(CommandClient.ROOM_ID, params.get(CommandClient.ROOM_ID));
        res.put(CommandClient.GAME_NAME, params.get(CommandClient.GAME_NAME));
        res.put(CommandClient.PLAYER_NUMBER, params.get(CommandClient.PLAYER_NUMBER));
        res.put(CommandClient.BOARD_SIZE, params.get(CommandClient.BOARD_SIZE));
        
        return res;
    }

    @Override
    public void gameTurnUpdate(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}