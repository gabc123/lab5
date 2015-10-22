package GameObjects;

import Controller.GameStatsObservable;
import Controller.KeyboardController;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Player extends Physics {

    private boolean jetpackState = false;
    private String name;
    private Weapon weapon;
    private boolean didFire = false;
    private double health = 100;
    private GameStatsObservable gameStatsObservable;

    private Direction dir;
    private Direction aim;

    public Player(String name_, double x, double y, int modelId) {
        super(0, 0, 7, modelId); // should be stationary
        this.setX(x);
        this.setY(y);
        this.dir = Direction.NONE;
        this.aim = Direction.NONE;
        this.name = name_;
        this.weapon = new Weapon(this, ProjectileType.GRANADE, 0);
    }
    
    public void setGameStatsObservable(GameStatsObservable o){
        this.gameStatsObservable = o;
    }

    public void takeDamage(double damage) {
        health -= damage;
        System.out.println("player: " + this.name + " Health: " + this.health);
        if (health < 0) {
            this.deactivate();
        }
        this.gameStatsObservable.checkUIInfo();
    }
    
    public double currentHealth() {
        return this.health;
    }

    public int currentAmmo() {
        return this.weapon.getAmmo();
    }
    
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        switch (this.dir) {
            case LEFT:
                super.addToDx(-5);
                weapon.setAimX(dir);
                break;
            case RIGHT:
                super.addToDx(5);
                weapon.setAimX(dir);
                break;
        }
        weapon.setAimY(aim);
        weapon.update(frameDelta, spawnedObj);
        if (this.jetpackState == true) {
            super.addToDy(-5);
        }
        super.update(frameDelta, spawnedObj);
        return true;
    }

    public void fireWeapon() {
        this.didFire = this.weapon.fire();
        /*System.out.println("player: " + getName()
                    + " Ammo: " + weapon.getAmmo()
                    + " cooldown: " + weapon.getCooldown()
            );*/
        if(this.didFire){
            this.gameStatsObservable.checkUIInfo();
        }
    }

    public void setJetpackState(boolean b) {
        this.jetpackState = b;
    }

    public void setDirection(Direction direction) {
        this.dir = direction;
    }
    
    public void setAim(Direction direction) {
        this.aim = direction;
    }

    @Override
    public void collisionWith(Physics gameObj) {
        if (gameObj instanceof SpawnBox) {
            SpawnBox box = (SpawnBox) gameObj;
            this.weapon = box.consumeBox(this);
            System.out.println("player: " + getName()
                    + " Ammo: " + weapon.getAmmo()
                    + " cooldown: " + weapon.getCooldown()
            );
        }
    }

    public String getName() {
        return this.name;
    }

    public void collisionWithTerrainAt(double x, double y) {
        // stop movement
        this.addToDx(-this.getDx());
        this.addToDy(-this.getDy());
        this.addToDx((this.getX() - x)/1);
        this.addToDy((this.getY() - y)/1);
    }

    public static class Playerinfo {

        private String playername;
        private KeyboardController keyInputs;

        public Playerinfo(String name, KeyboardController playerinput) {
            this.playername = name;
            this.keyInputs = playerinput;
        }

        public void setPlayerName(String name_) {
            this.playername = name_;
        }

        public String getPlayerName() {

            return this.playername;
        }

        public KeyboardController getKeyboard() {
            return this.keyInputs;
        }
        
        public void setKeyboard(KeyboardController playerinput_) {
            this.keyInputs = playerinput_;
        }

    }
}
