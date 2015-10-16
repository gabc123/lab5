/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.GameObject;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 *
 * @author o_0
 */
public class GameUpdateController extends AnimationTimer {
    public static final double BILLION = 100_000_000.0; //from Ball lab2b
    private long lastTime;
    private ArrayList<GameObject> gameObjects;
    
    public GameUpdateController(ArrayList<GameObject> gameObj) {
        super();
        this.gameObjects = gameObj;
    }
    
    @Override
    public void handle(long now) {
        double frameDelta = (now - lastTime)/BILLION;
        frameDelta = (frameDelta < 1) ? frameDelta : 0;
        lastTime = now;
        for(GameObject obj : gameObjects) {
            obj.update(frameDelta);
        }
    }
    
}
