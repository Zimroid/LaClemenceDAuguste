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
    
    /**
     * @param id L'identifiant du joueur voulu.
     * @return Le joueur.
     */
    public Player getPlayer(int id)
    {
        return this.tabPlayers.get(id);
    }
    
    /**
     * Mise à jour du joueur passé en paramètre. S'il n'existe pas, un nouveau joueur est inseré.
     * @param player Le joueur à mettre à jour.
     */
    public void updatePlayer(Player player)
    {
    	if(this.getPlayer(player.getId()) != null)
    	{
    		Player toRemove = this.getPlayer(player.getId());
    		this.players.remove(toRemove);
    		this.tabPlayers.remove(toRemove.getId());
    	}
        this.players.add(player);
        this.tabPlayers.put(player.getId(), player);
    }
    
    /**
     * @param p Le joueur à retirer.
     */
    public void removePlayer(Player p)
    {
        this.players.remove(p);
        this.tabPlayers.remove(p.getId());
    }
}