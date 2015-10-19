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

    double timeToLive = 1.0;
    public Explosion(double x, double y) {
        super(2);
        this.setX(x);
        this.setY(y);
    }

    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        timeToLive -= frameDelta;
        if(timeToLive < 0) {
            this.deactivate();
        }
        return true;
    }
    
}
