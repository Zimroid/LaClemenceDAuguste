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
    
    /**
     * Crée une instance de la classe Board, qui représente un tableau d'une certaine taille contenant des cellules.
     * @param size La taille du tableau.
     */
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
            
            res.add(preLine);
        }
        
        res.add(board.get(size-1));
        
        for(int i = 0; i<size-1; i++)
        {
            List<Cell> line = board.get(i+size);
            List<Cell> preLine = line.subList(i+1, line.size());
            
            res.add(preLine);
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
                }
                else
                {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    
    /**
     * @param uw Les coordonnées de la cellule dont on veut trouver les voisines.
     * @return La liste des cellules adjacentes au groupe de cellules auquel appartient la cellule passée en paramètre.
     */
    public List<Cell> getDeplacableCells(UW uw)
    {
        return this.getDeplacableCells(this.getCell(uw));
    }
    
    /**
     * @param origin La cellule dont on veut trouver les voisines.
     * @return La liste des cellules adjacentes au groupe de cellules auquel appartient la cellule passée en paramètre.
     */
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
            	//Vérifier que le pion est un legionnaire
            	Legionnary legionnaryNei = (Legionnary) neighbor.getPawn();
            	Legionnary legionnaryOri = (Legionnary) origin.getPawn();
                if(legionnaryNei.getLegion().getPlayer().getId() == legionnaryOri.getLegion().getPlayer().getId())
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
    
    /**
     * @param cell La cellule dont on veut les voisins.
     * @return Les voisins de la cellule passée en paramètre.
     */
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
    
    /**
     * @param uw Les coordonnées de la cellule.
     * @return La cellule correspondante au coordonnées.
     */
    public Cell getCell(UW uw)
    {
        Cell res = null;
        
        for(List<Cell> line : this.cells)
        {
            for(Cell c : line)
            {
                if(c.getUW().equals(uw))
                {
                    res = c;
                }
            }
        }
        
        return res;
    }
    
    /**
     * @param cells Liste de cellules à convertir vers le serveur. 
     * @return Une liste de cellules avec les coordonnées correct pour le plateau serveur.
     */
    public static List<Cell> convertTo(List<Cell> cells)
    {
        List<Cell> res = new ArrayList<>();
        for(Cell c : cells)
        {
            res.add(Cell.convertTo(c));
        }
        return res;
    }
    
    /**
     * @param cells Liste de cellules à convertir depuis le serveur.
     * @return Une liste de cellules avec les coordonnées correct pour le plateau client.
     */
    public static List<Cell> convertFrom(List<Cell> cells)
    {
        List<Cell> res = new ArrayList<>();
        
        for(Cell c : cells)
        {
            res.add(Cell.convertFrom(c));
        }
        
        return res;
    }
    
    /**
     * @param tenailles La liste des tenailles à appliquer.
     */
    public void applyTenaille(List<Tenaille> tenailles)
    {
        for(Tenaille tenaille : tenailles)
        {
            List<Legionnary> victimes = tenaille.applyTenaille();
            for(Legionnary pawn : victimes)
            {
                Legion legion = pawn.getLegion();
                legion.removePawn(pawn);
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