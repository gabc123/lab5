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
    private double gametime = 0;
    private double savetime = 0;
    private double spawnTime = 0;

    public GameTimer(GameController gameController) {
        super();
        this.gameController = gameController;
    }

    @Override
    public void handle(long now) {

        double frameDelta = (now - lastTime) / BILLION;
        frameDelta = (frameDelta < 1) ? frameDelta : 0;
        lastTime = now;
        this.gametime += frameDelta;
        spawnTime += frameDelta;
        ArrayList<GameObject> inActiveObj;
        inActiveObj = gameController.removeInactiveObjects();
        gameController.removeObservers(inActiveObj);

        gameController.gameCollisionUpdate();

        ArrayList<GameObject> newObjects;
        newObjects = gameController.updateGame(frameDelta);
        if(spawnTime > 10) {
            newObjects.add(gameController.makeSpawnBox());
            spawnTime = 0;
        }
        if (gameController.deathcheck()) {
            savetime += frameDelta;
            if (savetime > 2) {
                gameController.playerRespawn();
                savetime = 0;
            }
        }
        gameController.addObjects(newObjects);
        gameController.addObservers(newObjects);

    }

}
