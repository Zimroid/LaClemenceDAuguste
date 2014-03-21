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

import auguste.engine.entity.action.Action;
import auguste.engine.entity.pawn.Pawn;
import auguste.server.Client;
import java.util.ArrayList;


/**
 * Classe représentant un joueur.
 * @author Lzard
 */
public class Player
{        
    // Variables métier
    private Client user;
    private final ArrayList<Pawn> pawns;
    private Team team;
    private Action action;
    private Game game = null;
    
    /**
     * Instanciation d'un utilisateur avec les valeurs données.
     * @param user L'utilisateur relatif au joueur
     */
    public Player(Client user)
    {
        this.user = user;
        this.pawns = new ArrayList<>();
    }
    
    

    /**
     * @return the pawns
     */
    public ArrayList<Pawn> getPawns()
    {
        return pawns;
    }
    
    /**
     * @param pawn the pawn to add
     */
    public void addPawn(Pawn pawn)
    {
        this.pawns.add(pawn);
    }
    
    /**
     * @param pawn the pawn to remove
     */
    public void removePawn(Pawn pawn)
    {
        this.pawns.remove(pawn);
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
    }

    /**
     * @return the action
     */
    public Action getAction()
    {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(Action action)
    {
        this.action = action;
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
    public Client getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Client user) {
        this.user = user;
    }
}
