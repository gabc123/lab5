/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameData.GraphicModels;
import GameObjects.GameObject;
import View.GameView;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

/**
 *
 * @author o_0
 */
public class GameRender extends AnimationTimer {
    private ArrayList<GameObject> gameObjects;
    private GameView gameView;
    GraphicModels graphicModel;
    
    public GameRender(GameView view, GraphicModels graphicModel, ArrayList<GameObject> gameObj) {
        super();
        this.gameObjects = gameObj;
        this.gameView = view;
        this.graphicModel = graphicModel;
    }
    
    @Override
    public void handle(long now) {
        gameView.drawbackground();
        for(GameObject obj : gameObjects) {
            Image img = graphicModel.getModel(obj.getModelID(), 0);
            gameView.drawmodel(img, obj.getX(), obj.getY());
        }
    }
}
