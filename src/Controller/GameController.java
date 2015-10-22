/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.GameObject;
import GameObjects.Physics;
import GameData.GameModel;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class GameController {
    private GameModel gameModel;
    private ExplosionObserver explosionObserver;
    public GameController(GameModel gameModel,ExplosionObserver explosionObserver) {
        this.gameModel = gameModel;
        this.explosionObserver = explosionObserver;
    }
    
    public void addObservers(ArrayList<GameObject> newObservers) {
        for (GameObject obj : newObservers) {
            if (obj.physicsEnable()) {
                explosionObserver.addObserver((Physics) obj);
            }
        }
    }

    private void removeObserver(GameObject obj) {
        if (!obj.physicsEnable()) {return;}
        explosionObserver.checkTrigger(obj);
        explosionObserver.deleteObserver((Physics) obj);    
    }
    
    public void removeObservers(ArrayList<GameObject> inActiveObs) {
        if(inActiveObs.isEmpty()) { return;}
        for(GameObject observer : inActiveObs) {
            removeObserver(observer);
        }
    }
    
    public ArrayList<GameObject> removeInactiveObjects() {
        return gameModel.reapInactiveObjects();
    }

    public void gameCollisionUpdate() {
        gameModel.checkCollisions();
    }
    
    public ArrayList<GameObject> updateGame(double frameDelta) {
        gameModel.updateAiPlayers();
        return gameModel.updateGameobjects(frameDelta);
    }
    
    public void addObjects(ArrayList<GameObject> newObjects) {
        gameModel.addObjects(newObjects);
    }
}
