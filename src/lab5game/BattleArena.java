/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import Collisions.Collisions;
import Controller.ExplosionObserver;
import Controller.GameController;
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
import View.GameView;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    
    public void setup(ArrayList<Player.Playerinfo> playerInfo, int numOfAi, GameSetup gameSetup) {
        Stage stage = this.gameStage;
        
        //root = new Group();
        
        root = new StackPane();

        
        //root.getChildren().
        double width = 1024;
        double height = 720;
        MenuBar menubar = new MenuBar();
        TopMenu menu = new TopMenu(this, gameSetup);
        menubar = menu.getMenu();
        menubar.prefWidthProperty().bind(gameStage.widthProperty());
        
        Canvas canvas = new Canvas(width, height);
        
        VBox hbox = new VBox();
        HBox menuBox = new HBox();
        menuBox.getChildren().add(menubar);
        
        HBox gameBox = new HBox();
        gameBox.getChildren().add(canvas);        
        gameBox.prefHeight(720);
        StackPane.setMargin(hbox, Insets.EMPTY);
        hbox.getChildren().addAll(menuBox,gameBox);
        root.getChildren().addAll(hbox);
        
        
        Scene scene = new Scene(root, width, height + 28, Color.GREEN);
        
        gameObjects = new ArrayList<GameObject>();
        ArrayList<Player> aiEnemys = createPlayers(playerInfo);
        ArrayList<Ai> aiPlayers = new ArrayList<Ai>();
        for(int i = 0; i < numOfAi; i++) {
            Player aiUnit = new Player("Ai monster",200 , 40, 0);
            gameObjects.add(aiUnit);
            aiPlayers.add(new Ai(aiUnit,aiEnemys,gameObjects));
        }
        

        terrain = new Terrain(width, height);
        
        gameObjects.add(new SpawnBox(ProjectileType.BULLET, 200, 200, 0));
        
        ExplosionObserver observer = observerSetup();
        Collisions collisions = new Collisions(terrain,gameObjects);
        GameModel gameModel = new GameModel(width, height, gameObjects, aiPlayers, collisions, terrain);
        GameController gameController = new GameController(gameModel, observer);
        gameUpdate = new GameTimer(gameController);
        
        // render timer, controller, and GraphicModels
        String[] imgNames = {"buss.png", "Resource/Misil.png", "Resource/explosion.png"};
        GraphicModels graphicModels = new GraphicModels();
        graphicModels.loadmodel(imgNames);

        GameView gameView = new GameView(canvas);
        RenderController render = new RenderController(gameView, graphicModels);
        renderTimer = new RenderTimer(render, gameObjects, terrain);
        
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
}
