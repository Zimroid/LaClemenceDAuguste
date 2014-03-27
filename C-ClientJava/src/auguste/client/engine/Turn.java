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

package auguste.client.engine;

import auguste.client.entity.Game;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class Turn 
{
    private List<Move> moves;
    private Move move;
    private final int id;
    private final int turnNumber;
    private final Game game;
    
    public Turn(Game game, int id, int turnNumber)
    {
        this.game = game;
        this.id = id;
        this.turnNumber = turnNumber;
    }
    
    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        
        json.put("game_id", game.getId());
        json.put("pawn_id", move.getPawn().getId());
        json.put("pawn_origin", move.getUwBeg());
        json.put("pawn_destination", move.getUwEnd());
        
        return json;
    }

    /**
     * @return the moves
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to set
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * @return the move
     */
    public Move getMove() {
        return move;
    }

    /**
     * @param move the move to set
     */
    public void setMove(Move move) {
        this.move = move;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the turnNumber
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }
}