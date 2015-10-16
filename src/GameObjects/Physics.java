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
public abstract class Physics extends GameObject {
    private double dx;
    private double dy;
    
    protected double getDx() {return dx;}    
    protected double getDy() {return dy;}    
    protected void addToDx(double dx) {this.dx += dx;}    
    protected void addToDy(double dy) {this.dy += dy;}
    
    protected Physics(double dx, double dy,int modelId) {
        super(modelId);
        this.dx = dx;
        this.dy = dy;
    }
    
    protected void updateGravity(double frameDelta) {
        this.dy = 1.0*frameDelta;
    }
    
    @Override
    public void update(double frameDelta) {
        //updateGravity(frameDelta);
        this.setX(this.getX() + dx*frameDelta);
        this.setY(this.getY() + dy*frameDelta);
    }
}
