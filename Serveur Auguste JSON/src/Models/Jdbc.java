package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc
{
    private final String url;
    private final String login;
    private final String pwd;

    private Connection con;

    public Jdbc(String url, String login, String passwd)
    {
            this.url = url;
            this.login = login;
            this.pwd = passwd;
    }

    public void connect() throws SQLException, ClassNotFoundException
    {
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url, this.login, this.pwd);
    }

    public void disconnect() throws SQLException
    {
            this.con.close();
    }

    public ResultSet executeQuery(String query) throws SQLException
    {
            PreparedStatement state = this.con.prepareStatement(query);
            return state.executeQuery();
    }

    public int executeUpdate(String query) throws SQLException
    {
            PreparedStatement state = this.con.prepareStatement(query);
            return state.executeUpdate(query);
    }

    public static void showResult(ResultSet result) throws Exception
    {
            int i;
            for (i = 1; i <= result.getMetaData().getColumnCount(); i++)
            {
                    System.out.print(result.getMetaData().getColumnName(i) + "\t\t");
            }
            System.out.println("\n");
            while(result.next())
            {
                    for (i = 1; i <= result.getMetaData().getColumnCount(); i++)
                    {
                            System.out.print(result.getString(i) + "\t\t");
                    }
                    System.out.println();
            }
            System.out.println();
    }

    public Connection getCon()
    {
            return this.con;
    }
}