/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTimers;

import Controller.RenderController;
import GameData.Terrain;
import GameObjects.GameObject;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;

/**
 *
 * @author o_0
 */
public class RenderTimer extends AnimationTimer {

    private ArrayList<GameObject> gameObjects;
    RenderController renderController;
    private Terrain terrain;

    public RenderTimer(RenderController renderController, ArrayList<GameObject> gameObj, Terrain terrain) {
        super();
        this.gameObjects = gameObj;
        this.terrain = terrain;
        this.renderController = renderController;
    }

    @Override
    public void handle(long now) {
        renderController.displayTerrain(terrain);

// due to GameUpdate controller ability to add/remmove from gameObjects, we need a clone
        ArrayList<GameObject> localbuffer = (ArrayList<GameObject>) gameObjects.clone();
        for (GameObject obj : localbuffer) {
            renderController.displayModel(obj);
        }
    }
}
