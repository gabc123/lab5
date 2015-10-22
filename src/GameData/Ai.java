/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameData;

import GameObjects.Direction;
import GameObjects.GameObject;
import GameObjects.Player;
import java.util.ArrayList;

/**
 *
 * @author o_0
 */
public class Ai {
    private Player aiPlayer;
    private ArrayList<Player> enemyList;
    private ArrayList<GameObject> gameObjects;
    public Ai(Player player,ArrayList<Player> enemyList,ArrayList<GameObject> gameObjects) {
        this.aiPlayer = player;
        this.enemyList = enemyList;
        this.gameObjects = gameObjects;
    }
    
    public void updateAi() {
        double aiLocX = aiPlayer.getX();
        double aiLocY = aiPlayer.getY();
        double threatX = 0;
        double threatY = 0;
        for(Player enemy : enemyList) {
            threatX += aiLocX - enemy.getX();
            threatY += aiLocY - enemy.getY();
        }
        
        if(threatY > 0) {
            aiPlayer.setJetpackState(true);
        }else {
            aiPlayer.setJetpackState(false);
        }
        
        if(threatX > 0) {
            aiPlayer.setDirection(Direction.LEFT);
        }else {
            aiPlayer.setDirection(Direction.RIGHT);
        }
        
        
    }
}
