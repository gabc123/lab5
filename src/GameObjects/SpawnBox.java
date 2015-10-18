/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import GameObjects.Projectile.ProjectileBuilder;

/**
 *
 * @author o_0
 */
public class SpawnBox extends Physics {
    private Weapon weapon;
    private ProjectileBuilder projectile;
    private int ammo;
    public SpawnBox(ProjectileType type,double x, double y, int modelId) {
        super(0, 0, modelId);
        this.setX(x);
        this.setY(y);
        this.projectile = new ProjectileBuilder(type);
        this.projectile = this.projectile.withDamage(Math.random())
                .withSpeed(Math.random());
        switch(type) {
            case GRANADE: projectile.withModel(0); break;
            case BULLET: projectile.withModel(0); break;
            case MISSILE: projectile.withModel(0); break;
            default: projectile.withModel(0); break;
        }
    }
    
    public Weapon consumeBox(Player player) {
        return new Weapon(player,projectile,ammo,0);
    }
}
