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

package auguste.engine.turnData;

import java.awt.Point;

/**
 *
 * @author Zwyk
 */
public class Move {
    private Point p1;
    private Point p2;
    private boolean dies;
    
    public Move(Point p1, Point p2, boolean dies)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.dies = dies;
    }

    /**
     * @return the p1
     */
    public Point getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Point p1) {
        this.p1 = p1;
    }

    /**
     * @return the p2
     */
    public Point getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Point p2) {
        this.p2 = p2;
    }

    /**
     * @return the dies
     */
    public boolean isDies() {
        return dies;
    }

    /**
     * @param dies the dies to set
     */
    public void setDies(boolean dies) {
        this.dies = dies;
    }
}
