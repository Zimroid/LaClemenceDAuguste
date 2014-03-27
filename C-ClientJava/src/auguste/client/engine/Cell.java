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
public class Cell 
{
    private final UW uw;
    private boolean armored;
    private Pawn pawn;
    private boolean edge;
    
    public Cell(UW uw)
    {
        this.uw = uw;
        this.armored = false;
        this.edge = false;
    }
    
    public void setPawn(Pawn pawn)
    {
        this.pawn = pawn;
    }
    
    public Pawn getPawn()
    {
        return this.pawn;
    }
    
    public boolean isOccuped()
    {
        boolean res = false;
        if(this.pawn != null)
        {
            res = true;
        }
        return res;
    }
    
    public void setArmor(boolean b)
    {
        this.armored = b;
    }
    
    public boolean isArmored()
    {
        return this.armored;
    }
    
    public void takeArmor()
    {
        this.armored = false;
    }
    
    public UW getUW()
    {
        return this.uw;
    }

    /**
     * @return the edge
     */
    public boolean isEdge() {
        return edge;
    }

    /**
     * @param edge the edge to set
     */
    public void setEdge(boolean edge) {
        this.edge = edge;
    }
    
    public String getStringRepresentation()
    {
        if(this.isOccuped())
        {
            return this.getPawn().getStringRepresentation();
        }
        else if(this.isArmored())
        {
            return "o";
        }
        else
        {
            return ".";
        }
    }
    
    public static Cell convertToServer(Cell cell)
    {
        Cell res;
        
        if(cell.getUW().getU()>0)
        {
            int u = cell.getUW().getU();
            int w = cell.getUW().getW() - cell.getUW().getU();
            UW coor = new UW(u,w);
            res = new Cell(coor);
        }
        else
        {
            res = cell;
        }
        
        return res;
    }
    
    public static Cell convertFromServer(Cell cell)
    {
        Cell res;
        
        if(cell.getUW().getU()>0)
        {
            int u = cell.getUW().getU();
            int w = cell.getUW().getW() + cell.getUW().getU();
            UW coor = new UW(u,w);
            res = new Cell(coor);
        }
        else
        {
            res = cell;
        }
        
        return res;
    }
}