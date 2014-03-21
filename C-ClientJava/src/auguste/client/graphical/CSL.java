/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.graphical;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.entity.ChatMessageReceived;
import auguste.client.entity.Client;
import auguste.client.entity.Game;
import java.text.DateFormat;
import java.util.List;
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
     * @param client
     *      Le client de l'application
     */
    public CSL(Client client)
    {
        this.client = client;
    }
    
    /**
     * Cette fonction permet de récupérer des commandes client depuis la console
     * @throws JSONException
     */
    public void run() throws JSONException
    {
        stop = false;

        while(!stop)
        {
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            String[] words = line.split(" ");
            CommandClientManager.executeCommand(this.client, words);
        }
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
            DateFormat date = DateFormat.getDateInstance(DateFormat.HOUR_OF_DAY0_FIELD);
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
        System.out.println("Bienvenu "+client.getUser().getName());
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