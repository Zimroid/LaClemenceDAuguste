package clientjavaacrobatt;
import auguste.client.engine.Board;
import auguste.client.entity.Client;
import auguste.client.graphical.CSL;
import auguste.client.graphical.UpdateListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.JSONException;
import auguste.client.engine.Cell;

public class Main
{        
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{
            Board b = new Board(3);
            //b.boardToConsole();
            System.out.println();
            auguste.client.engine.Cell c0 = new auguste.client.engine.Cell(new auguste.client.engine.UW(0,0));
            List<Cell> listcells = b.getNeighbors(c0);
            System.out.println(listcells.size());
            for(Cell c : listcells)
            {
                System.out.println(c.getUW().toString());
            }
            
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