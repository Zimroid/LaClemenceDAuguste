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
public class Team 
{
    private List<Player> players;
    private Map<Integer,Player> tabPlayers;
    
    public Team()
    {
        this.players = new ArrayList<>();
        this.tabPlayers = new HashMap<>();
    }

    /**
     * @return the players
     */
    public List<Player> getPlayers() 
    {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) 
    {
        this.players = players;
    }
    
    public Player getPlayer(int id)
    {
        return this.tabPlayers.get(id);
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