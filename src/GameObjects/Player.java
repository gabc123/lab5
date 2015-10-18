
package GameObjects;

import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Player extends Physics{
    private double x;
    private double y;
    private boolean jetpackState = false;
    
    
    private Direction dir;
    
    public Player(double x,double y,int modelId) {
        super(0, 0, modelId); // should be stationary
        this.x = x;
        this.y = y;
        this.dir = Direction.NONE;
    }
    
    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        switch(this.dir) {
            case LEFT: super.addToDx(-1); break;
            case RIGHT: super.addToDx(1); break;
            case DOWN: super.addToDy(1); break;
            case UP: super.addToDy(-1); break;
            case NONE:  break;
            default:break;
        }
        
        if(this.jetpackState == true){
            super.addToDy(1);
        }
        super.update(frameDelta,spawnedObj);
        return true;
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
    
}
