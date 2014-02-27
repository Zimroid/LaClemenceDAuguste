package Models;

import java.security.MessageDigest;
import java.sql.ResultSet;
import org.apache.commons.codec.binary.Hex;

/**
 * Manager correspondant au model User
 * Permet d'assurer le stockage des utilisateurs dans la BDD
 * @author Vr4el
 */
public class UserManager
{
    private final Jdbc connect;

    public UserManager(Jdbc connect) throws Exception
    {
            this.connect = connect;
    }
    
    /**
     * Récupération d'un user  (par id)
     * @param idUser id de l'utilisateur à rechercher
     * @return Utilisateur demandé
     * @throws Exception protection contre les erreurs SQL
     */
    public User getUser(int idUser) throws Exception
    {
        // Recherche d'un utilisateur
        String query = String.format("SELECT id, username, password FROM user WHERE id = '%d'", idUser);
        ResultSet rs = connect.executeQuery(query);
        
        return getUserFromResult(rs);
    }

    /**
     * Récupération d'un user  (par login)
     * @param login nom de l'utilisateur recherché
     * @return Utilisateur demandé
     * @throws Exception protection contre les erreurs SQL
     */
    public User getUser(String login) throws Exception
    {
        String query = String.format("SELECT id, username, password FROM user WHERE username = '%s'", login);
        ResultSet rs = connect.executeQuery(query);
        
        return getUserFromResult(rs);
    }

    /**
     * Mise en forme des données retournées par une requete SQL
     * @param rs resultset récupéré par une requete générique (SELECT *)
     * @return retour l'utilisateur sous la forme de model User
     * @throws Exception
     */
    public User getUserFromResult(ResultSet rs) throws Exception
    {
        // Récupération des données depuis le result set
        int id = -1;
        String login = "", pwd = "";
        
        // Parcours des lignes du résultat
        while(rs.next())
        {
            if(rs.getString(1).compareTo("") != 0 && rs.getString(2).compareTo("") != 0 && rs.getString(3).compareTo("") != 0)
            {
                id = Integer.parseInt(rs.getString(1));
                login = rs.getString(2);
                pwd = rs.getString(3);
            }
        }
        
        // Mise en forme
        User usr = new User(id, login, pwd);
        
        // Retour de l'utilisateur
        return usr;
    }

    /**
     * Création d'un utilisateur
     * @param lgn login
     * @param pwd mot de passe
     * @return utilisateur créé
     * @throws Exception
     */
    public User createUser(String lgn, String pwd) throws Exception
    {
        // Hachage du mot de passe
        MessageDigest cript = MessageDigest.getInstance("SHA-1");
        cript.reset();
        cript.update(pwd.getBytes("utf8"));
        String passSHA = new String(Hex.encodeHex(cript.digest()));
             
        // Ajout d'un utilisateur
        String query = String.format("INSERT INTO user VALUES (NULL,'%s','%s')", lgn, passSHA);
        connect.executeUpdate(query);
        
        // Recherche du dernier id ajouté
        query = "SELECT LAST_INSERT_ID()";
        ResultSet rs = connect.executeQuery(query);
        
        // Parcours des lignes du résultat
        int lastId = -1;
        while(rs.next())
        {
            lastId = Integer.parseInt(rs.getString(1));
        }
        
        // Recherche de l'utilisateur
        User usr = null;
        if(lastId != -1)
        {
            usr = getUser(lastId);
        }
        
        // Retour de l'utilisateur
        return usr;
    }

    /**
     * Modification d'un mot de passe
     * @param lgn login de l'utilisateur
     * @param oldPwd ancien mot de passe
     * @param newPwd nouveau mot de passe
     * @param confirmNewPwd confirmation du nouveau mot de passe (sécurité)
     * @return Utilisateur (ou null si il n'existe pas)
     * @throws Exception
     */
    public User changePassword(String lgn, String oldPwd, String newPwd, String confirmNewPwd) throws Exception
    {
        // Variable de retour
        User usr = null;
        
        // Vérification de l'existence de l'utilisateur
        if(existUser(lgn))
        {
            usr = getUser(lgn);
            
            // Vérification ancien mot de passe et correspondance des nouveaux mots de passe
            if(usr.getPwd().compareTo(oldPwd) == 0 && newPwd.compareTo(confirmNewPwd) == 0)
            {
                // Hachage du mot de passe
                MessageDigest cript = MessageDigest.getInstance("SHA-1");
                cript.reset();
                cript.update(newPwd.getBytes("utf8"));
                String passSHA = new String(Hex.encodeHex(cript.digest()));
        
                // Modification mot de passe
                String query = String.format("UPDATE user SET password = '%s'", passSHA);
                connect.executeUpdate(query);
                
                // Récupération de l'user modifié depuis la BDD
                usr = getUser(usr.getId());
            }
        }        
        
        // Retour de l'utilisateur (ou null si inexistant)
        return usr;
    }    
    
    /**
     * Vérification de l'existance d'un utilisateur
     * @param nom 
     * @return boolean confirmant ou infirmant l'existance de l'utilisateur
     * @throws Exception
     */
    public boolean existUser(String nom) throws Exception
    {
        String query = String.format("SELECT id, username, password FROM user WHERE username = '%s'", nom);
        ResultSet rs = connect.executeQuery(query);
        return rs.next();
    }
}
