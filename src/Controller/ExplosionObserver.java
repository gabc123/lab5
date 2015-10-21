/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.GameObject;
import GameObjects.Projectile;
import java.util.Observable;

/**
 *
 * @author o_0
 */
public class ExplosionObserver extends Observable {
    Projectile projectile = null;
    public void explode(Projectile projectile) {
        this.projectile = projectile;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void checkTrigger(GameObject obj) {
        if (obj instanceof Projectile) {
            this.explode((Projectile) obj);
        }
    }
    
    public Projectile getProjectile() {
        return this.projectile;
    }
}
