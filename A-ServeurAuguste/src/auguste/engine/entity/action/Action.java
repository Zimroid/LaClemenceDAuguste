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

package auguste.engine.entity.action;

import auguste.engine.entity.Legion;
import auguste.engine.entity.action.card.Card;

/**
 * Classe représentant une action.
 * @author Zwyk
 */
public class Action {
    
    // Variables métier
    private Legion legion;
    private Movement movement;
    private Card card;

    /**
    * Instanciation d'une action avec le joueur, le mouvement et la carte.
    * @param legion La légion exécutant l'action
    * @param movement Le mouvement relatif à l'action (peut être null)
    * @param card La carte relative à l'action (peut être null)
    */
    public Action(Legion legion, Movement movement, Card card) {
        this.legion = legion;
        this.movement = movement;
        this.card = card;
    }
    
    public Action(Legion legion, Movement movement) {
        this.legion = legion;
        this.movement = movement;
        this.card = null;
    }
    
    /**
     * @return the player
     */
    public Legion getLegion() {
        return legion;
    }

    /**
     * @param legion the legion to set
     */
    public void seLegion(Legion legion) {
        this.legion = legion;
    }

    /**
     * @return the movement
     */
    public Movement getMovement() {
        return movement;
    }

    /**
     * @param movement the movement to set
     */
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }
}
