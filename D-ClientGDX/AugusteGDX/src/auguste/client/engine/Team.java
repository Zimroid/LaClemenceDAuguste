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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Evinrude
 */
public class Team 
{
    private List<Player> players;
    private int color;
    private int teamShape;
    private final int id;
    private Map<Integer,Player> tabPlayers;
    
    public static final int CIRCLE      = 1;
    public static final int TRIANGLE    = 2;
    public static final int SQUARE      = 3;
    public static final int PENTAGONE   = 4;
    public static final int HEXAGONE    = 5;
    
    public Team(int id)
    {
        this.id = id;
        this.players = new ArrayList<>();
        this.tabPlayers = new HashMap();
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) 
    {
        this.players = players;
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * @return the teamShape
     */
    public int getTeamShape() {
        return teamShape;
    }

    /**
     * @param teamShape the teamShape to set
     */
    public void setTeamShape(int teamShape) {
        this.teamShape = teamShape;
    }
    
    public Player getPlayer(int id)
    {
        return this.tabPlayers.get(id);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    public void addPlayer(Player player)
    {
        this.players.add(player);
        this.tabPlayers.put(player.getId(), player);
    }
    
    public void removePlayer(Player p)
    {
        this.players.remove(p);
        this.tabPlayers.remove(p.getId());
    }
}