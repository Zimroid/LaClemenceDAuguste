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

package auguste.server.entity.action.card;

import auguste.server.entity.Legion;

/**
 * Classe représentant une carte de type devin.
 * @author Zwyk
 */
public class Soothsayer {
    
    // Variables métier
    private final Legion legion;
    
    /**
    * Instanciation d'une carte de type muraille
    * @param legion La légion possédant la carte
    */
    public Soothsayer(Legion legion)
    {
        this.legion = legion;
    }

    /**
     * @return the legion
     */
    public Legion getLegion() {
        return legion;
    }
}
