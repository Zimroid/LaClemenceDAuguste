package clientjavaacrobatt;
import auguste.client.entity.Client;
import auguste.client.interfaces.CSL;
import auguste.client.interfaces.UpdateListener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

public class Main
{        
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{		
        Client c = Client.getInstance();
        c.getInterfaces().add(new CSL());
        c.getClientSocket().connect();
        for(UpdateListener ul : c.getInterfaces())
        {
            if(ul instanceof CSL)
            {
                CSL csl = (CSL) ul;
                csl.run();
            }
        }
	}
}