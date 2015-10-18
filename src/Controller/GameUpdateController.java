/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.GameObject;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.animation.AnimationTimer;

/**
 *
 * @author o_0
 */
public class GameUpdateController extends AnimationTimer {
    public static final double BILLION = 1000_000_000.0; //from Ball lab2b
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
        
        // removes all inactive objects
        Iterator<GameObject> it = gameObjects.iterator();
        while(it.hasNext()) {
            GameObject obj = it.next();
            if(!obj.isActive()) {
                it.remove();
            }
        }
        
        ArrayList<GameObject> removeObj = new ArrayList<GameObject>();
        ArrayList<GameObject> spawnedObj = new ArrayList<GameObject>();
        for(GameObject obj : gameObjects) {
            obj.update(frameDelta, spawnedObj);
            
        }
        // adds all spawned objects
        if(!spawnedObj.isEmpty()) {
            gameObjects.addAll(spawnedObj);
        }
        
        
    }
    
}
