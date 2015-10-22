/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import Controller.ExplosionObserver;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * All objects that has a physics property, movement, gravity and so on subclass this class
 * @author o_0
 */
public abstract class Physics extends GameObject implements Observer {

    private double dx;
    private double dy;
    private boolean gravityAcitve = true;
    private double bodyRadius = 10;
    /**
     * getter for dx
     * @return dx
     */
    protected double getDx() {
        return dx;
    }

    /**
     * getter for dy
     * @return 
     */
    protected double getDy() {
        return dy;
    }

    /**
     * add value to the dx component
     * @param dx added speed
     */
    protected void addToDx(double dx) {
        this.dx += dx;
    }

    /**
     * add value to the dy component
     * @param dy speed to add
     */
    protected void addToDy(double dy) {
        this.dy += dy;
    }

    /**
     * 
     * @param dx start movement x direction
     * @param dy start movement y direction
     * @param bodyRadius the bodyRadius for this object
     * @param modelId the model to be used
     */
    protected Physics(double dx, double dy, double bodyRadius, int modelId) {
        super(modelId);
        this.dx = dx;
        this.dy = dy;
        this.bodyRadius = bodyRadius;
        this.setBodySize(bodyRadius*2);
    }
    
    /**
     * that this obejct has physicsEnable
     * @return true
     */
    @Override
    public boolean physicsEnable() { return true;};
    
    /**
     * set if it uses gravity
     * @param isOn true == on
     */
    protected void setGravity(boolean isOn) {
        this.gravityAcitve = isOn;
    }

    /**
     * updates the gravity effect
     * @param frameDelta diff between frams
     */
    protected void updateGravity(double frameDelta) {
        this.dy += 80.0 * frameDelta;
    }

    /**
     * Sets a new bodyRadius
     * @param bodyRadius new value
     */
    protected void setBodyRadius(double bodyRadius) {
        this.bodyRadius = bodyRadius;
        this.setBodySize(bodyRadius);
    }
    
    /**
     * getter for bodyRadius
     * @return bodyRadius
     */
    public double getBodyRadius() {
        return this.bodyRadius;
    }
    
    /**
     * Called if this object collieds with anything
     * @param gameObj what it collided with
     */
    public abstract void collisionWith(Physics gameObj);
    
    /**
     * The point it hit the terrain at
     * @param x point it hit the terrain at
     * @param y point it hit the terrain at
     */
    public abstract void collisionWithTerrainAt(double x, double y);
    
    /**
     * Updates all physics on this object
     * @param frameDelta se super
     * @param spawnedObj se super
     * @return true
     */
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        if (this.gravityAcitve) {
            updateGravity(frameDelta);
        }
        this.setX(this.getX() + dx * frameDelta);
        this.setY(this.getY() + dy * frameDelta);
        return true;
    }

    /**
     * This is called if there is an explosion
     * @param o what projectile that exploded
     * @param arg not in use
     */
    @Override
    public void update(Observable o, Object arg) {
        
        Projectile exploded = ((ExplosionObserver) o).getProjectile();
        
        double diffX = this.getX() - exploded.getX();
        double diffY = this.getY() - exploded.getY();
        double effectRadius = exploded.getDamageRadius();
        
        double distance = Math.sqrt(diffX*diffX + diffY*diffY);
        if(distance > effectRadius) {
            return;
        }
        
        double power = 1 - distance/effectRadius;
        double strength = power * exploded.getDamage();
        this.dx += Math.signum(diffX) * strength;
        this.dy += Math.signum(diffY) * strength;
        
        if(this instanceof Player) {
            ((Player)this).takeDamage(strength);
        }
    }
    
    /**
     * Constrain the GameObject insde the height and width
     * @param w width
     * @param h height
     */
    @Override
    public void constrain(double w, double h) {
        double x = this.getX();
        double y = this.getY();
        if(x < bodyRadius) {
            this.setX(bodyRadius);
            this.dx = 0;//-dx/2;
        }else if(w - bodyRadius < x) {
            this.setX(w - bodyRadius);
            this.dx = 0;//-dx/2;;
        }
        if(y < bodyRadius) {
            this.setY(bodyRadius);
            this.dy = 0;//-dy/2;
        }else if(h - bodyRadius < y) {
            this.setY(h - bodyRadius);
            this.dy = 0;//-dy/2;
        }
    }
    
}
