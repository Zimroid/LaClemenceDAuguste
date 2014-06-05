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

package octavio.engine;

import java.util.TimerTask;
import octavio.engine.entity.Game;

/**
 *
 * @author Zwyk
 */
public class GameTimer extends TimerTask {
    
    private final Game game;
    private final int turn;
    
    public GameTimer(Game g)
    {
        this.game = g;
        this.turn = game.getTurn();
    }
    
    @Override
    public synchronized void run()
    {
        if(game.getListener() != null) game.getListener().onTurnEnd();
        else game.nextTurn();
    }
}
