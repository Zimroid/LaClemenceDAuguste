/*
 * Copyright 2014 Zwyk.
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

package auguste.engine.entity;

import auguste.engine.entity.action.card.Fortification;
import auguste.engine.entity.action.card.Horseman;
import auguste.engine.entity.action.card.Volcano;
import java.util.ArrayList;

/**
 * Classe représentant une équipe.
 * @author Zwyk
 */
public class Team
{
    
    // Variables métier
    private final ArrayList<Player> players;
    private Volcano volcano = null;
    private Fortification fortification = null;
    private Horseman horseman = null;
    
    /**
    * Instanciation d'une équipe.
    */
    public Team()
    {
        this.players = new ArrayList<>();
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    /**
     * @param player the player to add
     */
    public void addPlayer(Player player)
    {
        this.players.add(player);
    }

    /**
     * @param player the player to remove
     */
    public void removePlayer(Player player)
    {
        this.players.remove(player);
    }

    /**
     * @return the volcano
     */
    public Volcano getVolcano()
    {
        return volcano;
    }

    /**
     * @param volcano the volcano to set
     */
    public void setVolcano(Volcano volcano)
    {
        this.volcano = volcano;
    }

    /**
     * @return the fortification
     */
    public Fortification getFortification()
    {
        return fortification;
    }

    /**
     * @param fortification the fortification to set
     */
    public void setFortification(Fortification fortification)
    {
        this.fortification = fortification;
    }

    /**
     * @return the horseman
     */
    public Horseman getHorseman()
    {
        return horseman;
    }

    /**
     * @param horseman the horseman to set
     */
    public void setHorseman(Horseman horseman)
    {
        this.horseman = horseman;
    }
}
