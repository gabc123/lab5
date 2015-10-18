/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

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
import lab5game.GameSetup;

/**
 *
 * @author mats
 */
public class LoginScreen {
    GameSetup gameSetup;
    private Group root;
    private Image image;
    private Image imagebut;
    private Button but;
    private Button but2;
    private Stage loginStage;
    
    public LoginScreen(Stage stage, GameSetup gameSetup) {
        this.gameSetup = gameSetup;
        this.loginStage = stage;
        this.image = new Image("Resource/Vatten.png",false);
        this.imagebut = new Image("Resource/Playknapp.png",false);
        
    }
    
    public void setup(){
        Stage stage = this.loginStage;
	but = new Button();
	but.setGraphic(new ImageView(imagebut));

       	but.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                play();
            }
        });
        
        but2 = new Button();
	but2.setGraphic(new ImageView(imagebut));

       	but2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                loadgame();
            }
        });
	
	root = new Group();
	Scene scene = new Scene(root, 1024, 720, Color.GREEN);
	Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());

	but.setLayoutX(canvas.getWidth()/2 - (imagebut.getWidth()/2));
        but.setLayoutY(canvas.getHeight()/2 - (imagebut.getHeight() + imagebut.getHeight()));
	
        but2.setLayoutX(canvas.getWidth()/2 - (imagebut.getWidth()/2));
        but2.setLayoutY(canvas.getHeight()/2 - (imagebut.getHeight()/2));
	
	GraphicsContext gc = canvas.getGraphicsContext2D();
	gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	gc.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
	
	root.getChildren().add(canvas);
	root.getChildren().add(but);
        root.getChildren().add(but2);
	
	
	stage.setTitle("Start");
	stage.setScene(scene);
	stage.setResizable(false);
	stage.sizeToScene();
	stage.show();
        
    }
    
    public void play(){
        
        
        gameSetup.lobbyStart(this.gameSetup);
    }
    
    public void loadgame(){}
    
    public void showhighscore(){}

    public void keybindings(){}
    
}
