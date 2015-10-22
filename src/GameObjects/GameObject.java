/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

import java.util.ArrayList;

/**
 * The main gameObject in the game, this absractclass is the base for all objects in game
 * @author o_0
 */
public abstract class GameObject {
    private int modelID;
    private boolean active;
    //private boolean physicsEnable = false;
    private double x = 0.0;
    private double y = 0.0;
    private double bodySize = 0.0;
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    protected void setX(double x){this.x = x;}
    protected void setY(double y){this.y = y;}
    public double getBodySize() {return this.bodySize;}
    protected void setBodySize(double bodySize) {this.bodySize = bodySize;}
    /**
     * 
     * @param modelId what model it has
     */
    protected GameObject(int modelId) {
        this.modelID = modelId;
        this.active = true;
    }
    /**
     * This is called every frame by gameTimer
     * @param frameDelta fraction of a seconde since last update
     * @param spawnedObj list to store all newly created objects
     * @return not used
     */
    public abstract boolean update(double frameDelta, ArrayList<GameObject> spawnedObj);
    /**
     * 
     * @return true if this unit is active
     */
    public boolean isActive() {
        return this.active;
    }
    
    /**
     * set this object to inactive
     */
    protected void deactivate() {
        this.active = false;
    }
    /**
     * reactivate object
     */
    protected void reActivate(){
        this.active = true;
    }
    
    /**
     * 
     * @return if this is a physics object, so the more expencive instanceof do not
     * need to be used
     */
    public boolean physicsEnable() { return false;};
    
    /**
     * 
     * @return the modelid
     */
    public int getModelID() {
        return this.modelID;
    }
    /**
     * Sets a new modelId
     * @param modelId the new modelId
     */
    public void setModelID(int modelId) {
        this.modelID = modelId;
    }
    
    /**
     * Constrain the GameObject insde the height and width
     * @param w width
     * @param h height
     */
    public void constrain(double w, double h) {
        if(x < 0) {
            this.setX(1);
        }else if(w < x) {
            this.setX(w - 1);
        }
        if(y < 0) {
            this.setY(1);
        }else if(h < y) {
            this.setY(h - 1);
        }
    }
}
