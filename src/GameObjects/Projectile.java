/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Projectile extends Physics {

    private double damage;
    private double speed;
    private double damageRadius;
    private Player owner;
    private Player target;
    private double timeToLive = 2.0;

    private ProjectileType type;

    private Projectile(double aimSide, double aimUp, ProjectileBuilder builder) {
        super(0, 0, 10,builder.modelId);
        this.damage = builder.damage;
        this.damageRadius = builder.radius;
        this.speed = builder.speed;
        this.owner = builder.owner;
        this.target = builder.target;
        this.type = builder.type;
        this.setX(this.owner.getX());
        this.setY(this.owner.getY());
        super.addToDx(aimSide * speed);
        super.addToDy(aimUp * speed);
        if (type == ProjectileType.BULLET) {
            super.setGravity(false);
        }
    }

    public double getDamage() {
        return this.damage;
    }

    public double getDamageRadius() {
        return this.damageRadius;
    }

    public Player getOwner() {
        return this.owner;
    }

    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        super.update(frameDelta, spawnedObj);
        timeToLive -= frameDelta;
        if (timeToLive < 0) {
            spawnedObj.add(new Explosion(getX(), getY(), getDamageRadius()));
            super.deactivate();
        }
        return true;
    }

    @Override
    public void collisionWith(Physics gameObj) {
        if (this.owner != gameObj) {
            timeToLive = -1;
        }
    }

    public void collisionWithTerrainAt(double x, double y) {
        timeToLive = -1;    // explodes when < 0
    }
    
    public static class ProjectileBuilder {

        private double damage = 10;
        private double speed = 20;
        private ProjectileType type;
        private double radius = 50;
        private Player owner = null;
        private Player target = null;

        private int modelId = 0;

        public ProjectileBuilder(ProjectileType type) {
            this.type = type;
        }

        public ProjectileBuilder withModel(int modelId) {
            this.modelId = modelId;
            return this;
        }

        public ProjectileBuilder withDamage(double damage) {
            this.damage = damage;
            return this;
        }

        public ProjectileBuilder withRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public ProjectileBuilder withSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        public ProjectileBuilder withOwner(Player owner) {
            this.owner = owner;
            return this;
        }

        public ProjectileBuilder withTarget(Player target) {
            this.target = target;
            return this;
        }

        public Projectile build(double aimX, double aimY) {
            double aimSide = aimX / Math.sqrt(aimX * aimX + aimY * aimY);
            double aimUp = aimY / Math.sqrt(aimX * aimX + aimY * aimY);
            return new Projectile(aimSide, aimUp, this);
        }
    }
}
