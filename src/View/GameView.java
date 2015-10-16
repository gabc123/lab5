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
    private Image background;
    public GameView(Canvas canvas,Image background) {
        this.canvas = canvas;
        this.background = background;
    }
    
    public void drawbackground(){
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    public void drawmodel(Image model, double x, double y){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(model, x, y);
    }
    
   
    public void drawterrain(Canvas terrain){}
    
}
