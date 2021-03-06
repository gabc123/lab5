/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import UIGraphics.Lobby;
import UIGraphics.LoginScreen;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author o_0
 */
public class GameSetup {
    private LoginScreen loginScreen = null;
    private BattleArena battleArena = null;
    private Lobby lobby = null;

    private Stage mainStage;
    /**
     * This sets the main stage
     * @param stage 
     */
    public GameSetup(Stage stage) {
        this.mainStage = stage;
    }
    
    /**
     * starts the mainmenu
     */
    public void startMainMenu() {
        if(loginScreen == null) {
            loginScreen = new LoginScreen(mainStage, this);
        }
        loginScreen.setup();
    }
    /**
     * starts the lobby
     * @param gameSetup_ this
     */
    public void lobbyStart(GameSetup gameSetup_){
        if(lobby == null){
            lobby = new Lobby(mainStage, gameSetup_);
        }
        lobby.lobbysetup();
    }
    
    /**
     * starts the game play
     */
    public void startMap() {

        battleArena = new BattleArena(mainStage);
        /**KeyboardController input;
        input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        ArrayList<KeyboardController> keyInputs = new ArrayList<KeyboardController>();
        keyInputs.add(input);
        int numOfAi = 0;*/
        //Ovanstående ska in i en Lobby
        battleArena.setup(lobby.getPlayerInfo(), lobby.getnumOfAi(), this);
        //ovanstående ska ha getters från lobby istället för keyinputs och ai
        //Stoppa allt i en try catch så exception kastas om ai eller keyinpus inte finns
    }
    
    /**
     * exit program
     */
    public void exitGame() {
        mainStage.close();
    }
    
    /**
     * resets game
     */
    public void resetGame() {
        if(battleArena != null) {
            battleArena.killGame();
        }
        
        startMainMenu();
    }
    
}
