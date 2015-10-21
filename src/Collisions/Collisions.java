/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collisions;

import GameData.Terrain;
import GameObjects.GameObject;
import GameObjects.Physics;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Collisions {

    private boolean checkIntersect(Physics objA, Physics objB) {
        double diffX = objA.getX() - objB.getX();
        double diffY = objA.getY() - objB.getY();
        
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);
        if((objA.getBodyRadius() + objB.getBodyRadius()) > distance) {
            return true;
        }
        return false;
    }
    
    private void checkCollisionWithObject(Physics objA, int startIndex, ArrayList<GameObject> gameObjects) {
        for (int j = startIndex + 1; j < gameObjects.size(); j++) {
            GameObject objB = gameObjects.get(j);
            if (!objB.physicsEnable()) {
                continue;
            }
            if(checkIntersect(objA, (Physics) objB)) {
                objA.collisionWith((Physics) objB);
                ((Physics) objB).collisionWith(objA);
            }
            
        }
    }

    public void checkCollisions(ArrayList<GameObject> gameObjects) {
        int size = gameObjects.size();
        for (int i = 0; i < size; i++) {
            GameObject objA = gameObjects.get(i);
            if (!objA.physicsEnable()) {
                continue;
            }
            checkCollisionWithObject((Physics) objA, i , gameObjects);
        }
    }
    
    public void checkTerrainCollisions(Terrain terrain, ArrayList<GameObject> gameObjects) {
        for(GameObject obj : gameObjects) {
            if(obj.physicsEnable()) {
                terrain.checkCollision((Physics) obj);
            }
        }
    }
}
