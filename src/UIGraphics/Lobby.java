/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import Controller.KeyboardController;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lab5game.GameSetup;

/**
 *
 * @author mats
 */
public class Lobby {
    private int numOfAi;
    private KeyboardController input;
    private ArrayList<KeyboardController> keyInputs;
    private Stage Lobbystage;
    private Group root;
    private CheckBox aiCheck;
    private GameSetup gameSetup;
    private Button start;
    
    public Lobby(Stage stage, GameSetup gameSetup_){
        input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        keyInputs = new ArrayList<KeyboardController>();
        keyInputs.add(input);
        numOfAi = 0;
        this.Lobbystage = stage;
        this.gameSetup = gameSetup_;
    }
    
    public void lobbysetup(){
        Stage stage = this.Lobbystage;
        
        aiCheck = new CheckBox();
        start = new Button("Start");
        
        start.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                startMap();
            }
        });
        
        root = new Group();
	Scene scene = new Scene(root, 1024, 720, Color.GREEN);
	Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        
        start.setLayoutX(canvas.getWidth()/2);
        start.setLayoutY(canvas.getHeight()/6 * 5);
        
        aiCheck.setLayoutX(canvas.getWidth()/2);
        aiCheck.setLayoutY(canvas.getHeight()/2);
        
        aiCheck.setText("Ai");
        
        root.getChildren().add(canvas);
        root.getChildren().add(start);
        root.getChildren().add(aiCheck);
        
        stage.setTitle("Lobby");
	stage.setScene(scene);
	stage.setResizable(false);
	stage.sizeToScene();
	stage.show();
    }
    
    public void startMap(){
        gameSetup.startMap();
    }
    
    public int getnumOfAi(){
    
    return numOfAi;
    }
    
    public ArrayList getKeyInputs(){
        
        return keyInputs;
    }
}


