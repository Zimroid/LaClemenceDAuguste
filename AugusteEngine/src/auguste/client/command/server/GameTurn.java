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
import auguste.client.engine.Cell;
import auguste.client.engine.Laurel;
import auguste.client.engine.Legion;
import auguste.client.engine.Legionnary;
import auguste.client.engine.Move;
import auguste.client.engine.Pawn;
import auguste.client.engine.Tenaille;
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
    public static final String CELL_BEG =	"cell_beg";
    public static final String CELL_END =	"cell_end";
    public static final String LEGION_ID = 	"legion_id";
    public static final String U =			"u";
    public static final String W =			"w";
    public static final String TYPE =		"type";
    public static final String TENT =		"tent";
    public static final String TENAILLES =	"tenailles";
    public static final String MOVES =		"moves";
    public static final String U_BEG =		"UBeg";
    public static final String W_BEG =		"WBeg";
    public static final String U_END =		"UEnd";
    public static final String W_END =		"WEnd";
    
    public static final String LAUREL =		"laurel";
    public static final String LEGIONNARY =	"legionnary";
    public static final String ARMORED =	"armored_legionnary";
    public static final String ARMOR =		"armor";
    public static final String WALL =		"wall";
    
    public GameTurn()
    {
        super();
    }
    
    @Override
    public void execute() throws JSONException 
    {
        int id = this.getJSON().getInt(ROOM_ID);
        
        Game game = this.getClient().getGame(id);
        
        this.setMoves(game);
        this.setTenailles(game);
        this.setBoard(game);
        
        for(UpdateListener ul : this.getClient().getInterfaces())
        {
            ul.gameTurnUpdate(id);
        }
    }
    
    private void setTenailles(Game game) throws JSONException
    {
    	JSONArray tenailles = this.getJSON().getJSONArray(TENAILLES);
        for(int i = 0; i < tenailles.length(); i++)
        {
        	JSONObject json = tenailles.getJSONObject(i);
        	JSONObject cellBeg = json.getJSONObject(CELL_BEG);
        	JSONObject cellEnd = json.getJSONObject(CELL_END);
        	UW uwBeg = new UW(cellBeg.getInt(U), cellBeg.getInt(W));
        	UW uwEnd = new UW(cellEnd.getInt(U), cellEnd.getInt(W));
        	
        	Cell begTenaille = game.getBoard().getCell(uwBeg);
        	Cell endTenaille = game.getBoard().getCell(uwEnd);
        	
        	Tenaille tenaille = new Tenaille(begTenaille, endTenaille, game.getBoard());
        	
        	game.getTenailles().add(tenaille);
        }
    }
    
    private void setMoves(Game game) throws JSONException
    {
    	JSONArray moves = this.getJSON().getJSONArray(MOVES);
        for(int i = 0; i < moves.length(); i++)
        {
        	JSONObject json = moves.getJSONObject(i);
        	UW uwBeg = new UW(json.getInt(U_BEG), json.getInt(W_BEG));
        	UW uwEnd = new UW(json.getInt(U_END), json.getInt(W_END));
        	Pawn p = game.getBoard().getCell(uwBeg).getPawn();
        	
        	Move m = new Move(p, uwBeg, uwEnd);
        	
        	game.getMoves().add(m);
        }
    }
    
    private void setBoard(Game game) throws JSONException
    {
        JSONArray board = this.getJSON().getJSONArray(BOARD);
    	Board b = new Board(game.getBoardSize());
        for(int i = 0; i < board.length(); i++)
        {
        	JSONObject json = board.getJSONObject(i);
        	
        	int legion_id = json.getInt(LEGION_ID);
        	Legion legion = game.getLegions().get(legion_id);
        	UW uw = new UW(json.getInt(U),json.getInt(W));
        	Pawn p = null;
        	
        	switch(json.getString(TYPE))
        	{
	        	case LAUREL:
	        		p = new Laurel(uw);
	        		break;
	        	case LEGIONNARY:
	        		p = new Legionnary(uw, legion);
	        		break;
	        	case ARMORED:
	        		p = new Legionnary(uw, legion);
	        		((Legionnary) p).takeArmor();
	        		break;
	        	case ARMOR:
	        		game.getBoard().getCell(uw).putArmor();
	        		break;
	        	case WALL:
	        		p = new Wall(uw);
	        		break;
        	}
        	
        	b.getCell(uw).setPawn(p);
        	
        	if(json.getBoolean(TENT))
        	{
        		b.getCell(uw).putTent();
        	}
        }
        
        game.setBoard(b);
    }
}