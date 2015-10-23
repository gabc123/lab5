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
import UIGraphics.TopMenu;
import UIGraphics.UIStatObserver;
import View.GameView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *Class responsible for the construction of the game.
 * contains everything from players and terrain to alerts.
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

    /**
     * Constructor takes a stage as input 
     * @param stage stage taken from start of program GameSetup
     */
    public BattleArena(Stage stage) {
        this.gameStage = stage;
    }

    
    /**
     * Creates players, adds all infromation regarding name and keybord
     * to players
     * @param playerInfo ArrayList of type Player internal class playerinfo 
     * @return returns ArrayList of Player type
     */
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

    /**
     * removes eventhandler from gameStage
     */
    private void removeKeyEvents() {
        for (EventHandler<KeyEvent> input : inputs) {
            gameStage.removeEventHandler(KeyEvent.ANY, input);
        }
        inputs.clear();
    }

    /**
     * Creates new explosion observer for objects that can detonate
     * @return a explosionObserver 
     */
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
    
    /**
     * findes players in a gameobjects array
     * @return ArrayList of type player containing players 
     */
     public ArrayList<Player> playerFinde() {
        ArrayList<Player> players = new ArrayList<Player>();
        for(GameObject obj : this.gameObjects) {
            if(obj instanceof Player){
                players.add((Player) obj);
            }
        }
        return players;
    }
    
    /**
     * Sets up gamunits, controllers and modles for the game
     * @param playerInfo ArrayList of Player subclass Playerinfo type 
     * @param numOfAi a int number of ai
     * @param gameSetup a GameSetup
     */
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

    /**
     * kills game
     */
    public void killGame() {
        renderTimer.stop();
        gameUpdate.stop();
        removeKeyEvents();

    }

    /**
     * freses game uppdates and renders
     */
    public void paus() {
        gameUpdate.stop();
        renderTimer.stop();
    }

    /**
     * starts game again after paus
     */
    public void play() {
        gameUpdate.start();
        renderTimer.start();
    }
    /**
     * contains info for alert
     */
    public void info(){
        String titel = ("Keybindings");
        String header = ("Standard keybindings");
        //String msg =("Shoot at enemyKeybindings can be changed in lobby");
        String msg =("Shooot and kill your enmey, and take ammoboxes\n"
                +"Unless you have changed the keybinding in lobby\n"
                + "the standardsare:\n"
                + "Player1: Jump Q, Shoot SHIFT,\n"
                + "Aim up/down W/S, Move left/right A/D\n"
                + "\n"
                + "Player2: Jump SPACE, Shoot PERIOD\n,"
                + "Aim up/down UPARROW/DOWNARROW,\n "
                + "Move left/right LEFTARROW/RIGHTARROW\n");
        this.infoMessage(titel, header, msg);
    }
    
    /**
     * Shows the information alert msg
     * @param title the title for the alert
     * @param header the header
     * @param msg  and the main msg
     */
    public void infoMessage(String title,String header,String msg){
        informationPopup.setTitle(title);
        informationPopup.setHeaderText(header);
        informationPopup.setContentText(msg);
        informationPopup.showAndWait();
    }
    
    /**
     * The alert for game information
     */
    private final Alert informationPopup = new Alert(AlertType.INFORMATION);
    
    /**
     * This will open a fileChooser and save the current terrain
     */
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
    
    /**
     * 
     */
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
