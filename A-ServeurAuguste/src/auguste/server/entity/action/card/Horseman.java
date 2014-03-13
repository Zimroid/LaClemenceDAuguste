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

import auguste.server.entity.Team;

/**
 * Classe représentant une carte de type cavalier.
 * @author Zwyk
 */
public class Horseman {
    
    // Variables métier
    private final Team team;
    
    /**
    * Instanciation d'une carte de type cavalier
    * @param team L'équipe possédant la carte
    */
    public Horseman(Team team)
    {
        this.team = team;
    }

    /**
     * @return the team
     */
    public Team getTeam() {
        return team;
    }
}
