
package lab5game;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *Simply a small class containing main and the startup method
 * @author o_0
 */
public class Lab5game extends Application{
    private GameSetup game;
    @Override
    /**
     * Method creating the GameSetup and uses the startMainMenu method
     */
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
