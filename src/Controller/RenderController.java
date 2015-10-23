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
 * This is the controller that ties the view and the graphicModel together
 * This is where all things that needs to be rendered passes thru
 * @author o_0
 */
public class RenderController{
    private GameView gameView;
    private GraphicModels graphicModel;
    /**
     * 
     * @param view the view used to render the scene
     * @param graphicModel contains all models in use, and animation data
     */
    public RenderController(GameView view, GraphicModels graphicModel) {
        super();
        this.gameView = view;
        this.graphicModel = graphicModel;
    }
    /**
     * will trigger the view to render the terrain
     * @param terrain to be rendered
     */
    public void displayTerrain(Terrain terrain) {
        gameView.drawterrain(terrain.getTerrainImage());
    }
    /**
     * will trigger the view to render the one object
     * @param obj the object to be rendered
     * @param state state data used for scaling, and keyframe animation info
     */
    public void displayModel(GameObject obj,RenderingState state) {
        Image model = graphicModel.getModel(obj.getModelID(), 0);
        double scaling = state.getScaleFactor(model.getWidth(), model.getHeight());
        gameView.drawmodel(model, obj.getX(), obj.getY(), scaling);
    }
}
