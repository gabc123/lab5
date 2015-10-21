/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTimers;

import Controller.RenderController;
import GameData.Terrain;
import GameObjects.GameObject;
import static GameTimers.GameTimer.BILLION;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 *
 * @author o_0
 */
public class RenderTimer extends AnimationTimer {

    private ArrayList<GameObject> gameObjects;
    RenderController renderController;
    private Terrain terrain;
    long lastTime = 0;
    double gameTime = 0;
    public RenderTimer(RenderController renderController, ArrayList<GameObject> gameObj, Terrain terrain) {
        super();
        this.gameObjects = gameObj;
        this.terrain = terrain;
        this.renderController = renderController;
    }

    @Override
    public void handle(long now) {
        renderController.displayTerrain(terrain);
        double frameDelta = (now - lastTime) / BILLION;
        frameDelta = (frameDelta < 1) ? frameDelta : 0;
        lastTime = now;
        gameTime += frameDelta;
// due to GameUpdate controller ability to add/remmove from gameObjects, we need a clone
        ArrayList<GameObject> localbuffer = (ArrayList<GameObject>) gameObjects.clone();
        for (GameObject obj : localbuffer) {
            RenderingState state = new RenderingState(obj,gameTime);
            renderController.displayModel(obj,state);
        }
    }
    
    public class RenderingState {
        private double timePassed;
        private double radius;
        public RenderingState(GameObject obj,double time) {
            this.timePassed = time;
            this.radius = obj.getBodySize();
            radius = (radius >= 0) ? radius : 0;
        }
        
        public double getTimeCount() {
            return this.timePassed;
        }
        public double getScaleFactor(double width,double heigth) {
            double maxValue = Math.max(width, width);
            if(this.radius <= 0 || maxValue <= 0) {
                return 1;
            }
            return this.radius/maxValue;
        }
    }
}
