package GameObjects;

import Controller.KeyboardController;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Player extends Physics {

    private double x;
    private double y;
    private boolean jetpackState = false;
    private String name;
    private Weapon weapon;
    private boolean didFire = false;

    private Direction dir;

    public Player(String name_, double x, double y, int modelId) {
        super(0, 0, modelId); // should be stationary
        this.x = x;
        this.y = y;
        this.dir = Direction.NONE;
        this.name = name_;
        this.weapon = new Weapon(this, ProjectileType.GRANADE, 0);
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
            case DOWN:
                //super.addToDy(1);
                break;
            case UP:
                //super.addToDy(-1);
                break;
            case NONE:
                break;
            default:
                break;
        }

        weapon.setAimY(dir);

        weapon.update(frameDelta, spawnedObj);
        if (this.jetpackState == true) {
            super.addToDy(-15);
        }
        super.update(frameDelta, spawnedObj);
        return true;
    }

    public void fireWeapon() {
        this.didFire = this.weapon.fire();
    }

    public void setJetpackState(boolean b) {
        this.jetpackState = b;
    }

    public void setDirection(Direction direction) {
        this.dir = direction;
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

    public static class Playerinfo {

        private String playername;
        private KeyboardController keyInputs;

        public Playerinfo(String name, KeyboardController playerinput) {
            this.playername = name;
            this.keyInputs = playerinput;
        }

        public String getPlayerName() {
            return this.playername;
        }

        public KeyboardController getKeyboard() {
            return this.keyInputs;
        }

    }
}
