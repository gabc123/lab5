/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import GameTimers.RenderTimer;
import Controller.GameUpdateController;
import Controller.KeyboardController;
import Controller.RenderController;
import GameData.GraphicModels;
import GameData.Terrain;
import GameObjects.GameObject;
import GameObjects.Player;
import GameObjects.ProjectileType;
import GameObjects.SpawnBox;
import UIGraphics.TopMenu;
import View.GameView;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuBar;
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
    private Terrain terrain;
    private Group root;

    private RenderTimer renderTimer;
    private GameUpdateController gameUpdate;

    private ArrayList<EventHandler<KeyEvent>> inputs;
    private Stage gameStage;

    public BattleArena(Stage stage) {
        this.gameStage = stage;
    }

    private void createPlayers(ArrayList<Player.Playerinfo> playerInfo) {
        inputs = new ArrayList<EventHandler<KeyEvent>>();
        EventHandler<KeyEvent> pressed;
        EventHandler<KeyEvent> released;
        KeyboardController keyboard;
        for (Player.Playerinfo info : playerInfo) {
            Player player = new Player(info.getPlayerName(), 500, 20, 0);
            System.out.println(info.getPlayerName());
            keyboard = info.getKeyboard();
            pressed = keyboard.getPlayerKeyPressedHandler(player);
            released = keyboard.getPlayerKeyReleasedHandler(player);
            gameStage.addEventHandler(KeyEvent.KEY_PRESSED, pressed);
            gameStage.addEventHandler(KeyEvent.KEY_RELEASED, released);
            inputs.add(pressed);
            inputs.add(released);
            gameObjects.add(player);
        }
    }

    private void removeKeyEvents() {
        for (EventHandler<KeyEvent> input : inputs) {
            gameStage.removeEventHandler(KeyEvent.ANY, input);
        }
        inputs.clear();
    }

    public void setup(ArrayList<Player.Playerinfo> playerInfo, int numOfAi) {
        Stage stage = this.gameStage;
        MenuBar menubar = new MenuBar();
        TopMenu menu = new TopMenu(this);
        root = new Group();
        Scene scene = new Scene(root, 1024, 720, Color.GREEN);

        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        //root.getChildren().
        menubar = menu.getMenu();
        menubar.prefWidthProperty().bind(gameStage.widthProperty());
        root.getChildren().add(menubar);

        gameObjects = new ArrayList<GameObject>();
        createPlayers(playerInfo);

        
        String[] imgNames = {"buss.png", "unit.png", "Resource/explosion.png"};
        GraphicModels gameModels = new GraphicModels();
        gameModels.loadmodel(imgNames);

        GameView gameView = new GameView(canvas);
        RenderController render = new RenderController(gameView, gameModels);

        terrain = new Terrain(scene.getWidth(), scene.getHeight());
        renderTimer = new RenderTimer(render, gameObjects, terrain);
        
        gameObjects.add(new SpawnBox(ProjectileType.BULLET, 200, 200, 0));
        
        gameUpdate = new GameUpdateController(scene.getWidth(), scene.getHeight(), gameObjects, terrain);
        
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
