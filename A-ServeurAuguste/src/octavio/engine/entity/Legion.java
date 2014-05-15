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

package octavio.engine.entity;

import octavio.engine.entity.action.Action;
import octavio.engine.entity.action.card.Soothsayer;
import octavio.engine.entity.pawn.Pawn;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Classe représentant une légion.
 * @author Zwyk
 */
public class Legion
{
    
    // Variables de classe
    private String color;
    private String shape;
    private int position;
    
    // Variables métier
    private Player player;
    private final ArrayList<Pawn> pawns;
    private Soothsayer soothsayer = null;
    private Action action = null;
    
    /**
    * Instanciation d'une légion avec le joueur la gérant.
    * @param player Joueur gérant la légion
    * @param color Couleur de la légion
    * @param shape Forme de la légion
    */
    public Legion(Player player, String color, String shape)
    {
        this.player = player;
        this.color = color;
        this.shape = shape;
        this.pawns = new ArrayList<>();
    }
    
    /**
    * Instanciation d'une légion avec le joueur la gérant.
    * @param player Joueur gérant la légion
    */
    public Legion(Player player)
    {
        this(player,null,null);
    }

    /**
     * @return the color
     */
    public String getColor()
    {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color)
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
     * @return the living pawns
     */
    public ArrayList<Pawn> getLivingPawns()
    {
        ArrayList<Pawn> res = new ArrayList<>();
        for(Pawn p : pawns) if(!p.isDeleted()) res.add(p);
        return res;
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

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    @Override
    public String toString()
    {
        return "Légion (" + this.getPosition() + ") - " + this.getPlayer();
    }

    /**
     * @return the shape
     */
    public String getShape() {
        return shape;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(String shape) {
        this.shape = shape;
    }
}
