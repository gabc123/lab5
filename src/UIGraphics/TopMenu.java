/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import lab5game.BattleArena;
import lab5game.GameSetup;

/**
 *
 * @author mats
 */
public class TopMenu {
    
    private BattleArena battlearena;
    private MenuBar menubar;
    private GameSetup GS;
    
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
	//stopgame.setOnAction(save());
	MenuItem scoregame = new MenuItem("Score board");
        //startgame.setOnAction(score());
        
        menuOptions.getItems().addAll(savegame, scoregame);
            
        Menu menuEnd = new Menu("End game");
        
        MenuItem maingame = new MenuItem("Main menu");
        maingame.setOnAction(quit());
        MenuItem exitgame = new MenuItem("Exit Game");
        //maingame.setOnAction(exit());
        
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
    
    public void save(){
        //battlearena.play();
    }
    
    public EventHandler<ActionEvent> quit(){
        return new EventHandler<ActionEvent>() {
            public void handle(ActionEvent z) {
		GS.resetGame();
		}
	    }; 
    }
    
    public void showscoreboard(){}
    
    public EventHandler<ActionEvent> exit(){
        return null;
    }
    
}
