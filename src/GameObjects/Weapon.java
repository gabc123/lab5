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
    private Direction dirX = Direction.LEFT;
    private Direction dirY = Direction.NONE;
    private double aimX = 0;
    private double aimY = 0;
    private double cooldown = 0.1;
    private double fireTimer = 0;
    private boolean didFire = false;
    private ProjectileBuilder projectileBuilder;
    private int ammo = 0;
    
    public Weapon(Player owner, ProjectileBuilder projectile, int ammo, int modelId) {
        super(modelId);
        this.setX(50);
        this.setY(50);
        this.ammo = ammo;
        this.projectileBuilder = projectile.withOwner(owner);
        
        /*this.builder = new ProjectileBuilder(BULLET)
                .withModel(0)
                .withDamage(20)
                .withSpeed(10)
                .withOwner(owner);*/
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
            return false;
        }
        fireTimer = cooldown;
        didFire = true;
        return true;
    }
    
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        switch(dirX) {
            case LEFT: aimX = -1.0; break;
            case RIGHT: aimX = 1.0; break;
        }
        
        switch(dirY) {
            case UP: aimY += -0.01*frameDelta; break;
            case DOWN: aimY += 0.1*frameDelta; break;
        }
        
        updateCooldown(frameDelta);
        
        if(didFire && ammo > 0) {
            Projectile projectile = projectileBuilder.build(aimX, aimY);
            spawnedObj.add(projectile);
            didFire = false;
            ammo--;
        }
        return true;
    }
    
}
