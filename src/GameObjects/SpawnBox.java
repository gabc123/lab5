/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import GameObjects.Projectile.ProjectileBuilder;
import java.util.Random;

/**
 *
 * @author o_0
 */
public class SpawnBox extends Physics {
    private Weapon weapon;
    private ProjectileBuilder projectile;
    private int ammo;
    private double cooldown;
    public SpawnBox(ProjectileType type,double x, double y, int modelId) {
        super(0, 0, modelId);
        this.setX(x);
        this.setY(y);
        Random rand = new Random();
        this.projectile = new ProjectileBuilder(type);
        this.projectile = this.projectile.withDamage(1 + rand.nextDouble() * 50)
                .withSpeed(20 + rand.nextDouble() * 500)
                .withRadius(5 + rand.nextDouble() * 200);
        switch(type) {
            case GRANADE: projectile.withModel(0); break;
            case BULLET: projectile.withModel(0); break;
            case MISSILE: projectile.withModel(0); break;
            default: projectile.withModel(0); break;
        }
        this.ammo = rand.nextInt(100) + 10;
        //System.out.println("Create Spawnbox with ammo: " + ammo);
        this.cooldown = 0.01 + rand.nextDouble() * 2 ;
        this.setGravity(false);
    }
    
    public Weapon consumeBox(Player player) {
        this.deactivate();
        //System.out.println("player: " + player.getName() + " created weapon: ammo" + ammo);
        return new Weapon(player,projectile,cooldown,ammo,0);
    }

    public void collisionWithTerrainAt(double x, double y) {
        // stop movement
        this.addToDx(-this.getDx());
        this.addToDy(-this.getDy());
        
        // move the objected so it do not collide
        double diffX = getX() - x;
        double diffY = getY() - y;
        this.setX(getX() + 2 * Math.signum(diffX));
        this.setY(getY() + 2 * Math.signum(diffY));        
    }
    
    @Override
    public void collisionWith(Physics gameObj) {
        
    }
}
