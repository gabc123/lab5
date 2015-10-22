/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import java.util.Observable;
import java.util.Observer;
import GameObjects.Player;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author mats
 */
public class UIStatObserver implements Observer{
        
        private StackPane root;
        private ArrayList<Player> players;
    
        public UIStatObserver(StackPane root_){
            this.root = root_;
        }
        
        public void createUI(){
        
            Label player1name = new Label(players.get(0).getName());
            Label player2name = new Label(players.get(1).getName());
            player1name.setTextFill(Color.RED);
            player2name.setTextFill(Color.RED);
            Label player1health = new Label(players.get(0).getName());
            Label player2health = new Label(players.get(1).getName());
            player1health.setTextFill(Color.RED);
            player2health.setTextFill(Color.RED);
            VBox playerinfocon1 = new VBox(5);
            playerinfocon1.getChildren().addAll(player1name, player1health);
            VBox playerinfocon2 = new VBox(5);
            playerinfocon2.getChildren().addAll(player2name, player2health);
            HBox namecon = new HBox(880);
            namecon.setAlignment(Pos.BOTTOM_CENTER);
            namecon.getChildren().addAll(playerinfocon1, playerinfocon2);
            root.getChildren().addAll(namecon);
    
        }
    
    public void setPlayers(ArrayList<Player> players_){
        this.players = players_;
    
    }
    @Override
    public void update(Observable o, Object arg) {
        
    
    }
    
}
