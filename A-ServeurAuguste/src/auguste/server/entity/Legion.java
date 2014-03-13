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

package auguste.server.entity;

import auguste.server.entity.action.card.Soothsayer;
import auguste.server.entity.pawn.Pawn;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Classe représentant une légion.
 * @author Zwyk
 */
public class Legion
{
    
    // Variables de classe
    private Color color;
    
    // Variables métier
    private Player player;
    private final ArrayList<Pawn> pawns;
    private Soothsayer soothsayer = null;
    
    /**
    * Instanciation d'une légion avec le joueur la gérant.
    * @param player Joueur gérant la légion
    * @param color Couleur de la légion
    */
    public Legion(Player player, Color color)
    {
        this.player = player;
        this.color = color;
        this.pawns = new ArrayList<>();
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color)
    {
        this.color = color;
    }

    /**
     * @return the player
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player)
    {
        this.player = player;
    }

    /**
     * @return the pawns
     */
    public ArrayList<Pawn> getPawns()
    {
        return pawns;
    }
    
    /**
     * @param pawn The pawn to add
     */
    public void addPawn(Pawn pawn)
    {
        this.pawns.add(pawn);
    }
    
    /**
     * @param pawn The pawn to remove
     */
    public void removePawn(Pawn pawn)
    {
        this.pawns.remove(pawn);
    }

    /**
     * @return the soothsayer
     */
    public Soothsayer getSoothsayer()
    {
        return soothsayer;
    }

    /**
     * @param soothsayer the soothsayer to set
     */
    public void setSoothsayer(Soothsayer soothsayer)
    {
        this.soothsayer = soothsayer;
    }
}
