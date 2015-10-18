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
public abstract class Physics extends GameObject {

    private double dx;
    private double dy;
    private boolean gravityAcitve = true;

    protected double getDx() {
        return dx;
    }

    protected double getDy() {
        return dy;
    }

    protected void addToDx(double dx) {
        this.dx += dx;
    }

    protected void addToDy(double dy) {
        this.dy += dy;
    }

    protected Physics(double dx, double dy, int modelId) {
        super(modelId);
        this.dx = dx;
        this.dy = dy;
    }

    protected void setGravity(boolean isOn) {
        this.gravityAcitve = isOn;
    }

    protected void updateGravity(double frameDelta) {
        this.dy = 1.0 * frameDelta;
    }

    @Override
    public boolean update(double frameDelta, ArrayList<GameObject> spawnedObj) {
        if (this.gravityAcitve) {
            updateGravity(frameDelta);
        }
        this.setX(this.getX() + dx * frameDelta);
        this.setY(this.getY() + dy * frameDelta);
        return true;
    }
}
