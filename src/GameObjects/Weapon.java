/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import GameObjects.Projectile.ProjectileBuilder;
import static GameObjects.Projectile.ProjectileType.*;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Weapon extends GameObject {
    private double x;
    private double y;
    private Direction dirX;
    private Direction dirY;
    private double aimX;
    private double aimY;
    private double cooldown = 1;
    private double fireTimer = 0;
    private boolean didFire = false;
    private ProjectileBuilder projectileBuilder;
    private int ammo = 0;
    
    public Weapon(Player owner, ProjectileBuilder projectile, int modelId) {
        super(modelId);
        this.x = 50;
        this.y = 50;
        /*this.builder = new ProjectileBuilder(BULLET)
                .withModel(0)
                .withDamage(20)
                .withSpeed(10)
                .withOwner(owner);*/
    }
    
    public void setAimX(Direction dir) {
        this.dirX = dir;
    }
    
    public void setAimY(Direction dir) {
        this.dirY = dir;
    }
    
    @Override
    protected void setX(double x) {
        this.x = x;
    }

    @Override
    protected void setY(double y) {
        this.y = y;
    }

    
    
    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
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
            case UP: aimY += -1.0*frameDelta; break;
            case DOWN: aimY += 1.0*frameDelta; break;
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
