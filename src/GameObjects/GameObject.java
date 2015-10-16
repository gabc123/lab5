/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObjects;

/**
 *
 * @author o_0
 */
public abstract class GameObject {
    private int modelID;
    public abstract double getX();
    public abstract double getY();
    protected abstract void setX(double x);
    protected abstract void setY(double y);
    protected GameObject(int modelId) {
        this.modelID = modelId;
    }
    public abstract void update(double frameDelta);
    
    public int getModelID() {
        return this.modelID;
    }
    public void setModelID(int modelId) {
        this.modelID = modelId;
    }
}
