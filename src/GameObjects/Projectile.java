/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.util.ArrayList;

/**
 *Class containing information about the projectiles used by weapons in the game
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

    /**
     * Private constructor, using the builder pattern
     * @param aimSide what start dx
     * @param aimUp what start dy
     * @param builder a builder for a projectile
     */
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

    /**
     * getter
     * @return damage
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * 
     * @return damageRaduys
     */
    public double getDamageRadius() {
        return this.damageRadius;
    }

    /**
     * what player shot this
     * @return 
     */
    public Player getOwner() {
        return this.owner;
    }
/**
     * This is called every frame by gameTimer thru the controller
     * @param frameDelta fraction of a seconde since last update
     * @param spawnedObj list to store all newly created objects
     * @return not used
     */
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

    /**
     * The player has been in a collision, with gameObj
     * @param gameObj gameObj
     */
    @Override
    public void collisionWith(Physics gameObj) {
        if (this.owner != gameObj) {
            timeToLive = -1;
        }
    }
  /**
     * The point it hit the terrain at
     * @param x point it hit the terrain at
     * @param y point it hit the terrain at
     */
    public void collisionWithTerrainAt(double x, double y) {
        timeToLive = -1;    // explodes when < 0
    }
    
    /**
     * builder for projectile
     */
    public static class ProjectileBuilder {

        private double damage = 10;
        private double speed = 20;
        private ProjectileType type;
        private double radius = 50;
        private Player owner = null;
        private Player target = null;

        private int modelId = 0;

        /**
         * 
         * @param type what projectile
         */
        public ProjectileBuilder(ProjectileType type) {
            this.type = type;
        }

        /**
         * 
         * @param modelId modelid
         * @return builder
         */
        public ProjectileBuilder withModel(int modelId) {
            this.modelId = modelId;
            return this;
        }

        /**
         * 
         * @param damage
         * @return 
         */
        public ProjectileBuilder withDamage(double damage) {
            this.damage = damage;
            return this;
        }

        /**
         * 
         * @param radius
         * @return builder
         */
        public ProjectileBuilder withRadius(double radius) {
            this.radius = radius;
            return this;
        }

        /**
         * 
         * @param speed
         * @return builder
         */
        public ProjectileBuilder withSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        /**
         * 
         * @param owner
         * @return builder
         */
        public ProjectileBuilder withOwner(Player owner) {
            this.owner = owner;
            return this;
        }

        /**
         * 
         * @param target
         * @return builder
         */
        public ProjectileBuilder withTarget(Player target) {
            this.target = target;
            return this;
        }

        /**
         * 
         * @param aimX
         * @param aimY
         * @return a new projectile for use by game
         */
        public Projectile build(double aimX, double aimY) {
            double aimSide = aimX / Math.sqrt(aimX * aimX + aimY * aimY);
            double aimUp = aimY / Math.sqrt(aimX * aimX + aimY * aimY);
            return new Projectile(aimSide, aimUp, this);
        }
    }
}
