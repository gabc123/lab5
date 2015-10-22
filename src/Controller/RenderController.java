/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameData.GraphicModels;
import GameData.Terrain;
import GameObjects.GameObject;
import GameTimers.RenderTimer.RenderingState;
import View.GameView;
import javafx.scene.image.Image;

/**
 *
 * @author o_0
 */
public class RenderController{
    private GameView gameView;
    private GraphicModels graphicModel;
    public RenderController(GameView view, GraphicModels graphicModel) {
        super();
        this.gameView = view;
        this.graphicModel = graphicModel;
    }
    
    public void displayTerrain(Terrain terrain) {
        gameView.drawterrain(terrain.getTerrainImage());
    }
    
    public void displayModel(GameObject obj,RenderingState state) {
        Image model = graphicModel.getModel(obj.getModelID(), 0);
        double scaling = state.getScaleFactor(model.getWidth(), model.getHeight());
        gameView.drawmodel(model, obj.getX(), obj.getY(), scaling);
    }
}
