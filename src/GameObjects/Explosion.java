/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Explosion extends GameObject{

    private double timeToLive = 0.5;
    private double kaboomSize = 1.0;
    private double tickScaling = 1;
    public Explosion(double x, double y,double kaboomSize) {
        super(2);
        this.setX(x);
        this.setY(y);
        this.kaboomSize = kaboomSize;
        this.tickScaling = (0.5*kaboomSize)/timeToLive;
        this.setBodySize(kaboomSize/2);
    }

    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        timeToLive -= frameDelta;
        this.setBodySize(this.getBodySize() + tickScaling*frameDelta);
        if(timeToLive < 0) {
            this.deactivate();
        }
        return true;
    }
    
}
