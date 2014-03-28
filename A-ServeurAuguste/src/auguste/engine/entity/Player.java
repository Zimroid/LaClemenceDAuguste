/*
 * Copyright 2014 Conseil7.
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

import auguste.server.User;
import java.util.ArrayList;


/**
 * Classe représentant un joueur.
 * @author Lzard
 */
public class Player
{        
    // Variables métier
    private User user;
    private final ArrayList<Legion> legions;
    private Team team;
    private Game game = null;
    
    /**
     * Instanciation d'un utilisateur avec les valeurs données.
     * @param user L'utilisateur relatif au joueur
     */
    public Player(User user)
    {
        this.user = user;
        this.legions = new ArrayList<>();
    }

    /**
     * @return the legions
     */
    public ArrayList<Legion> getLegions()
    {
        return legions;
    }
    
    /**
     * @param legion the legion to add
     */
    public void addLegion(Legion legion)
    {
        this.legions.add(legion);
    }
    
    /**
     * @param legion the legion to remove
     */
    public void removeLegion(Legion legion)
    {
        this.legions.remove(legion);
    }

    /**
     * @return the team
     */
    public Team getTeam()
    {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam(Team team)
    {
        this.team = team;
        if(!this.team.getPlayers().contains(this)) this.team.addPlayer(this);
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

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString()
    {
        return "Joueur (" + this.user.getName() + ") - " + this.getTeam();
    }
}
