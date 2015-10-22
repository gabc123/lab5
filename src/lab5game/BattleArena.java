/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import Collisions.Collisions;
import Controller.ExplosionObserver;
import Controller.GameController;
import Controller.GameStatsObservable;
import GameData.GameModel;
import GameTimers.RenderTimer;
import GameTimers.GameTimer;
import Controller.KeyboardController;
import Controller.RenderController;
import GameData.Ai;
import GameData.GraphicModels;
import GameData.Terrain;
import GameObjects.GameObject;
import GameObjects.Physics;
import GameObjects.Player;
import GameObjects.ProjectileType;
import GameObjects.SpawnBox;
import UIGraphics.TopMenu;
import UIGraphics.UIStatObserver;
import View.GameView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author o_0
 */
public class BattleArena {

    private ArrayList<GameObject> gameObjects;
    private Terrain terrain;
    //private Group root;
    private StackPane root;

    private RenderTimer renderTimer;
    private GameTimer gameUpdate;

    private ArrayList<EventHandler<KeyEvent>> inputs;
    private Stage gameStage;
    //private UIStatObserver uIStatObserver;
    private GameStatsObservable gameStatsObservable;

    public BattleArena(Stage stage) {
        this.gameStage = stage;
    }

    
    
    private ArrayList<Player> createPlayers(ArrayList<Player.Playerinfo> playerInfo) {
        inputs = new ArrayList<EventHandler<KeyEvent>>();
        ArrayList<Player> newPlayers = new ArrayList<Player>();
        EventHandler<KeyEvent> pressed;
        EventHandler<KeyEvent> released;
        KeyboardController keyboard;
        for (Player.Playerinfo info : playerInfo) {
            Player player = new Player(info.getPlayerName(), 500, 20, 0);
            newPlayers.add(player);
            System.out.println(info.getPlayerName());
            keyboard = info.getKeyboard();
            pressed = keyboard.getPlayerKeyPressedHandler(player);
            released = keyboard.getPlayerKeyReleasedHandler(player);
            gameStage.addEventHandler(KeyEvent.KEY_PRESSED, pressed);
            gameStage.addEventHandler(KeyEvent.KEY_RELEASED, released);
            inputs.add(pressed);
            inputs.add(released);
            
        }
        if(!newPlayers.isEmpty()) {
            gameObjects.addAll(newPlayers);
        }
        return newPlayers;
    }

    private void removeKeyEvents() {
        for (EventHandler<KeyEvent> input : inputs) {
            gameStage.removeEventHandler(KeyEvent.ANY, input);
        }
        inputs.clear();
    }

    public ExplosionObserver observerSetup() {
        ExplosionObserver explosionObserver = new ExplosionObserver();
        explosionObserver.addObserver(this.terrain);
        for (GameObject obj : gameObjects) {
            if (obj.physicsEnable()) {
                explosionObserver.addObserver((Physics) obj);
            }
        }
        return explosionObserver;
    }
    
     public ArrayList<Player> playerFinde() {
        ArrayList<Player> players = new ArrayList<Player>();
        for(GameObject obj : this.gameObjects) {
            if(obj instanceof Player){
                players.add((Player) obj);
            }
        }
        return players;
    }
    
    
    public void setup(ArrayList<Player.Playerinfo> playerInfo, int numOfAi, GameSetup gameSetup) {
        Stage stage = this.gameStage;
        
        //root = new Group();
        
        root = new StackPane();
        
        

        
        //root.getChildren().
        double width = 1024;
        double height = 720;
        
        
        Canvas canvas = new Canvas(width, height);
        
        
        gameObjects = new ArrayList<GameObject>();
        ArrayList<Player> aiEnemys = createPlayers(playerInfo);
        ArrayList<Ai> aiPlayers = new ArrayList<Ai>();
        for(int i = 0; i < numOfAi; i++) {
            Player aiUnit = new Player("Ai monster",200 , 40, 0);
            gameObjects.add(aiUnit);
            Ai ai = new Ai(aiUnit,aiEnemys,gameObjects);
            aiUnit.setAiControl(ai);
            aiPlayers.add(ai);
        }
        

        terrain = new Terrain(width, height);
        
        
        ExplosionObserver explosionObserver = observerSetup();
        Collisions collisions = new Collisions(terrain,gameObjects);
        GameModel gameModel = new GameModel(width, height, gameObjects, aiPlayers, collisions, terrain);
        GameController gameController = new GameController(gameModel, explosionObserver);
        gameUpdate = new GameTimer(gameController);
        
        // render timer, controller, and GraphicModels
        String[] imgNames = {"buss.png", "Resource/Misil.png",
            "Resource/explosion.png",
            "Resource/Granade.png",
            "Resource/Bullet.png"};
        GraphicModels graphicModels = new GraphicModels();
        graphicModels.loadmodel(imgNames);

        GameView gameView = new GameView(canvas);
        RenderController render = new RenderController(gameView, graphicModels);
        renderTimer = new RenderTimer(render, gameObjects, terrain);
        
       
        
        //MenuBar menubar = new MenuBar();
        TopMenu menu = new TopMenu(this, gameSetup);
        MenuBar menubar = menu.getMenu();
        menubar.prefWidthProperty().bind(gameStage.widthProperty());
        
        VBox hbox = new VBox();
        HBox menuBox = new HBox();
        menuBox.getChildren().add(menubar);
        
        HBox gameBox = new HBox();
        gameBox.getChildren().add(canvas);        
        gameBox.prefHeight(720);
        StackPane.setMargin(hbox, Insets.EMPTY);
        
        ArrayList<Player> allPlayers = playerFinde();
        UIStatObserver uIStatObserver = new UIStatObserver(allPlayers);
        HBox uigamestat = uIStatObserver.createUI(root);
        //uigamestat.getChildren().add(gameBox);
        //gameBox.getChildren().add();
        hbox.getChildren().addAll(menuBox,uigamestat,gameBox);
        root.getChildren().addAll(hbox);
        
        gameStatsObservable = new GameStatsObservable();
        gameStatsObservable.addObserver(uIStatObserver);
        
        for(Player player : allPlayers) {
            player.setGameStatsObservable(gameStatsObservable);
        }
        
        Scene scene = new Scene(root, width, height + 50, Color.GREEN);
        
        this.play();
        stage.setTitle("Lab5Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public void killGame() {
        renderTimer.stop();
        gameUpdate.stop();
        removeKeyEvents();

    }

    public void paus() {
        gameUpdate.stop();
        renderTimer.stop();
    }

    public void play() {
        gameUpdate.start();
        renderTimer.start();
    }
    
    public void info(){
        String titel = ("Keybindings");
        String header = ("Standard keybindings");
        String msg =("Keybindings can be changed in lobby");
        /**String msg =("Unless you have changed the keybindings"
                + "the standardsare:"
                + "Player1: Jump Q, Shoot SHIFT,"
                + "Aim up/down W/S, Move left/right A/D"
                + ""
                + "Player2: Jump SPACE, Shoot PERIOD,"
                + "Aim up/down UPARROW/DOWNARROW, "
                + "Move left/right LEFTARROW/RIGHTARROW");*/
        this.infoMessage(titel, header, msg);
    }
    
    public void infoMessage(String title,String header,String msg){
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private final Alert alert = new Alert(AlertType.INFORMATION);
    
    public void saveGame() {
        paus();
        System.out.println("savegame selected");
        System.out.println("loadgame selected");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save game map");
        File file = fileChooser.showSaveDialog(gameStage);
        try {
            this.terrain.saveTerrain(file);
        } catch (IOException ex) {
            //System.out.println("save error");
            showErrorMsg("Failed to save","Path error","Make sure you save it with a valid name");
        }
    }
    
    public void loadGame() {
        paus();
        System.out.println("loadgame selected");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load game map");
        File file = fileChooser.showOpenDialog(gameStage);
        try {
            this.terrain.loadTerrain(file);
        } catch (IOException ex) {
            //System.out.println("loadgame error");
            showErrorMsg("Failed to load","Could not load","File need to be of typ png");
            
        }catch(IllegalArgumentException ex) {
            showErrorMsg("Failed to load","Path error","File can only be loaded from game directory");
        }
        renderTimer.start();
    }
    
    public void showErrorMsg(String title,String header,String msg) {
        
        errorMsg.setTitle(title);
        errorMsg.setHeaderText(header);
        errorMsg.setContentText(msg);
        errorMsg.show();
        
    }
    
    private final Alert errorMsg = new Alert(Alert.AlertType.ERROR);
}
