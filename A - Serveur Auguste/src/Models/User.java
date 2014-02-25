package Models;

public class User {
    private int id;
    private String login;
    private String pwd;
    
    // Création utilisateur
    public User (int id, String lgn, String pwd) {
        this.id     = id;
        this.login  = lgn;
        this.pwd    = pwd;
    }
    
    // Getter / Setter
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
