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

/**
 *
 * @author mats
 */
public class TopMenu {
    
    private BattleArena battlearena;
    private MenuBar menubar;
    
    public TopMenu(BattleArena ba){
        this.battlearena = ba;
    }
    
    
    public MenuBar getMenu(){
        
        menubar = new MenuBar();
        
        Menu menuFile = new Menu("State");
	Menu menuSave = new Menu("Save");
	Menu scoreView = new Menu("ViewScoreboard");
	
	MenuItem stopgame = new MenuItem("Stop");
	stopgame.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent t) {
		    stop();
		}
	    }); 
	
	MenuItem startgame = new MenuItem("start");
        startgame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent y) {
		start();
            }
	});
        
	MenuItem exit = new MenuItem("exit");
	
	menuFile.getItems().addAll(stopgame, startgame,  exit);
        menubar.getMenus().addAll(menuFile, menuSave, scoreView);

        
        return menubar;
    }
    
    public void save(){}
    
    public void stop(){
        battlearena.paus();
    }
    
    public void start(){
        battlearena.play();
    }
    
    public void quit(){
        battlearena.killGame();
    }
    
    public void showscoreboard(){}
    
}
