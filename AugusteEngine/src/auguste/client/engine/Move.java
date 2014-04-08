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

/**
 *
 * @author Evinrude
 */
public class Move 
{
    private UW uwBeg;
    private UW uwEnd;
    private Pawn pawn;
    private boolean destroyed;
    private Board board;
    
    public Move(Pawn pawn, UW beg, UW end, Board board)
    {
        this.pawn = pawn;
        this.uwBeg = beg;
        this.uwEnd = end;
        this.board = board;
    }

    /**
     * @return the uwBeg
     */
    public UW getUwBeg() 
    {
        return uwBeg;
    }

    /**
     * @param uwBeg the uwBeg to set
     */
    public void setUwBeg(UW uwBeg) 
    {
        this.uwBeg = uwBeg;
    }

    /**
     * @return the uwEnd
     */
    public UW getUwEnd() 
    {
        return uwEnd;
    }

    /**
     * @param uwEnd the uwEnd to set
     */
    public void setUwEnd(UW uwEnd) {
        this.uwEnd = uwEnd;
    }

    /**
     * @return the pawn
     */
    public Pawn getPawn() 
    {
        return pawn;
    }

    /**
     * @param pawn the pawn to set
     */
    public void setPawn(Pawn pawn) 
    {
        this.pawn = pawn;
    }
    
    public void setDestroyed(boolean b)
    {
    	this.destroyed = b;
    }
    
    public boolean isDestroyed()
    {
    	return this.destroyed;
    }
    
    public Legionnary applyMove()
    {
		return (Legionnary) this.board.getCell(this.uwBeg).getPawn();
    }
}