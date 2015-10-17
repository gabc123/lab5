/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author mats
 */
public class LoginScreen extends Application{
    
    private Group root;
    private Image image = new Image("/Vatten.png", false);
    private Image imagebut = new Image("/Playknapp.png", false);
    Button but;
    
    @Override
    public void start(Stage stage) throws Exception{
        
	but = new Button();
	but.setGraphic(new ImageView(imagebut));

        //but.setOnAction(new EventHandler<ActionEvent>());
	
	root = new Group();
	Scene scene = new Scene(root, 1024, 720, Color.GREEN);
	Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());

	but.setLayoutX(canvas.getWidth()/2 - (imagebut.getWidth()/2));
        but.setLayoutY(canvas.getHeight()/2 - (imagebut.getHeight()/2));
	
	
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
	
	root.getChildren().add(canvas);
	root.getChildren().add(but);
	
	
	stage.setTitle("Start");
	stage.setScene(scene);
	stage.setResizable(false);
	stage.sizeToScene();
	stage.show();
        
    }
    
    public void play(){
    
    }
    
    public void loadgame(){}
    
    public void showhighscore(){}

    public void keybindings(){}
    
}
