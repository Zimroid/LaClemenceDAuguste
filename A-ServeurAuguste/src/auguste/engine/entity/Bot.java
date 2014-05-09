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

/**
 *
 * @author Zwyk
 */
public class Bot {

    private Strategy strategy = null;
    private Player player;
    private boolean playedLaurel = false;

    public Bot(Player p) {
        this.player = p;
    }
    
    public Bot(Player p, Strategy strategy) {
        this(p);
        this.strategy = strategy;
    }

    /**
     * @return the strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy the strategy to set
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the playedLaurel
     */
    public boolean getPlayedLaurel() {
        return playedLaurel;
    }

    /**
     * @param playedLaurel the playedLaurel to set
     */
    public void setPlayedLaurel(boolean playedLaurel) {
        this.playedLaurel = playedLaurel;
    }
    
    public enum Strategy {
        random,
        pseudoRandom
    }
}
