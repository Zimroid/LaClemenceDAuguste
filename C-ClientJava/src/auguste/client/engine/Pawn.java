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

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Evinrude
 */
public class Pawn 
{
    private int id;
    private UW uw;
    private final Player player;
    private boolean armored;
    
    public Pawn(UW uw, Player player)
    {
        this.uw = uw;
        this.player = player;
        this.armored = false;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public UW getUW()
    {
        return this.uw;
    }
    
    public void setUW(UW uw)
    {
        this.uw = uw;
    }
    
    public Player getPlayer()
    {
        return this.player;
    }
    
    @Override
    public String toString()
    {
        return "Pawn : "+this.id+" "+this.uw.toString();
    }
    
    public String getStringRepresentation()
    {
        return "*";
    }
    
    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        
        json.put("pawn_id", this.getId());
        json.put("pawn_u", this.getUW().getU());
        json.put("pawn_w", this.getUW().getW());
        
        return json;
    }
    
    public boolean isArmored()
    {
        return this.armored;
    }
    
    public void takeArmor()
    {
        this.armored = true;
    }
}