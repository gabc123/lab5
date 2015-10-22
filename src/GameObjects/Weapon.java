/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import GameObjects.Projectile.ProjectileBuilder;

import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Weapon extends GameObject {
    Player owner;
    private Direction dirX = Direction.LEFT;
    private Direction dirY = Direction.NONE;
    private double angle = 0;
    private double cooldown = 0.1;
    private double fireTimer = 0;
    private boolean didFire = false;
    private ProjectileBuilder projectileBuilder;
    private int ammo = 0;
    
    public Weapon(Player owner, ProjectileBuilder projectile,double cooldown, int ammo, int modelId) {
        super(modelId);
        this.owner = owner;
        this.setX(50);
        this.setY(50);
        this.ammo = ammo;
        this.cooldown = cooldown;
        this.fireTimer = 0;
        this.projectileBuilder = projectile.withOwner(owner);
        
    }
    
    /**
     * Defualt value, basic weapon
     * @param owner
     * @param type
     * @param modelId 
     */
    public Weapon(Player owner, ProjectileType type,int modelId) {
        super(modelId);
        this.setX(50);
        this.setY(50);
        this.ammo = 50;
        this.owner = owner;
        this.projectileBuilder = new ProjectileBuilder(type)
                .withModel(1)
                .withDamage(20)
                .withSpeed(200)
                .withRadius(50)
                .withOwner(owner);
    }
    
    public void setAimX(Direction dir) {
        this.dirX = dir;
    }
    
    public void setAimY(Direction dir) {
        this.dirY = dir;
    }
    
    private void updateCooldown(double frameDelta) {
        if(fireTimer > 0) {
            fireTimer -= frameDelta;
        }
    }
    
    public boolean fire() {
        if(fireTimer > 0) {
            //System.out.println("player: " + owner.getName() + " Ammo: " + ammo);
            return false;
        }
        fireTimer = cooldown;
        didFire = true;
        return true;
    }
    
    public int getAmmo() {
        return this.ammo;
    }
    
    public double getCooldown() {
        return this.cooldown;
    }
    
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
                
        switch(dirY) {
            case UP: angle += -1.0*frameDelta; break;
            case DOWN: angle += 1.0*frameDelta; break;
        }
        double halfPi = Math.PI/2;
        angle = (angle > halfPi) ? halfPi : angle;
        angle = (angle < -halfPi) ? -halfPi : angle;
        
        updateCooldown(frameDelta);
        
        if(didFire && ammo > 0) {
            double tmpAngle = angle;
            if(dirX == Direction.LEFT && angle < halfPi) {
                tmpAngle = -tmpAngle;
                tmpAngle += Math.PI;
            }
            if(dirX == Direction.RIGHT && angle > halfPi) {
                tmpAngle -= Math.PI;
            }
            double aimX = Math.cos(tmpAngle);
            double aimY = Math.sin(tmpAngle);
            System.out.println("owner: " + owner.getName() +" total Angle: " + tmpAngle +" sin angle: " + angle);
            Projectile projectile = projectileBuilder.build(aimX, aimY);
            spawnedObj.add(projectile);
            didFire = false;
            ammo--;
        }
        return true;
    }
    
}
