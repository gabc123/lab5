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
    public abstract double getX();
    public abstract double getY();
    protected abstract void setX(double x);
    protected abstract void setY(double y);
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
    
    public int getModelID() {
        return this.modelID;
    }
    public void setModelID(int modelId) {
        this.modelID = modelId;
    }
}
