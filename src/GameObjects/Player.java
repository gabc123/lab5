package GameObjects;

import Controller.GameStatsObservable;
import Controller.KeyboardController;
import GameData.Ai;
import java.util.ArrayList;

/**
 * the player that is controlled by an ai or a human
 * @author o_0
 */
public class Player extends Physics {

    private boolean jetpackState = false;
    private String name;
    private Weapon weapon;
    private boolean didFire = false;
    private double health = 100;
    private GameStatsObservable gameStatsObservable = null;
    private int numberofdeaths = 0;

    private Direction dir;
    private Direction aim;

    private Ai aiControl = null;

    /**
     * 
     * @param name_ name of player
     * @param x starting x position
     * @param y starting y position
     * @param modelId what model to use
     */
    public Player(String name_, double x, double y, int modelId) {
        super(0, 0, 7, modelId); // should be stationary
        this.setX(x);
        this.setY(y);
        this.dir = Direction.NONE;
        this.aim = Direction.NONE;
        this.name = name_;
        this.weapon = new Weapon(this, ProjectileType.GRANADE, 3);
    }

    /**
     * The Observable to be called if some player stats was changed
     * @param o the GameStatsObservable
     */
    public void setGameStatsObservable(GameStatsObservable o) {
        this.gameStatsObservable = o;
    }

    /**
     * set if an ai controls this player
     * @param aiControl the ai
     */
    public void setAiControl(Ai aiControl) {
        this.aiControl = aiControl;
    }

    /**
     * called to take damage on player, if player health < 0 it deactivets
     * @param damage how much
     */
    public void takeDamage(double damage) {
        health -= damage;
        System.out.println("player: " + this.name + " Health: " + this.health);
        if (health < 0) {
            this.deactivate();
            numberofdeaths++;
        }
        if (gameStatsObservable != null) {
            this.gameStatsObservable.checkUIInfo();
        }
    }
    
    /**
     * Rests the player and activates it with health
     * @param x x coordinate
     * @param y y coordinate
     * @param health what health to start witg
     */
    public void resetPlayer(double x, double y, double health) {
        this.setX(x);
        this.setY(y);
        this.weapon = new Weapon(this, ProjectileType.GRANADE, 3);
        this.health = health;
        this.reActivate();
        if (gameStatsObservable != null) {
            this.gameStatsObservable.checkUIInfo();
        }
    }
    
    /**
     * 
     * @return how manny times this player has died
     */
    public int getDeaths(){
        return this.numberofdeaths;
    }
    
    /**
     * Used by ai to get info
     *
     * @return the players dx
     */
    public double currentDx() {
        return this.getDx();
    }

    /**
     * Used by ai to get info
     *
     * @return the players dy
     */
    public double currentDy() {
        return this.getDy();
    }

    /**
     * getter
     * @return currentHealth
     */
    public double currentHealth() {
        return this.health;
    }

    /**
     * getter
     * @return currentAmmo
     */
    public int currentAmmo() {
        return this.weapon.getAmmo();
    }
    
    /**
     * getter
     * @return currentAimAngle
     */
    public double currentAimAngle() {
        return weapon.getAimAngle();
    }
/**
     * This is called every frame by gameTimer thru the controller
     * @param frameDelta fraction of a seconde since last update
     * @param spawnedObj list to store all newly created objects
     * @return not used
     */
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
        if (didFire && gameStatsObservable != null) {
            this.gameStatsObservable.checkUIInfo();
            didFire = false;
        }
        super.update(frameDelta, spawnedObj);
        return true;
    }

    /**
     * fires weapon
     */
    public void fireWeapon() {
        this.didFire = this.weapon.fire();
        /*System.out.println("player: " + getName()
         + " Ammo: " + weapon.getAmmo()
         + " cooldown: " + weapon.getCooldown()
         );*/
    }

    /**
     * if jetpack should be on or off
     * @param b on/off
     */
    public void setJetpackState(boolean b) {
        this.jetpackState = b;
    }

    /**
     * what direction the player moves
     * @param direction 
     */
    public void setDirection(Direction direction) {
        this.dir = direction;
    }

    /**
     * if he aims up or down
     * @param direction aim
     */
    public void setAim(Direction direction) {
        this.aim = direction;
    }

    /**
     * The player has been in a collision, with gameObj
     * @param gameObj gameObj
     */
    @Override
    public void collisionWith(Physics gameObj) {
        if (gameObj instanceof SpawnBox) {
            SpawnBox box = (SpawnBox) gameObj;
            this.weapon = box.consumeBox(this);

            if (this.aiControl != null) {
                this.aiControl.pickedupSpawnBox();
            }
        }
    }

    /**
     * players name
     * @return name in string
     */
    public String getName() {
        return this.name;
    }
    /**
     * The point it hit the terrain at
     * @param x point it hit the terrain at
     * @param y point it hit the terrain at
     */
    public void collisionWithTerrainAt(double x, double y) {
        // stop movement
        this.addToDx(-this.getDx());
        this.addToDy(-this.getDy());
        this.addToDx((this.getX() - x) / 1);
        this.addToDy((this.getY() - y) / 1);
        if (this.aiControl != null) {
            this.aiControl.collisionWithTerrainAt(x, y);
        }
    }

    /**
     * a class used for storage of player info from the lobby,
     */
    public static class Playerinfo {

        private String playername;
        private KeyboardController keyInputs;

        /**
         * 
         * @param name player name
         * @param playerinput input controller for this player
         */
        public Playerinfo(String name, KeyboardController playerinput) {
            this.playername = name;
            this.keyInputs = playerinput;
        }

        /**
         * 
         * @param name_ name
         */
        public void setPlayerName(String name_) {
            this.playername = name_;
        }

        /**
         * 
         * @return name of player
         */
        public String getPlayerName() {

            return this.playername;
        }

        /**
         * 
         * @return this players KeyboardController
         */
        public KeyboardController getKeyboard() {
            return this.keyInputs;
        }

        /**
         * 
         * @param playerinput_ set new KeyboardController for player
         */
        public void setKeyboard(KeyboardController playerinput_) {
            this.keyInputs = playerinput_;
        }

    }
}
