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

package auguste.engine.ia;

import auguste.engine.entity.Bot;
import auguste.engine.entity.Game;
import auguste.engine.entity.action.Action;
import java.util.ArrayList;

/**
 *
 * @author Zwyk
 */
public class IA {
    
    private Game game;
    
    public IA(Game g)
    {
        this.game = g;
    }
    
    public ArrayList<Action> activateBot(Bot b)
    {
        return null;
    }
}
