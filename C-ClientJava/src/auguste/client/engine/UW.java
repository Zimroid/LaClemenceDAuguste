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

/**
 * Les coordonnées en UW
 * @author Evinrude
 */
public class UW
{
    private final int u;
    private final int w;
    
    public UW(int u, int w)
    {
        this.u = u;
        this.w = w;
    }
    
    public int getU()
    {
        return this.u;
    }
    
    public int getW()
    {
        return this.w;
    }
    
    @Override
    public String toString()
    {
        return "U:"+this.u+" W:"+this.w;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof UW)
        {
            UW uw = (UW) o;
            if(uw.getU() == this.getU() && uw.getW() == this.getW())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 97 * hash + this.u;
        hash = 97 * hash + this.w;
        return hash;
    }
}