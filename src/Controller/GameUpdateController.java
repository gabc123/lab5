/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.GameObject;
import GameObjects.Physics;
import GameObjects.Projectile;
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
    private ExplosionObserver explosionObserver;

    public GameUpdateController(ArrayList<GameObject> gameObj) {
        super();
        this.gameObjects = gameObj;
        explosionObserver = new ExplosionObserver();
        for (GameObject obj : gameObj) {
            if (obj.physicsEnable()) {
                explosionObserver.addObserver((Physics) obj);
            }
        }
    }

    private void addObservers(ArrayList<GameObject> spawnedObj) {
        for (GameObject obj : spawnedObj) {
            if (obj.physicsEnable()) {
                explosionObserver.addObserver((Physics) obj);
            }
        }
    }

    private void removeObservers(GameObject obj) {
        if (obj.physicsEnable()) {
            explosionObserver.deleteObserver((Physics) obj);
        }

        if (obj instanceof Projectile) {
            explosionObserver.explode((Projectile) obj);
        }
    }

    @Override
    public void handle(long now) {

        double frameDelta = (now - lastTime) / BILLION;
        frameDelta = (frameDelta < 1) ? frameDelta : 0;
        lastTime = now;

        // removes all inactive objects
        Iterator<GameObject> it = gameObjects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            if (!obj.isActive()) {
                removeObservers(obj);
                it.remove();
            }
        }
        
        ArrayList<GameObject> removeObj = new ArrayList<GameObject>();
        ArrayList<GameObject> spawnedObj = new ArrayList<GameObject>();
        for (GameObject obj : gameObjects) {
            obj.update(frameDelta, spawnedObj);

        }
        // adds all spawned objects
        if (!spawnedObj.isEmpty()) {
            gameObjects.addAll(spawnedObj);
            addObservers(spawnedObj);
        }

    }

}
