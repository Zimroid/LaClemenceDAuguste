package Sockets;

import Models.Jdbc;
import Models.User;
import Models.UserManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Vr4el / Zim
 * C'est ici que le serveur de calcul recevra et enverra les données
 * Grace à socketList on peut accéder d'ici à la totalité des clients actuellement connecté
 * C'est donc ici que seront traiter les info de SocketHandler
 * Et on enverra nos réponse au bon SocketHandler
 */
public final class Server implements Runnable
{
    private static volatile Server instance = null;
    private ServerSocket socketserver = null;
    private static ArrayList<SocketHandler> socketList;
    private static ArrayList<User> usersConnected;
    public Thread t1;
    //public static UserManager usm;
    public static Jdbc jdbc;
    
    private Server (ServerSocket ss, Jdbc jdbc)
    {
	 this.socketserver = ss;
         //socketList est destiné à être supprimé
         this.socketList = new ArrayList<>();
         //userConnected dois remplacer socketList... Elle contient la liste des utilisateurs connécté et leurs socket associé !
         this.usersConnected = new ArrayList<>();
         //une liste de parties devrais être ajouté
         this.jdbc = jdbc;
    }
    
    /**
     * @param ss ServerSocket la socket du serveur
     * @param usm UserManager pour gérer la BDD
     * @return Une instance du serveur
     * Permet de retourner une instance du Serveur
     */
    public final static Server getInstance(ServerSocket ss, Jdbc jdbc)
    {
         if (Server.instance == null)
         {
            synchronized(Server.class) {
              if (Server.instance == null) {
                Server.instance = new Server(ss, jdbc);
              }
            }
         }
         return Server.instance;
     }

    /**
     * Fonction qui se lance quand on fait un Server.start();
     */
    @Override
    public void run()
    {
        // Ici on tente de connecter des clients
	try
        {
            // On écoute en boucle si un client essais de se connecter
            while(true)
            {
                // On envois au client this pour qu'il puisse communiquer avec le serveur
                SocketHandler sh = new SocketHandler(socketserver.accept(), this);
                // Une fois le client accepté on sauvegarde sa connexion dans socketList (On la sauvegarde une fois qu'il c'est identifié)
		//socketList.add(sh);
		System.out.println("Nouveau client ...");
                
                // Si un nouveaux client à bien été connecté on lance un nouveau thread pour lui
                t1 = new Thread(sh);
                t1.start();
            }
        }
        catch (IOException e)
        {
            System.err.println("Erreur serveur lors de l'ajout d'un client ...");
	}
    }
    
    /**
     * Cette fonction permetra d'envoyer un message à l'ensemble des clients connecté
     * @param message String le message à broadcaster
     */
    public static void broadcast (String message)
    {
        for(SocketHandler sh : socketList)
        {
            sh.send(message);
        }
    }
    
    /**
      * Fonction de traitement des messages recus
      * @param source : source du message, objet de type SocketHandler
      * @param message : donnée recue sérialisé au format JSON
      */
    public static void traitement(SocketHandler source, String message)
    {
        try
        {
            System.out.println("Récéption JSON : " + message);
            JSONObject jso = new JSONObject(message);
            String commande = (String) jso.get("command");
            System.out.println("Réception commande : " + commande);
            
            if(commande.equals("chat"))
            {
                String msg = (String) jso.get("message");
                for(User u : usersConnected)
                {
                    if(u.getSocket().equals(source.toString()))
                    {
                        broadcast("{\"command\":\"chat\",\"pseudo\":\"" + u.getLogin() + "\",\"message\":\"" + msg + "\"}");
                    }
                }
            }
            // Nouvelle commande !
            else if(commande.equals("create_account"))
            {
                String login = (String) jso.get("login");
                String password = (String) jso.get("password");
                try
                {
                    UserManager usm = new UserManager(jdbc);
                    if(!usm.existUser(login))
                    {
                        usm.createUser(login, password);
                    }
                    else
                    {
                        source.send("{\"command\":\"chat\",\"pseudo\":\"[Serveur]\",\"message\":\"Se pseudo n\'est pas disponible :(\"}");
                    }
                }
                catch(Exception e)
                {}
            }
            else if(commande.equals("game_addbot"))
            {

            }
            else if(commande.equals("game_create"))
            {

            }
            else if(commande.equals("game_config"))
            {

            }
            else if(commande.equals("game_join"))
            {

            }
            else if(commande.equals("game_kick"))
            {

            }
            else if(commande.equals("game_leave"))
            {

            }
            else if(commande.equals("game_list"))
            {

            }
            else if(commande.equals("game_start"))
            {

            }
            else if(commande.equals("log_in"))
            {
                String login = (String) jso.get("login");
                String password = (String) jso.get("password");
                // Hachage du mot de passe
                try
                {
                    MessageDigest cript = MessageDigest.getInstance("SHA-1");
                    cript.reset();
                    cript.update(password.getBytes("utf8"));
                    password = new String(Hex.encodeHex(cript.digest()));
                }
                catch(Exception e)
                {}
                // Tentative de connexion
                try
                {
                    UserManager usm = new UserManager(jdbc);
                    // Si les info sont correcte
                    if(usm.existUser(login) && usm.getUser(login).getPwd().equals(password))
                    {
                        //Si la source est déjà dans la liste de socket on ne l'ajoute pas
                        if(!socketList.contains(source))
                        {
                            socketList.add(source);
                        }
                        
                        //On verifie si un client est déjà connecté
                        //(On peut immaginer qu'un même client essai de se connecter à 2 comptes en même temps !
                        //Se qu'on veut empecher ici
                        boolean containUserSocket = false;
                        for(User user : usersConnected)
                        {
                            if(user.getSocket().equals(source))
                            {
                                containUserSocket = true;
                            }
                        }
                        //Si la socket du client n'est pas présent dans la liste des utilisateurs on l'ajoute
                        if(!containUserSocket)
                        {
                            User u = usm.getUser(login);
                            u.setSocket(source);
                            usersConnected.add(u);
                            source.send("{\"command\":\"chat\",\"pseudo\":\"[Serveur]\",\"message\":\"Bienvenu " + login + " :)\"}");
                        }
                    }
                    else
                    {
                        source.send("{\"command\":\"chat\",\"pseudo\":\"[Serveur]\",\"message\":\"Erreur lors de la connexion :(\"}");
                    }
                }
                catch(Exception e)
                {}
            }
            else if(commande.equals("log_out"))
            {
                //String session = (String) jso.get("session");
                //Pas besoin de renseigner sa session, on a la confirmation dans source
                
                //On supprime de la liste de socket connecté
                socketList.remove(source);
                
                User user = null;
                for(User u : usersConnected)
                {
                    if(u.getSocket().equals(source))
                    {
                        user = u;
                    }
                }
                //On le supprime de la liste des utilisateurs
                if(usersConnected.remove(user))
                {
                    source.send("{\"command\":\"chat\",\"pseudo\":\"[Serveur]\",\"message\":\"Vous êtes déconnecté :(\"}");
                }
            }
            else if(commande.equals("turn_leave"))
            {

            }
            else if(commande.equals("turn_move"))
            {

            }
            else
            {

            }
        }
        catch(JSONException e)
        {
            System.out.println("Une erreur s'est produite durant le traitement d'un objet JSON :\n" + e.getMessage());
        }
    }
}