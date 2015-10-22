/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author mats
 */
public class GameView {
    
    private Canvas canvas;
    //private Canvas background;
    public GameView(Canvas canvas) {
        this.canvas = canvas;
    }
    
    public void drawbackground(Image background){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    public void drawmodel(Image model, double x, double y, double scalingFactor){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = model.getWidth() * scalingFactor;
        double height = model.getHeight() * scalingFactor;
        gc.drawImage(model, x - width/2, y - height/2, width, height);
    }
    
   
    public void drawterrain(Image terrain){        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(terrain, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
}
