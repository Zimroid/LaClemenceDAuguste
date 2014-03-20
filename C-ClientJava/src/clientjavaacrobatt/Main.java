package clientjavaacrobatt;
import auguste.client.entity.Client;
import java.io.IOException;
import java.net.URISyntaxException;
import org.json.JSONException;

public class Main
{        
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{
		Client c = new Client();
                c.getClientSocket().setClient(c);
                c.getClientSocket().connect();
                c.runCSL();
	}
}