/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.graphical;

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
                case "log_in":
                    command = login(words);
                    break;
                case "account_create":
                    command = accountCreate(words);
                    break;
                case "exit":
                    command = exit();
                    break;
                case "chat_send":
                    command = msg(words);
                    break;
                case "game_list":
                    command = listGame();
                    break;
                case "game_create":
                    command = gameCreate(words);
                    break;
                case "game_join":
                    command = gameJoin(words);
                    break;
                case "game_leave":
                    command = gameLeave(words);
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
        
        res.put("command", args[0]);
        res.put("game_name", args[1]);
        
        return res;
    }
    
    private Map<String,String> login(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", args[0]);
        res.put("login", args[1]);
        res.put("password", args[2]);
        
        return res;
    }
    
    private Map<String,String> accountCreate(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", args[0]);
        res.put("login", args[1]);
        res.put("password", args[2]);
        
        return res;
    }
    
    private Map<String,String> gameJoin(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", args[0]);
        res.put("game_id", args[1]);
        
        return res;
    }
    
    private Map<String,String> exit()
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", "exit");
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
        
        res.put("command", args[0]);
        res.put("room_id", args[1]);
        res.put("message", message);
        
        return res;
    }
    
    private Map<String,String> gameLeave(String[] args)
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", "game_leave");
        res.put("room_id", args[1]);
        
        return res;
    }
    
    private Map<String,String> listGame()
    {
        Map<String,String> res = new HashMap<>();
        
        res.put("command", "game_list");
        
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
    public void createGameUpdate() 
    {
        Game game = client.getCurrentGame();
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
}