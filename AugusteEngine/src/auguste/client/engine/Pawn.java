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
    
    /**
     * @param uw Les coordonnées du pion.
     */
    public Pawn(UW uw)
    {
        this.uw = uw;
    }
    
    /**
     * @return L'identifiant du pion.
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * @param id L'identifiant à définir.
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**
     * @return Les coordonnées du pion.
     */
    public UW getUW()
    {
        return this.uw;
    }
    
    /**
     * @param uw Les coordonnées à définir.
     */
    public void setUW(UW uw)
    {
        this.uw = uw;
    }
    
    @Override
    public String toString()
    {
        return this.getStringRepresentation() + " : " + this.id + " " + this.uw.toString();
    }
    
    /**
     * @return La représentation d'un pion en art ASCII.
     */
    public String getStringRepresentation()
    {
        return "*";
    }
    
    /**
     * @return Le JSON du pion.
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException
    {
        JSONObject json = new JSONObject();
        
        json.put("pawn_id", this.getId());
        json.put("pawn_u", this.getUW().getU());
        json.put("pawn_w", this.getUW().getW());
        
        return json;
    }
}