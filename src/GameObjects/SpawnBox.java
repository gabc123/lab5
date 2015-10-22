/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import GameObjects.Projectile.ProjectileBuilder;
import java.util.Random;

/**
 *Boxes containing weapons that are spawned during the game
 * @author o_0
 */
public class SpawnBox extends Physics {
    private Weapon weapon;
    private ProjectileBuilder projectile;
    private int ammo;
    private double cooldown;
    /**
     * Constructor for spawnbox
     * @param type what type of projectile the box will contain,
     * the stats of the projectile is randomised during creation
     * @param x the x value the box will have when spawend
     * @param y the y value the box will have when spawend
     */
    public SpawnBox(ProjectileType type,double x, double y) {
        super(0, 0,10, 0);
        this.setX(x);
        this.setY(y);
        Random rand = new Random();
        this.projectile = new ProjectileBuilder(type);
        this.projectile = this.projectile.withDamage(1 + rand.nextDouble() * 50)
                .withSpeed(20 + rand.nextDouble() * 500)
                .withRadius(5 + rand.nextDouble() * 200);
        switch(type) {
            case GRANADE: projectile.withModel(3); break;
            case BULLET: projectile.withModel(4); break;
            case MISSILE: projectile.withModel(1); break;
            default: projectile.withModel(0); break;
        }
        this.ammo = rand.nextInt(100) + 10;
        //System.out.println("Create Spawnbox with ammo: " + ammo);
        this.cooldown = 0.01 + rand.nextDouble() * 2 ;
        //this.setGravity(false);
    }
    
    /**
     * when a box is consumed by a player
     * @param player the player that took the box
     * @return the randomised weapon the got
     */
    public Weapon consumeBox(Player player) {
        this.deactivate();
        //System.out.println("player: " + player.getName() + " created weapon: ammo" + ammo);
        return new Weapon(player,projectile,cooldown,ammo,0);
    }

    /**
     * Checks if the box collides with the terrain
     * @param x box x value
     * @param y box y value
     */
    public void collisionWithTerrainAt(double x, double y) {
        // stop movement
        this.addToDx(-this.getDx());
        this.addToDy(-this.getDy());
        
        // move the objected so it do not collide
        double diffX = (getX() - x);
        double diffY = (getY() - y);
        this.setX(getX() + 1*Math.signum(diffX));
        this.setY(getY() + 1*Math.signum(diffY));
        if(getBodyRadius()!=0) {
            diffX = diffX - diffX*(1 - diffX/getBodyRadius());
            diffY = diffY- diffY*(1 - diffY/getBodyRadius());
        }
        this.addToDx(diffX);
        this.addToDy(diffY);  
    }
    
    @Override
    /**
     * spawnbox collided with the object taken as parameter.
     * The box will however do noting in this case.
     */
    public void collisionWith(Physics gameObj) {
        
    }
}
