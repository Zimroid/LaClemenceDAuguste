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

package auguste.engine;

import auguste.engine.entity.Game;

/**
 *
 * @author Zwyk
 */
public class GameTimer extends Thread {
    
    private final Game game;
    private final long duration;
    
    public GameTimer(Game g, long d)
    {
        this.game = g;
        this.duration = d;
    }
    
    @Override
    public synchronized void run()
    {
        try
        {
            this.wait(duration);
        } catch (InterruptedException ex)
        {
        }
        game.getListener().onTurnEnd();
    }
}
