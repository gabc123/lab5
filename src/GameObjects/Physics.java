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
 *
 * @author o_0
 */
public abstract class Physics extends GameObject implements Observer {

    private double dx;
    private double dy;
    private boolean gravityAcitve = true;
    private double bodyRadius = 10;
    protected double getDx() {
        return dx;
    }

    protected double getDy() {
        return dy;
    }

    protected void addToDx(double dx) {
        this.dx += dx;
    }

    protected void addToDy(double dy) {
        this.dy += dy;
    }

    protected Physics(double dx, double dy, int modelId) {
        super(modelId);
        this.dx = dx;
        this.dy = dy;
        this.bodyRadius = 10;
    }

    @Override
    public boolean physicsEnable() { return true;};
    
    protected void setGravity(boolean isOn) {
        this.gravityAcitve = isOn;
    }

    protected void updateGravity(double frameDelta) {
        this.dy += 10.0 * frameDelta;
    }

    public double getBodyRadius() {
        return this.bodyRadius;
    }
    
    public abstract void collisionWith(Physics gameObj);
    
    public abstract void collisionWithTerrainAt(double x, double y);
    
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        if (this.gravityAcitve) {
            updateGravity(frameDelta);
        }
        this.setX(this.getX() + dx * frameDelta);
        this.setY(this.getY() + dy * frameDelta);
        return true;
    }

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
