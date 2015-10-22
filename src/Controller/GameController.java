
package Controller;

import GameObjects.GameObject;
import GameObjects.Physics;
import GameData.GameModel;
import java.util.ArrayList;

/**
 * The gameController for the game, it ties the gameModel and ExplosionObserver together
 * @author o_0
 */
public class GameController {

    private GameModel gameModel;
    private ExplosionObserver explosionObserver;
    private GameStatsObservable gameStatsObservable;

    /**
     * 
     * @param gameModel the gameModel, that has all gamelogic
     * @param explosionObserver the explosion tracker
     */
    public GameController(GameModel gameModel, ExplosionObserver explosionObserver) {
        this.gameModel = gameModel;
        this.explosionObserver = explosionObserver;
        this.gameStatsObservable = gameStatsObservable;
    }

    /**
     * Add more observers to the
     * @param newObservers explosionObserver
     */
    public void addObservers(ArrayList<GameObject> newObservers) {
        for (GameObject obj : newObservers) {
            if (obj.physicsEnable()) {
                explosionObserver.addObserver((Physics) obj);
            }
        }
    }

    /**
     * Used to remove a observer from the explosionObserver
     * @param obj object to remove
     */
    private void removeObserver(GameObject obj) {
        if (!obj.physicsEnable()) {
            return;
        }
        explosionObserver.checkTrigger(obj);
        explosionObserver.deleteObserver((Physics) obj);
    }

    /**
     * Removes all inActive objects from the explosionObserver
     * @param inActiveObs 
     */
    public void removeObservers(ArrayList<GameObject> inActiveObs) {
        if (inActiveObs.isEmpty()) {
            return;
        }
        for (GameObject observer : inActiveObs) {
            removeObserver(observer);
        }
    }

    /**
     * Returns a list of all newly inactive gameObjects
     * @return a list of all newly inactive gameObjects
     */
    public ArrayList<GameObject> removeInactiveObjects() {
        return gameModel.reapInactiveObjects();
    }

    /**
     * Tells the model to do all collisionChecks
     */
    public void gameCollisionUpdate() {
        gameModel.checkCollisions();
    }

    /**
     * Updates all game data, and logic
     * @param frameDelta the fraction of a seconde that has passed sence last update
     * @return all new objects that has spawned this updateCycle
     */
    public ArrayList<GameObject> updateGame(double frameDelta) {
        gameModel.updateAiPlayers(frameDelta);
        return gameModel.updateGameobjects(frameDelta);
    }

    /**
     * Adds objects to the gameModel
     * @param newObjects 
     */
    public void addObjects(ArrayList<GameObject> newObjects) {
        gameModel.addObjects(newObjects);
    }

    /**
     * Respawns all inactive players, (if they died or something)
     */
    public void playerRespawn() {
        if (gameModel.deathCheck()) {
            ArrayList<GameObject> respawned;
            respawned = gameModel.reSpawnPlayers();
            this.addObjects(respawned);
            this.addObservers(respawned);
        }

    }

    /**
     * Creates a new spawnBox
     * @return returns a spawnBox, with random stuff according to the model
     */
    public GameObject makeSpawnBox() {
        return gameModel.spawnBox();
    }

    /**
     * Checks if a player has been inactive
     * @return true if someone is inactive
     */
    public boolean deathcheck() {
        return gameModel.deathCheck();
    }
}
