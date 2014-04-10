package clientjavaacrobatt;
import auguste.client.entity.Client;
import auguste.client.interfaces.CSL;
import auguste.client.interfaces.UpdateListener;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

public class Main
{        
	public static Client c;
	
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{		
        c = Client.getInstance();
        c.getInterfaces().add(new CSL());
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