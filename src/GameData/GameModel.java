/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameData;

import Collisions.Collisions;
import GameData.Terrain;
import GameObjects.GameObject;
import GameObjects.Physics;
import GameObjects.Projectile;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author o_0
 */
public class GameModel {
    
    public static final double BILLION = 1000_000_000.0; //from Ball lab2b
    private long lastTime;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Ai> aiPlayers;
    Collisions collisions;
    private Terrain terrain;
    private double heigth = 10;
    private double width = 10;
    
    public GameModel(double width,double heigth,ArrayList<GameObject> gameObj,ArrayList<Ai> aiPlayers,Collisions collisions,Terrain terrain) {
        super();
        this.width = width;
        this.heigth = heigth;
        this.gameObjects = gameObj;
        this.collisions = collisions;
        this.terrain = terrain;
        this.aiPlayers = aiPlayers;
    }
    
    public ArrayList<GameObject> reapInactiveObjects() {
        ArrayList<GameObject> removed = new ArrayList<GameObject>();
        // removes all inactive objects
        Iterator<GameObject> it = gameObjects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            if (!obj.isActive()) {
                //removeObservers(obj);
                removed.add(obj);
                it.remove();
            }
        }
        return removed;
    }

    public void checkCollisions() {
         collisions.checkAllCollisions();
        collisions.checkTerrainCollisions();
    }
    
    public void updateAiPlayers(double frameDelta) {
        for(Ai ai : aiPlayers) {
            ai.updateAi(frameDelta);
        }
    }
    
    public ArrayList<GameObject> updateGameobjects(double frameDelta) {
        ArrayList<GameObject> spawnedObj = new ArrayList<GameObject>();
        for (GameObject obj : gameObjects) {
            obj.update(frameDelta, spawnedObj);
            obj.constrain(width, heigth);
        }
        return spawnedObj;
    }
    
    public void addObjects(ArrayList<GameObject> newObjects) {
        // adds all spawned objects
        if (!newObjects.isEmpty()) {
            gameObjects.addAll(newObjects);
        }
    }
    
}
