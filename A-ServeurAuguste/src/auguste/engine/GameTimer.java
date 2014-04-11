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
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Zwyk
 */
public class GameTimer extends Thread {
    
    private static final ArrayList<GameTimer> threads = new ArrayList<>();
    
    private final Game game;
    private final long duration;
    
    public GameTimer(Game g, long d)
    {
        this.game = g;
        this.duration = d;
    }
    
    @Override
    public void run()
    {
        for(GameTimer thread : GameTimer.threads) thread.interrupt();
        GameTimer.threads.clear();
        GameTimer.threads.add(this);
        try
        {
            Thread.sleep(duration);
        } catch (InterruptedException ex)
        {
        }
        finally
        {
            
            System.out.println("lol at " + (new Date()).getTime());
            if(game.getListener() != null) game.getListener().onTurnEnd();
        }
    }
}
