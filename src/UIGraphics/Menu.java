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
import javafx.scene.layout.HBox;
import lab5game.BattleArena;

/**
 *
 * @author mats
 */
public class Menu {
    private BattleArena battlearena;
    
    public Menu(BattleArena ba){
    this.battlearena = ba;
    }
    
    public HBox menuGetHbox(){
        
        Button quit = new Button();
        Button scorboard = new Button();
	quit.setText("Quit");
	scorboard.setText("Scorboard");
        
        HBox menu = new HBox();
        ComboBox dropdown = new ComboBox();
        
        dropdown.getItems().addAll(
            "Stop",
            "Play" 
        );
        
        dropdown.setOnAction(new EventHandler<ActionEvent>(){
           public void handle(ActionEvent event) {
               if(dropdown.getValue() == "Stop"){
                   stop();
               }
               else{
                   start();
               }
           
           }
        });
        
        
        menu.setStyle("-fx-background-color: LIGHTGREY;");
	menu.setPrefWidth(1024);
	menu.setPrefHeight(28);
	menu.setSpacing(5);
        menu.getChildren().addAll(dropdown, scorboard, quit);
        
        return menu;
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
