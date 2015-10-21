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
        
        Menu menuFile = new Menu("State");
	Menu menuSave = new Menu("Save");
	Menu scoreView = new Menu("ViewScoreboard");
	
	MenuItem stopgame = new MenuItem("Stop");
	stopgame.setOnAction(stop());
	
	MenuItem startgame = new MenuItem("start");
        startgame.setOnAction(start());
        
	MenuItem exitgame = new MenuItem("exit");
        exitgame.setOnAction(quit());
	
	menuFile.getItems().addAll(stopgame, startgame,  exitgame);
        menubar.getMenus().addAll(menuFile, menuSave, scoreView);

        
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
    
}
