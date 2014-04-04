/*
 * Copyright 2014 Evinrude.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package auguste.client.command.server;

import auguste.client.engine.Board;
import auguste.client.engine.Laurel;
import auguste.client.engine.Legion;
import auguste.client.engine.Legionnary;
import auguste.client.engine.Pawn;
import auguste.client.engine.UW;
import auguste.client.engine.Wall;
import auguste.client.entity.Game;
import auguste.client.interfaces.UpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class GameTurn extends CommandServer
{
    public static final String ROOM_ID =   	"room_id";
    public static final String BOARD = 		"board";
    
    public GameTurn()
    {
        super();
    }
    
    @Override
    public void execute() throws JSONException 
    {
        int id = this.getJSON().getInt(ROOM_ID);
        
        Game game = this.getClient().getGame(id);
        JSONArray board = this.getJSON().getJSONArray(BOARD);
        
        Board b = new Board(game.getBoardSize());
        
        for(int i = 0; i < board.length(); i++)
        {
        	JSONObject json = board.getJSONObject(i);
        	
        	int legion_id = json.getInt("legion_id");
        	Legion legion = game.getLegions().get(legion_id);
        	UW uw = new UW(json.getInt("x"),json.getInt("y"));
        	Pawn p = null;
        	
        	switch(json.getString("type"))
        	{
	        	case "laurel":
	        		p = new Laurel(uw);
	        		break;
	        	case "legionnary":
	        		p = new Legionnary(uw, legion);
	        		break;
	        	case "armored_legionnary":
	        		p = new Legionnary(uw, legion);
	        		((Legionnary) p).takeArmor();
	        		break;
	        	case "armor":
	        		game.getBoard().getCell(uw).putArmor();
	        		break;
	        	case "wall":
	        		p = new Wall(uw);
	        		break;
        	}
        	
        	b.getCell(uw).setPawn(p);
        	
        	if(json.getBoolean("tent"))
        	{
        		b.getCell(uw).putTent();
        	}
        }
        
        game.setBoard(b);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.gameTurnUpdate(id);
        }
    }
}