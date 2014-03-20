/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package auguste.client.graphical;

import auguste.client.command.manager.CommandClientManager;
import auguste.client.entity.Client;
import java.util.Scanner;
import org.json.JSONException;

/**
 *
 * @author Evinrude
 */
public class CSL 
{
    private final Client client;
    private boolean stop;
    
    public CSL(Client client)
    {
        this.client = client;
    }
    
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
    
    public void setStop(boolean b)
    {
        this.stop = b;
    }
}