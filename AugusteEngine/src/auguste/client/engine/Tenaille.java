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
import java.util.List;

/**
 *
 * @author Evinrude
 */
public class Tenaille 
{
    private final Cell beginning;
    private final Cell end;
    private final Board board;
    
    /**
     * @param beginning Le début de la tenaille.
     * @param end La fin de la tenaille.
     * @param board Le plateau de jeu.
     */
    public Tenaille(Cell beginning, Cell end, Board board)
    {
        this.beginning = beginning;
        this.end = end;
        this.board = board;
    }
    
    /**
     * Cherche et renvoie la liste des pions détruits par la tenaille
     * @return Liste des pions détruits par la tenaille
     */
    public List<Legionnary> applyTenaille()
    {
        List<Legionnary> res = new ArrayList<>();
        
        int uBeg = this.beginning.getUW().getU();
        int wBeg = this.beginning.getUW().getW();
        
        int uEnd = this.end.getUW().getU();
        int wEnd = this.end.getUW().getW();
        
        // Recherche des cases sur une ligne.
        if(uBeg == uEnd) // Recherche des cases sur la ligne du U
        {
            int inc;
            if(wEnd > wBeg)
            {
                inc = 1;
            }
            else
            {
                inc = -1;
            }
            
            for(int i = wBeg; i<=wEnd; i = i+inc)
            {
                Cell c = this.board.getCell(new UW(uBeg, i));
                
                if(c.getUW()!=this.beginning.getUW() &&
                   c.getUW()!=this.end.getUW() &&
                   c.isOccuped())
                {
                    res.add((Legionnary) c.getPawn());
                }
            }
        }
        else if(wBeg == wEnd) // Recherche des cases sur la ligne des W
        {
            int inc;
            if(uEnd > uBeg)
            {
                inc = 1;
            }
            else
            {
                inc = -1;
            }
            for(int i = uBeg; i<=uEnd; i = i+inc)
            {
                Cell c = this.board.getCell(new UW(i, wBeg));
                
                if(c.getUW()!=this.beginning.getUW() &&
                   c.getUW()!=this.end.getUW() &&
                   c.isOccuped())
                {
                    res.add((Legionnary) c.getPawn());
                }
            }
        }
        else // Recherche des cases en biais
        {
            int inc;
            if(uEnd > uBeg)
            {
                inc = 1;
            }
            else
            {
                inc = -1;
            }
            for(int i = uBeg; i<uEnd; i = i + inc)
            {
                for(int j = wBeg; j<wEnd; j = j + inc)
                {
                    Cell c = this.board.getCell(new UW(i, j));
                
                    if(c.getUW()!=this.beginning.getUW() &&
                       c.getUW()!=this.end.getUW() &&
                       c.isOccuped())
                    {
                        res.add((Legionnary) c.getPawn());
                    }
                }
            }
        }
        
        return res;
    }

    /**
     * @return the beginning
     */
    public Cell getBeginning() {
        return beginning;
    }

    /**
     * @return the end
     */
    public Cell getEnd() {
        return end;
    }
}