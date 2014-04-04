package clientjavaacrobatt;
import auguste.client.engine.Board;
import auguste.client.engine.Cell;
import auguste.client.engine.UW;
import auguste.client.entity.Client;
import auguste.client.interfaces.CSL;
import auguste.client.interfaces.UpdateListener;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;

public class Main
{        
	public static void main(String[] args) throws URISyntaxException, IOException, JSONException
	{
//            Board b = new Board(3);
//            b.boardToConsole();
//            List<Cell> cells = b.getNeighbors(b.getCell(new UW(2,0)));
//            for(Cell c : cells)
//            {
//                System.out.println(c.getUW());
//            }
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