
package lab5game;

import GameTimers.RenderTimer;
import Controller.GameUpdateController;
import Controller.KeyboardController;
import GameObjects.GameObject;
import GameObjects.Player;
import GameData.GraphicModels;
import UIGraphics.LoginScreen;
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
    private GameSetup game;
    @Override
    public void start(Stage stage) throws Exception {
        game = new GameSetup(stage);
        game.startMainMenu();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
