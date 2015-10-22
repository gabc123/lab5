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
 * This is a Observable , that will notify all observers to a new explosion
 * @author o_0
 */
public class ExplosionObserver extends Observable {
    Projectile projectile = null;
    /**
     * This will alert all observers of an explosion
     * @param projectile 
     */
    public void explode(Projectile projectile) {
        this.projectile = projectile;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Checks if this is a explosive object like a Projectile,
     * if so then this will get triggerd
     * @param obj the object to check if it going to explode
     */
    public void checkTrigger(GameObject obj) {
        if (obj instanceof Projectile) {
            this.explode((Projectile) obj);
        }
    }
    /**
     * used to get the projectile that exploded
     * @return the projectile that exploded
     */
    public Projectile getProjectile() {
        return this.projectile;
    }
}
