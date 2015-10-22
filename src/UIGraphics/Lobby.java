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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Button jumpbuttonp1;
    private Button leftbuttonp1;
    private Button rightbuttonp1;
    private Button jumpbuttonp2;
    private Button leftbuttonp2;
    private Button rightbuttonp2;
    //private Button[] player1buttons;
    //private int i;
    
    private String player1name;
    private String player2name;
    private KeyboardController player1input;
    private KeyboardController player2input;
    private boolean enterNewKey;
    private int player;
    private int whatkey;
    
    private KeyCode jumpbutp1;
    private KeyCode leftbutp1;
    private KeyCode rightbutp1;
    
    private KeyCode jumpbutp2;
    private KeyCode leftbutp2;
    private KeyCode rightbutp2;
    
    
    public Lobby(Stage stage, GameSetup gameSetup_){
        KeyboardController input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        playerInfo = new ArrayList<Playerinfo>();
        playerInfo.add(new Playerinfo("bob",input));
        input = new KeyboardController(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        playerInfo.add(new Playerinfo("tod",input));
        numOfAi = 0;
        this.Lobbystage = stage;
        this.gameSetup = gameSetup_;
    }
    
    

    
    public void lobbysetup(){
        Stage stage = this.Lobbystage;
        enterNewKey = false;
        player = 0;
        whatkey = 0;
        //player1buttons = new Button[3];
        jumpbutp1 = KeyCode.SPACE;;
        leftbutp1 = KeyCode.A;
        rightbutp1 = KeyCode.D;
        jumpbutp2 = KeyCode.SPACE;
        leftbutp2 = KeyCode.LEFT;
        rightbutp2 = KeyCode.RIGHT;
        
        
        EventHandler handler;
        handler = new EventHandler<KeyEvent>() {
        @Override
            public void handle(KeyEvent event) {
                if(enterNewKey) {
                    setKey(event.getCode());
                    enterNewKey = false;
                    event.consume();
                }
                else{event.consume();}
            }
        };
        
        /**for(i = 0; i < player1buttons.length; i++){
            player1buttons[i] = new Button();
                player1buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 1;
                    enterNewKey = true;
                    whatkey = i;
            }   
        });
        }*/
        
        
        Label jumpp1 = new Label("Jump");
        jumpbuttonp1 = new Button();
            jumpbuttonp1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 1;
                    enterNewKey = true;
                    whatkey = 1;
            }   
        });
        HBox jumpcon = new HBox(10);
        
        Label leftp1 = new Label("Left");
        leftbuttonp1 = new Button();
            leftbuttonp1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 1;
                    enterNewKey = true;
                    whatkey = 2;
            }   
        });
        HBox leftcon = new HBox(10);
        
        Label rightp1 = new Label("Right");
        rightbuttonp1 = new Button();
            rightbuttonp1.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 1;
                    enterNewKey = true;
                    whatkey = 3;
            }   
        });
        HBox rightcon = new HBox(10);
        
        Label jumpp2 = new Label("Jump");
        jumpbuttonp2 = new Button();
            jumpbuttonp2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 2;
                    enterNewKey = true;
                    whatkey = 1;
            }   
        });
        HBox jumpcon2 = new HBox(10);
        
        Label leftp2 = new Label("Left");
        leftbuttonp2 = new Button();
            leftbuttonp2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 2;
                    enterNewKey = true;
                    whatkey = 2;
            }   
        });
        HBox leftcon2 = new HBox(10);
        
        Label rightp2 = new Label("Right");
        rightbuttonp2 = new Button();
            rightbuttonp2.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    player = 2;
                    enterNewKey = true;
                    whatkey = 3;
            }   
        });
        HBox rightcon2 = new HBox(10);
            
            

        Label player1 = new Label("Player 1 Name:");
        TextField player1Name = new TextField ();
        HBox namecon = new HBox(10);
        
        Label player2 = new Label("Player 2 Name:");
        TextField player2Name = new TextField ();
        HBox namecon2 = new HBox(10);
        
        aiCheck = new CheckBox();
        start = new Button("Start");
        
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player1name = player1Name.getText();
                playerInfo.get(0).setPlayerName(player1name);
                
                player1input = new KeyboardController(leftbutp1, rightbutp1, jumpbutp1);
                playerInfo.get(0).setKeyboard(player1input);
                
                player2name = player2Name.getText();
                playerInfo.get(1).setPlayerName(player2name);
                
                player2input = new KeyboardController(leftbutp2, rightbutp2, jumpbutp2);
                playerInfo.get(1).setKeyboard(player2input);
                        
                //System.out.println(player1name);
                //playerInfo.add(new Playerinfo(player1name,input));
                if(aiCheck.isSelected() == true){
                    playerInfo.remove(1);
                    numOfAi++;
                }
                stage.removeEventHandler(KeyEvent.ANY, handler);
                startMap();
            }
        });
        
        root = new Group();
	Scene scene = new Scene(root, 1024, 720, Color.AZURE);
        
	Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        
        start.setLayoutX(canvas.getWidth()/2);
        start.setLayoutY(canvas.getHeight()/6 * 5);
        
        aiCheck.setText("Ai");
        
        namecon.getChildren().addAll(player1, player1Name);
        
        namecon2.getChildren().addAll(player2, player2Name, aiCheck);
        
        jumpcon.getChildren().addAll(jumpp1, jumpbuttonp1);
        
        leftcon.getChildren().addAll(leftp1, leftbuttonp1);
        
        rightcon.getChildren().addAll(rightp1, rightbuttonp1);
        
        /**jumpcon.getChildren().addAll(jumpp1, player1buttons[0]);
        
        leftcon.getChildren().addAll(leftp1, player1buttons[1]);
        
        rightcon.getChildren().addAll(rightp1, player1buttons[2]);*/
                
        jumpcon2.getChildren().addAll(jumpp2, jumpbuttonp2);
        
        leftcon2.getChildren().addAll(leftp2, leftbuttonp2);
        
        rightcon2.getChildren().addAll(rightp2, rightbuttonp2);
        
        Label wichplayer = new Label("Player1:");
        VBox keyconp1 = new VBox(10);
        keyconp1.getChildren().addAll(wichplayer,jumpcon, leftcon, rightcon);
        
        Label wichplayer2 = new Label("Player2:");
        VBox keyconp2 = new VBox(10);
        keyconp2.getChildren().addAll(wichplayer2,jumpcon2, leftcon2, rightcon2);
        
        HBox players12 = new HBox(10);
        players12.getChildren().addAll(keyconp1, keyconp2);
        
        VBox keyconinfo = new VBox(10);
        Label info = new Label("Choose buttons to controll your caracter with:");
        keyconinfo.getChildren().addAll(info, players12);
        
        /**HBox namecon4 = new HBox();
        namecon4.setSpacing(10);
        namecon4.getChildren().addAll(namecon, namecon2, keyconinfo);
        namecon4.setPrefWidth(scene.getWidth());*/
        
        FlowPane namecon4 = new FlowPane();
        namecon4.setVgap(10);
        namecon4.setHgap(10);
        namecon4.getChildren().addAll(namecon, namecon2, keyconinfo);
        namecon4.setPrefWidth(scene.getWidth());
        
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, handler);
                
        /**stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
        @Override
            public void handle(KeyEvent event) {
                if(enterNewKey) {
                    setKey(event.getCode());
                    enterNewKey = false;
                }
            }
        });*/
        

        root.getChildren().add(canvas);
        root.getChildren().add(namecon4);
        root.getChildren().add(start);
         
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
    
    private void setKey(KeyCode key){
        
        if(player == 1){
            switch (whatkey){
            case 1: jumpbutp1 = key; break;
            case 2: leftbutp1 = key; break;
            case 3: rightbutp1 = key; break;
            default: break;
            }
        }
        if(player == 2){
            switch (whatkey){
            case 1: jumpbutp2 = key; break;
            case 2: leftbutp2 = key; break;
            case 3: rightbutp2 = key; break;
                default: break;
            }
        }
    
    }

}
