/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab5game;

import GameTimers.RenderTimer;
import Controller.GameUpdateController;
import Controller.KeyboardController;
import GameData.GraphicModels;
import GameObjects.GameObject;
import GameObjects.Player;
import UIGraphics.Lobby;
import UIGraphics.LoginScreen;
import View.GameView;
import java.util.ArrayList;
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
public class GameSetup {
    private LoginScreen loginScreen = null;
    private BattleArena battleArena = null;
    private Lobby lobby = null;

    private Stage mainStage;
    
    public GameSetup(Stage stage) {
        this.mainStage = stage;
    }
    
    public void startMainMenu() {
        if(loginScreen == null) {
            loginScreen = new LoginScreen(mainStage, this);
        }
        loginScreen.setup();
    }
    
    public void lobbyStart(GameSetup gameSetup_){
        if(lobby == null){
            lobby = new Lobby(mainStage, gameSetup_);
        }
        lobby.lobbysetup();
    }
    
    public void startMap() {

        battleArena = new BattleArena(mainStage);
        /**KeyboardController input;
        input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        ArrayList<KeyboardController> keyInputs = new ArrayList<KeyboardController>();
        keyInputs.add(input);
        int numOfAi = 0;*/
        //Ovanstående ska in i en Lobby
        battleArena.setup(lobby.getPlayerInfo(), lobby.getnumOfAi());
        //ovanstående ska ha getters från lobby istället för keyinputs och ai
        //Stoppa allt i en try catch så exception kastas om ai eller keyinpus inte finns
    }
    
    public void resetGame() {
        if(battleArena != null) {
            battleArena.killGame();
        }
        
        startMainMenu();
    }
}
