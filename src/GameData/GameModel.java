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
import GameObjects.Player;
import GameObjects.Projectile;
import GameObjects.ProjectileType;
import GameObjects.SpawnBox;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author o_0
 */
public class GameModel {

    public static final double BILLION = 1000_000_000.0; //from Ball lab2b
    private long lastTime;
    private ArrayList<GameObject> gameObjects;
    private ArrayList<Ai> aiPlayers;
    private Collisions collisions;
    private Terrain terrain;
    private double heigth = 10;
    private double width = 10;
    private ArrayList<Player> currentPlayers;
    private Random rand;

    public GameModel(double width, double heigth, ArrayList<GameObject> gameObj, ArrayList<Ai> aiPlayers, Collisions collisions, Terrain terrain) {
        super();
        this.width = width;
        this.heigth = heigth;
        this.gameObjects = gameObj;
        this.collisions = collisions;
        this.terrain = terrain;
        this.aiPlayers = aiPlayers;
        this.rand = new Random();
        this.currentPlayers = new ArrayList<Player>();
        this.findeplayers();
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

    private void findeplayers() {
        for (GameObject obj : this.gameObjects) {
            if (obj instanceof Player) {
                currentPlayers.add((Player) obj);
            }
        }
    }

    public boolean deathCheck() {
        for (Player player : currentPlayers) {
            if (!player.isActive()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<GameObject> reSpawnPlayers() {
        ArrayList<GameObject> respawned = new ArrayList<GameObject>();
        for (Player player : currentPlayers) {
            if (!player.isActive()) {
                double newX = rand.nextDouble() * 1000;
                double newY = rand.nextDouble() * 50;
                player.resetPlayer(newX, newY, 100);
                respawned.add(player);
            }
        }
        return respawned;
    }

    public GameObject spawnBox() {
        double newX = rand.nextDouble() * 1000;
        double newY = rand.nextDouble() * 50;

        SpawnBox box;
        int type = rand.nextInt(3);
        switch (type) {
            case 0:
                box = new SpawnBox(ProjectileType.GRANADE, newX, newY);
                break;
            case 1:
                box = new SpawnBox(ProjectileType.BULLET, newX, newY);
                break;
            case 3:
                box = new SpawnBox(ProjectileType.MISSILE, newX, newY);
                break;
            default:
                box = new SpawnBox(ProjectileType.MISSILE, newX, newY); break;

        }
        return box;
    }

    public void checkCollisions() {
        collisions.checkAllCollisions();
        collisions.checkTerrainCollisions();
    }

    public void updateAiPlayers(double frameDelta) {
        for (Ai ai : aiPlayers) {
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
