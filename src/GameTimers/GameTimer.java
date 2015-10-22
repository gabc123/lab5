/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTimers;

import Collisions.Collisions;
import Controller.GameController;
import GameData.Terrain;
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
public class GameTimer extends AnimationTimer {

    public static final double BILLION = 1000_000_000.0; //from Ball lab2b
    private long lastTime;
    GameController gameController;
    private double gametime;
    public GameTimer(GameController gameController) {
        super();
        this.gameController = gameController;
    }


    @Override
    public void handle(long now) {

        double frameDelta = (now - lastTime) / BILLION;
        frameDelta = (frameDelta < 1) ? frameDelta : 0;
        lastTime = now;

        ArrayList<GameObject> inActiveObj;
        inActiveObj = gameController.removeInactiveObjects();
        gameController.removeObservers(inActiveObj);
        
        gameController.gameCollisionUpdate();
        
        ArrayList<GameObject> newObjects;
        newObjects = gameController.updateGame(frameDelta);
        
        gameController.addObjects(newObjects);
        gameController.addObservers(newObjects);

    }

}
