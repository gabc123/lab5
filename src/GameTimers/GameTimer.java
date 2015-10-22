/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTimers;

import Controller.GameController;
import GameObjects.GameObject;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 *Gametimer keeps track of time during the game and calls 
 * functions reliant on timed actions.
 * @author o_0
 */
public class GameTimer extends AnimationTimer {

    public static final double BILLION = 1000_000_000.0; //from Ball lab2b
    private long lastTime;
    GameController gameController;
    private double gametime = 0;
    private double savetime = 0;
    private double spawnTime = 0;

    /**
     * Constructor
     * @param gameController takes a gameController as input
     * since the methods are needed
     */
    public GameTimer(GameController gameController) {
        super();
        this.gameController = gameController;
    }

    @Override
    /**
     * handle keeps track of time and calls methods from gameController.
     * handle removes and adds objects, spawn players.
     */
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
