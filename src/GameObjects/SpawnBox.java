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
public class SpawnBox extends Physics {
    private double x;
    private double y;

    public SpawnBox(double x, double y, int modelId) {
        super(0, 0, modelId);
        this.x = x;
        this.y = y;
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
