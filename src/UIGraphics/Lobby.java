/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import Controller.KeyboardController;
import GameObjects.Player.Playerinfo;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lab5game.GameSetup;

/**
 *
 * @author mats
 */
public class Lobby {
    private int numOfAi;
    private ArrayList<Playerinfo> playerInfo;
    private Stage Lobbystage;
    private Group root;
    private CheckBox aiCheck;
    private GameSetup gameSetup;
    private Button start;
    private String player1name;
    private KeyboardController input;
    
    public Lobby(Stage stage, GameSetup gameSetup_){
        this.input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        playerInfo = new ArrayList<Playerinfo>();
        playerInfo.add(new Playerinfo("bob",input));
        this.input = new KeyboardController(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        playerInfo.add(new Playerinfo("tod",input));
        numOfAi = 0;
        this.Lobbystage = stage;
        this.gameSetup = gameSetup_;
    }
    
    public void lobbysetup(){
        Stage stage = this.Lobbystage;
        //KeyboardController input_  this.input;
        
        Label player1 = new Label("Player 1 Name:");
        TextField player1Name = new TextField ();
        HBox namecon = new HBox();
        
        aiCheck = new CheckBox();
        start = new Button("Start");
        
        start.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                player1name = player1Name.getText();
                playerInfo.get(0).setPlayerName(player1name);
                //System.out.println(player1name);
                //playerInfo.add(new Playerinfo(player1name,input));
                startMap();
            }
        });
        
        root = new Group();
	Scene scene = new Scene(root, 1024, 720, Color.AZURE);
	Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        
        start.setLayoutX(canvas.getWidth()/2);
        start.setLayoutY(canvas.getHeight()/6 * 5);
        
        //aiCheck.setLayoutX(canvas.getWidth()/2);
        //aiCheck.setLayoutY(canvas.getHeight()/2);
        
        aiCheck.setText("Ai");
        
        namecon.setSpacing(10);
        namecon.getChildren().addAll(player1, player1Name, aiCheck);
        
        root.getChildren().add(canvas);
        root.getChildren().add(namecon);
        root.getChildren().add(start);
        //root.getChildren().add(aiCheck);
         
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
    
    public ArrayList<Playerinfo> getPlayerInfo(){
        
        return playerInfo;
    }
}


