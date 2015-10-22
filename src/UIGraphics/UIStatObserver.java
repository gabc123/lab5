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
import java.text.DecimalFormat;

/**
 *Observer that looks on the basic stats of a player like health and ammo.
 * If changes happen to the stats this class will update the numbers 
 * shown on the screen in the game.
 * @author mats
 */
public class UIStatObserver implements Observer {

    //private StackPane root;
    private Label player1name;
    private Label player2name;
    private Label player1health;
    private Label player2health;
    private Label player1ammo;
    private Label player2ammo;
    private Label player1death;
    private Label player2death;
    private ArrayList<Player> players;

    /**
     * constructor for the observer
     * @param players takes a arraylist of Player type containing players
     */
    public UIStatObserver(ArrayList<Player> players) {
        super();
        this.players = players;
        //createUI(root);

    }

    /**
     * Method that creates the graphic view for the stats in a HBox that is 
     * returned
     * @param root takes a StackPane  a parameter since this is were the 
     * information will be added
     * @return returns HBox containing the information
     */
    public HBox createUI(StackPane root) {
        player1name = new Label(players.get(0).getName());
        player2name = new Label(players.get(1).getName());
        player1name.setTextFill(Color.RED);
        player2name.setTextFill(Color.RED);
        player1health = new Label("Health:" + players.get(0).currentHealth());
        player2health = new Label("Health:" + players.get(1).currentHealth());
        player1health.setTextFill(Color.RED);
        player2health.setTextFill(Color.RED);
        player1ammo = new Label("Ammo:" + players.get(0).currentAmmo());
        player2ammo = new Label("Ammo:" + players.get(1).currentAmmo());
        player1ammo.setTextFill(Color.RED);
        player2ammo.setTextFill(Color.RED);
        player1death = new Label("Deaths:" + players.get(0).getDeaths());
        player2death = new Label("Deaths:" + players.get(1).getDeaths());
        player1death.setTextFill(Color.RED);
        player2death.setTextFill(Color.RED);
        HBox playerinfocon1 = new HBox(5);
        playerinfocon1.getChildren().addAll(player1name, player1health, player1ammo, player1death);
        HBox playerinfocon2 = new HBox(5);
        playerinfocon2.getChildren().addAll(player2name, player2health, player2ammo, player2death);
        HBox namecon = new HBox(500);
        namecon.setAlignment(Pos.BOTTOM_CENTER);
        namecon.getChildren().addAll(playerinfocon1, playerinfocon2);
        //root.getChildren().addAll(playerinfocon1, playerinfocon2);
        return namecon;
    }

    /**
     * sets the players of whom the stats are shown
     * @param players_ ArrayList of Player type 
     */
    public void setPlayers(ArrayList<Player> players_) {
        this.players = players_;

    }

    @Override
    /**
     * Method that updates the labels containing player information
     */
    public void update(Observable o, Object arg) {
        DecimalFormat form = new DecimalFormat("##0.0");
        String p1h = form.format(players.get(0).currentHealth());
        String p2h = form.format(players.get(1).currentHealth());
        
        this.player1health.setText("Health:" + p1h);
        this.player2health.setText("Health:" + p2h);
        //this.player1health.setText("Health:" + players.get(0).currentHealth());
        //this.player2health.setText("Health:" + players.get(1).currentHealth());
        this.player1ammo.setText("Ammo:" + players.get(0).currentAmmo());
        this.player2ammo.setText("Ammo:" + players.get(1).currentAmmo());
        this.player1death.setText("Deaths:" + players.get(0).getDeaths());
        this.player2death.setText("Deaths:" + players.get(1).getDeaths());
    }

}
