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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Evinrude
 */
public class Board 
{
    private List<List<Cell>> cells;
    private final int size;
    private final Map<UW, Cell> tabCells;
    
    public Board(int size)
    {
        this.tabCells = new HashMap<>();
        
        // Création du carré qui servira à "sculpter" le plateau
        this.cells = buildBoard(size);
        this.cells = sculptBoard(this.cells, size);
        this.fillTab(cells);
        
        this.size = size;
    }
    
    private static List<Cell> lineCell(int size, int u)
    {
        List<Cell> res = new ArrayList<>();
        int wMax = size - 1;
        int wMin = wMax *(-1);
        
        for(int i = wMin; i<=wMax; i++)
        {
            UW uw = new UW(u,i);
            Cell c = new Cell(uw);
            res.add(c);
        }
        
        return res;
    }
    
    private static List<List<Cell>> buildBoard(int size)
    {
        List<List<Cell>> res = new ArrayList<>();
        
        int uMax = size - 1;
        int uMin = uMax * (-1);
        
        for(int i = uMin; i <= uMax; i++)
        {
            List<Cell> line = lineCell(size, i);
            res.add(line);
        }
        
        return res;
    }
    
    private static List<List<Cell>> sculptBoard(List<List<Cell>> board, int size)
    {
        List<List<Cell>> res = new ArrayList<>();
        
        for(int i = 0; i<size-1; i++)
        {
            List<Cell> line = board.get(i);
            List<Cell> preLine = line.subList(0, (size)+i);
            
            //Ajout de case vide, pour plus de facilité à gérer les coordonnées
            List<Cell> nullList = new ArrayList<>();
            for(int j = size+i; j<line.size(); j++)
            {
                nullList.add(null);
            }
            preLine.addAll(nullList);
            res.add(preLine);
        }
        
        res.add(board.get(size-1));
        
        for(int i = 0; i<size-1; i++)
        {
            List<Cell> line = board.get(i+size);
            List<Cell> preLine = line.subList(i+1, line.size());
            
            //Ajout de case vide, pour plus de facilité à gérer les coordonnées
            List<Cell> nullList = new ArrayList<>();
            for(int j = 0; j<i+1; j++)
            {
                nullList.add(null);
            }
            nullList.addAll(preLine);
            res.add(nullList);
        }
        
        return res;
    }
    
    public void boardToConsole()
    {
        for(List<Cell> line : this.cells)
        {
            for(int i = 0; i<line.size(); i++)
            {
                if(line.get(i) != null)
                {
                    System.out.print(line.get(i).getUW().toString()+", ");
                    //System.out.print("*");
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    public List<Cell> getDeplacableCells(UW uw)
    {
        return this.getDeplacableCells(this.getCell(uw));
    }
    
    public List<Cell> getDeplacableCells(Cell origin)
    {
        List<Cell> res = new ArrayList<>();
        
        for(Cell neighbor : this.getNeighbors(origin))
        {
            if(!neighbor.isOccuped())
            {
                if(!res.contains(neighbor))
                {
                    res.add(neighbor);
                }
            }
            else
            {
                if(neighbor.getPawn().getPlayer().getId() == origin.getPawn().getPlayer().getId())
                {
                    res.addAll(this.getDeplacableCells(neighbor));
                }
            }
        }
        
        return res;
    }
    
    private void fillTab(List<List<Cell>> board)
    {
        for(List<Cell> line : board)
        {
            for(Cell cell : line)
            {
                if(cell != null)
                {
                    this.tabCells.put(cell.getUW(), cell);
                }
            }
        }
    }
    
    public List<Cell> getNeighbors(Cell cell)
    {
        List<Cell> res = new ArrayList<>();
        UW coordonneesBase = cell.getUW();
        
        for(int i = -1; i<=1; i++)
        {
            for(int j = -1; j<=1; j++)
            {
                if(i != j*(-1))
                {
                    UW neighborUW = new UW(coordonneesBase.getU()+i,coordonneesBase.getW()+j);
                    Cell neighbor = this.tabCells.get(neighborUW);

                    if(neighbor != null && !neighbor.getUW().equals(cell.getUW()))
                    {
                        res.add(neighbor);
                    }
                }
            }
        }
        
        return res;
    }
    
    public Cell getCell(UW uw)
    {
        Cell res = null;
        
        for(List<Cell> line : this.cells)
        {
            for(Cell c : line)
            {
                if(c != null && c.getUW().equals(uw))
                {
                    res = c;
                }
            }
        }
        
        return res;
    }
    
    public static List<Cell> convertForServer(List<Cell> cells)
    {
        List<Cell> res = new ArrayList<>();
        for(Cell c : cells)
        {
            res.add(Cell.convertToServer(c));
        }
        return res;
    }
    
    public static List<Cell> convertFromServer(List<Cell> cells)
    {
        List<Cell> res = new ArrayList<>();
        
        for(Cell c : cells)
        {
            res.add(Cell.convertFromServer(c));
        }
        
        return res;
    }
    
    public void applyTenaille(List<Tenaille> tenailles)
    {
        for(Tenaille tenaille : tenailles)
        {
            List<Pawn> victimes = tenaille.applyTenaille();
            for(Pawn pawn : victimes)
            {
                Player player = pawn.getPlayer();
                player.getPawns().remove(pawn);
            }
        }
    }

    /**
     * @return the cells
     */
    public List<List<Cell>> getCells() {
        return cells;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }
}