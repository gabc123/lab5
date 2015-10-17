/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import Controller.GameRender;
import Controller.GameUpdateController;
import Controller.KeyboardController;
import GameData.GraphicModels;
import GameObjects.GameObject;
import GameObjects.Player;
import View.GameView;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author o_0
 */
public class BattleArena {
    private ArrayList<GameObject> gameObjects;
    private Group root;
    private GameRender render;
    private GameUpdateController gameUpdate;
    private ArrayList<EventHandler<KeyEvent>> inputs;
    private Stage gameStage;
    
    public BattleArena(Stage stage) {
        this.gameStage = stage;
    }
    
    private void createPlayersWithKeyEvents(ArrayList<KeyboardController> keyInputs) {
        inputs = new ArrayList<EventHandler<KeyEvent>>();
        EventHandler<KeyEvent> pressed;
        EventHandler<KeyEvent> released;
        
        for(KeyboardController input : keyInputs) {
            Player player = new Player(500,20,0);
            pressed = input.getPlayerKeyPressedHandler(player);
            released = input.getPlayerKeyReleasedHandler(player);
            gameStage.addEventHandler(KeyEvent.KEY_PRESSED,pressed );
            gameStage.addEventHandler(KeyEvent.KEY_RELEASED, released);
            inputs.add(pressed);
            inputs.add(released);
            gameObjects.add(player);
        }
    }
    
    private void removeKeyEvents() {
        for(EventHandler<KeyEvent> input : inputs) {
            gameStage.removeEventHandler(KeyEvent.ANY, input);
        }
        inputs.clear();
    }
    
    public void setup(ArrayList<KeyboardController> keyInputs,int numOfAi) {
        Stage stage = this.gameStage;
        root = new Group();
        Scene scene = new Scene(root, 1024, 720, Color.GREEN);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        
        gameObjects = new ArrayList<GameObject>();
        createPlayersWithKeyEvents(keyInputs);
        
        GraphicModels gameModels = new GraphicModels();
        String[] imgNames = {"buss.png","unit.png"};
        gameModels.loadmodel(imgNames);
        
        GameView gameView = new GameView(canvas, new Image("mapbackground.png"));
        render = new GameRender(gameView,gameModels,gameObjects);
        
        render.start();
        
        gameUpdate = new GameUpdateController(gameObjects);
        gameUpdate.start();
        stage.setTitle("Lab5Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }
    
    public void killGame() {
        render.stop();
        gameUpdate.stop();
        removeKeyEvents();
        
    }
}
