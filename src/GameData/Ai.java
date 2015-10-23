/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameData;

import GameObjects.Direction;
import GameObjects.GameObject;
import GameObjects.Player;
import GameObjects.SpawnBox;
import java.util.ArrayList;
import javafx.geometry.Point2D;

/**
 * the main ai
 * @author o_0
 */
public class Ai {

    private Player aiPlayer;
    private ArrayList<Player> enemyList;
    private ArrayList<GameObject> gameObjects;
    private double gameTime = 0;
    private Point2D collisionPoint;
    private Point2D destination;
    private double fireDelay;

    private enum AiState {

        HUNT, SEARCH, AVOID_TERRAIN, GETSPAWNBOX,MOVETOPOINT
    };

    private class StateData {

        private AiState state;
        private double timeStamp;

        protected StateData(AiState state, double timeStamp) {
            this.state = state;
            this.timeStamp = timeStamp;
        }

        protected AiState getState() {
            return this.state;
        }

        protected double getTimeStamp() {
            return this.timeStamp;
        }
        protected void setTimeStamp(double time) {
            this.timeStamp = time;
        }
    }

    private ArrayList<StateData> stateStack = null;
    private GameObject target = null;

    public Ai(Player player, ArrayList<Player> enemyList, ArrayList<GameObject> gameObjects) {
        this.aiPlayer = player;
        this.enemyList = enemyList;
        this.gameObjects = gameObjects;
        this.collisionPoint = new Point2D(0, 0);
        this.destination = new Point2D(0, 0);
        this.target = null;
        this.stateStack = new ArrayList<StateData>();
        this.stateStack.add(new StateData(AiState.HUNT, 0));

    }

    private void moveTo(double destX, double destY) {
        if (destY > 0) {
            aiPlayer.setJetpackState(true);
        } else {
            aiPlayer.setJetpackState(false);
        }
        double maxX = Math.abs(aiPlayer.currentDx());
        /*if(maxX > 10) {
            aiPlayer.setDirection(Direction.NONE);
        }*/
        if (destX > 0 && aiPlayer.currentDx() < 10) {
            aiPlayer.setDirection(Direction.LEFT);
        } else if (aiPlayer.currentDx() > -10) {
            aiPlayer.setDirection(Direction.RIGHT);
        } else {
            aiPlayer.setDirection(Direction.NONE);
        }
        
    }

    private void moveToPoint() {
        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        StateData stateData = stateStack.get(index);
        double diffX = Math.abs(aiPlayer.getX() - destination.getX());
        double diffY = Math.abs(aiPlayer.getY() - destination.getY());
        if(diffX + diffY < 10 || gameTime - stateData.timeStamp > 1 ){
            stateStack.remove(index);
            return;
        }
        moveTo(destination.getX(),destination.getY());
    }
    
    private void huntTarget() {

        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        if (this.target == null && stateStack.get(index).getState() != AiState.SEARCH) {
            stateStack.add(new StateData(AiState.SEARCH, gameTime));
            return;
        }
        StateData stateData = stateStack.get(index);
        double timeDiff = this.gameTime - stateData.timeStamp;
        if(timeDiff > 20 ) {
            stateData.setTimeStamp(gameTime); // reset this state clock
            this.destination = new Point2D(1000*Math.random(),1000*Math.random());
            stateStack.add(new StateData(AiState.MOVETOPOINT, gameTime));
            return;
        }
        double aiLocX = aiPlayer.getX();
        double aiLocY = aiPlayer.getY();
        double threatX = 0;
        double threatY = 0;
        for (Player enemy : enemyList) {
            threatX += aiLocX - enemy.getX();
            threatY += aiLocY - enemy.getY();
        }
        if(threatY > 30) {
            aiPlayer.setAim(Direction.UP);
        }else if(threatY < -30){
            aiPlayer.setAim(Direction.DOWN);
        }else{
            aiPlayer.setAim(Direction.NONE);
        }
        if(this.fireDelay > 1.0) {
            aiPlayer.fireWeapon();
            this.fireDelay = 0;
        }
        
        
        moveTo(threatX, threatY);
    }

    /**
     * state enging
     */
    private void moveToSpawnBox() {
        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        StateData stateData = stateStack.get(index);
        double timeDiff = this.gameTime - stateData.timeStamp;
        if (timeDiff > 5) {
            stateStack.remove(index);
            return;
        }
        if (target == null) {
            stateStack.remove(index);
            return;
        }
        moveTo(target.getX(), target.getY());
    }

    /**
     * 
     * @return if it found a box
     */
    private boolean searchSpawnBox() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof SpawnBox) {
                this.target = obj;
                return true;
            }
        }
        return false;
    }

    /**
     * serch for target
     */
    private void searchTarget() {
        double distance = 10000;
        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        if (aiPlayer.currentAmmo() < 4) {
            if(searchSpawnBox()) {
                stateStack.set(index, new StateData(AiState.GETSPAWNBOX, gameTime));
                return;
            }

        }
        for (Player enemy : enemyList) {
            double threatX = aiPlayer.getX() - enemy.getX();
            double threatY = aiPlayer.getY() - enemy.getY();
            double tmpDistance = Math.sqrt(threatX * threatX + threatY * threatY);
            if (tmpDistance < distance) {
                this.target = enemy;
                distance = tmpDistance;
            }
        }
        if (this.target != null) {
            stateStack.remove(index);
        }

    }

    /**
     * avoid terain
     */
    private void avoidTerrain() {
        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        StateData stateData = stateStack.get(index);
        double timeDiff = this.gameTime - stateData.timeStamp;
        
        // timed out on this operation
        if (timeDiff > 0.5 ||  Math.abs(aiPlayer.currentDx()) > 15
                || Math.abs(aiPlayer.currentDy()) > 15) {
            stateStack.remove(index);
            return;
        }
        double diffX = aiPlayer.getX() - collisionPoint.getX();
        double diffY = aiPlayer.getY() - collisionPoint.getY();
        moveTo(aiPlayer.getX() + 2*diffX, aiPlayer.getY() + 2*diffY);

    }

    /**
     * collision point
     * @param x 
     * @param y 
     */
    public void collisionWithTerrainAt(double x, double y) {
        this.collisionPoint = new Point2D(x, y);
        int index = stateStack.size() - 1;
        if (index >= 0 && stateStack.get(index).getState() != AiState.AVOID_TERRAIN) {
            StateData stateData = new StateData(AiState.AVOID_TERRAIN, gameTime);
            stateStack.add(stateData);
        }

    }

    /**
     * called if call
     */
    public void pickedupSpawnBox() {
        int index = stateStack.size() - 1;
        if (index < 0) {
            return;
        }
        if(stateStack.get(index).state == AiState.GETSPAWNBOX){
            stateStack.remove(index);
        }
    }

    /**
     * update ai state machine
     * @param frameDelta 
     */
    public void updateAi(double frameDelta) {
        this.gameTime += frameDelta;
        this.fireDelay +=frameDelta;
        if (stateStack.isEmpty()) {
            stateStack.add(new StateData(AiState.HUNT, 0));
        }
        int index = stateStack.size() - 1;
        StateData stateData = stateStack.get(index);
        switch (stateData.state) {
            case HUNT:
                huntTarget();
                break;
            case AVOID_TERRAIN:
                avoidTerrain();
                break;
            case SEARCH:
                searchTarget();
                break;
            case GETSPAWNBOX: moveToSpawnBox(); break;
            case MOVETOPOINT: moveToPoint();break;
            default:
                huntTarget();
                break;
        }

    }
}
