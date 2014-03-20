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

import java.util.ArrayList;

/**
 * Classe représentant une partie.
 * @author Zwyk
 */
public class Game
{
    
    // Variables métier
    private Player master;
    private final ArrayList<Player> players;
    private Board board;
    
    /**
    * Instanciation d'une partie avec son gérant.
    * @param master Joueur gérant la partie
    */
    public Game(Player master)
    {
        this.master = master;
        this.players = new ArrayList<>();
        this.players.add(master);
    }

    /**
     * @return the master
     */
    public Player getMaster()
    {
        return master;
    }

    /**
     * @param master the master to set
     */
    public void setMaster(Player master)
    {
        this.master = master;
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
     * @return the board
     */
    public Board getBoard()
    {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(Board board)
    {
        this.board = board;
    }
}
