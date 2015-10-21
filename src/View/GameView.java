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
    
    public void drawmodel(Image model, double x, double y){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(model, x - model.getWidth()/2, y - model.getHeight()/2);
    }
    
   
    public void drawterrain(Image terrain){        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(terrain, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
}
