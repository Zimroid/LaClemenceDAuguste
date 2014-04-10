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
import java.util.ArrayList;
import java.util.List;

/**
 * Un joueur dans une partie
 * @author Evinrude
 */
public class Player 
{
    private int id;
    private String name;
    private Game game;
    private List<Legion> legions;
    
    public Player(int id)
    {
        this.id = id;
        this.legions = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public int getId() 
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) 
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() 
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the game
     */
    public Game getGame()
    {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) 
    {
        this.game = game;
    }
    
    public void addLegion(Legion l)
    {
    	this.legions.add(l);
    }
    
    public List<Legion> getLegions()
    {
        return this.legions;
    }
    
    public void setLegions(List<Legion> legions)
    {
    	this.legions = legions;
    }
}