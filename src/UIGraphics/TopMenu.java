/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import GameData.Terrain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import lab5game.BattleArena;
import lab5game.GameSetup;

/**
 *Class creating the menu at the top of the scene during the game
 * @author mats
 */
public class TopMenu {
    
    private BattleArena battlearena;
    private MenuBar menubar;
    private GameSetup GS;
    
    /**
     * constructor for TopMenu
     * @param ba takes a BattleArena as input to get accsess to methods
     * @param GS_ takes gameSetup to get accsess to methods
     */
    public TopMenu(BattleArena ba, GameSetup GS_){
        this.battlearena = ba;
        this.GS = GS_;
    }
    
    
    public MenuBar getMenu(){
        
        menubar = new MenuBar();
        
        Menu menuState = new Menu("State");
	
	MenuItem stopgame = new MenuItem("Stop");
	stopgame.setOnAction(stop());
	MenuItem startgame = new MenuItem("start");
        startgame.setOnAction(start());
        
        menuState.getItems().addAll(stopgame, startgame);
        
        Menu menuOptions = new Menu("Options");
        
        MenuItem savegame = new MenuItem("Save");
	savegame.setOnAction(save());
        MenuItem loadgame = new MenuItem("Load");
	loadgame.setOnAction(load());
        MenuItem infogame = new MenuItem("Information");
	infogame.setOnAction(info());
        
	MenuItem scoregame = new MenuItem("Score board");
        //startgame.setOnAction(score());
        
        menuOptions.getItems().addAll(savegame, loadgame, scoregame, infogame);
            
        Menu menuEnd = new Menu("End game");
        
        MenuItem maingame = new MenuItem("Main menu");
        maingame.setOnAction(reset());
        MenuItem exitgame = new MenuItem("Exit Game");
        exitgame.setOnAction(quit());
        
        menuEnd.getItems().addAll(maingame, exitgame);
	
	
        menubar.getMenus().addAll(menuState, menuOptions, menuEnd);

        
        return menubar;
    }
    
    private EventHandler<ActionEvent> start(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent y) {
		battlearena.play();
            }
        };
    }
    
    public EventHandler<ActionEvent> stop(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
		battlearena.paus();
		}
	    };
        
    }
    
    private EventHandler<ActionEvent> save(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent y) {
                battlearena.saveGame();
            }
        };
    }
    
    private EventHandler<ActionEvent> load(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent y) {
                battlearena.loadGame();
            }
        };
    }
    
    public EventHandler<ActionEvent> reset(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent z) {
		GS.resetGame();
		}
	    }; 
    }
    
    public EventHandler<ActionEvent> quit(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent z) {
		GS.exitGame();
		}
	    }; 
    }
    
    public void showscoreboard(){}
    
    public EventHandler<ActionEvent> info(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent z) {
		battlearena.info();
		}
	    }; 
    }
    
    
}
