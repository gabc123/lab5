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
public abstract class GameObject {
    private int modelID;
    private boolean active;
    //private boolean physicsEnable = false;
    private double x = 0.0;
    private double y = 0.0;
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    protected void setX(double x){this.x = x;}
    protected void setY(double y){this.y = y;}
    protected GameObject(int modelId) {
        this.modelID = modelId;
        this.active = true;
    }
    public abstract boolean update(double frameDelta, ArrayList<GameObject> spawnedObj);
    
    public boolean isActive() {
        return this.active;
    }
    
    protected void deactivate() {
        this.active = false;
    }
    
    public boolean physicsEnable() { return false;};
    
    public int getModelID() {
        return this.modelID;
    }
    public void setModelID(int modelId) {
        this.modelID = modelId;
    }
}
