/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//put in vbox
package UIGraphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import lab5game.BattleArena;

/**
 *
 * @author mats
 */
public class Menu {
    private BattleArena battlearena;
    private MenuBar menubar;
    
    public Menu(BattleArena ba){
    this.battlearena = ba;
    }
    
    public MenuBar getMenu(){
        
        menubar = new MenuBar();
        
        /**Menu menuFile = new Menu("State");
	Menu menuEdit = new Menu("Edit");
	Menu menuView = new Menu("View");
        Menu hejsan = new Menu("hej");*/
	
	MenuItem add = new MenuItem("add");
	add.setOnAction(new EventHandler<ActionEvent>() {
		public void handle(ActionEvent t) {
		    System.out.println("Hejsan");
		}
	    }); 
	
	MenuItem clear = new MenuItem("clear");
	MenuItem exit = new MenuItem("exit");
	
	//menuFile.getItems().addAll(add, clear,  exit);
        //menubar.getMenus().addAll(menuFile, menuEdit, menuView);

        
        return menubar;
    }
    
    public void save(){}
    
    public void stop(){
        battlearena.paus();
    }
    
    public void start(){
        battlearena.play();
    }
    
    public void quit(){}
    
    public void showscoreboard(){}
    
}
