
package lab5game;

import Controller.GameRender;
import Controller.GameUpdateController;
import Controller.KeyboardController;
import GameObjects.GameObject;
import GameObjects.Player;
import GameData.GraphicModels;
import View.GameView;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author o_0
 */
public class Lab5game extends Application{
    private ArrayList<GameObject> gameObjects;
    private Group root;
    private GameRender render;
    private GameUpdateController gameUpdate;

    @Override
    public void start(Stage stage) throws Exception {
        root = new Group();
        Scene scene = new Scene(root, 1024, 720, Color.GREEN);
        Canvas canvas = new Canvas(scene.getWidth(), scene.getHeight());
        root.getChildren().add(canvas);
        
        stage.setTitle("Lab5Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        
        Player player1 = new Player(500,20,0);
        
        KeyboardController input;
        input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, input.getPlayerKeyPressedHandler(player1));
        stage.addEventHandler(KeyEvent.KEY_RELEASED, input.getPlayerKeyReleasedHandler(player1));        
        gameObjects = new ArrayList<GameObject>();
        gameObjects.add(player1);
        //gameObjects.add(new Player(150,150,0));
        
        GraphicModels gameModels = new GraphicModels();
        String[] imgNames = {"buss.png","unit.png"};
        gameModels.loadmodel(imgNames);
        
        GameView gameView = new GameView(canvas, new Image("mapbackground.png"));
        render = new GameRender(gameView,gameModels,gameObjects);
        
        render.start();
        
        gameUpdate = new GameUpdateController(gameObjects);
        gameUpdate.start();
        //render = new GameRender(canvas);
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
