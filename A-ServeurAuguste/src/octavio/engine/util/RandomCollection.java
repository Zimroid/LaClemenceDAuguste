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

package octavio.engine.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import octavio.engine.entity.action.Movement;

/**
 * Classe permettant le tirage aléatoire pondéré d'une collection d'objets
 * @author Zwyk
 */
public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    /**
     * 
     */
    public RandomCollection() {
        this(new Random());
    }

    /**
     * 
     * @param random 
     */
    public RandomCollection(Random random) {
        this.random = random;
    }

    /**
     * 
     * @param weight
     * @param result 
     */
    public void add(double weight, E result) {
        if (weight > 0) {
            total += weight;
            map.put(total, result);
        }
    }

    /**
     * @return the map
     */
    public NavigableMap<Double, E> getMap()
    {
        return map;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * 
     * @return L'objet tiré aléatoirement selon les poids
     */
    public E next() {
        if(total > 0) {
            double value = random.nextDouble() * total;
            return map.ceilingEntry(value).getValue();
        }
        else return null;
    }
}
