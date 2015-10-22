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
public class UIStatObserver implements Observer {

    //private StackPane root;
    private Label player1name;
    private Label player2name;
    private Label player1health;
    private Label player2health;
    private Label player1ammo;
    private Label player2ammo;
    private ArrayList<Player> players;

    public UIStatObserver(StackPane root, ArrayList<Player> players) {
        super();
        this.players = players;
        createUI(root);

    }

    private void createUI(StackPane root) {
        player1name = new Label(players.get(0).getName());
        player2name = new Label(players.get(1).getName());
        player1name.setTextFill(Color.RED);
        player2name.setTextFill(Color.RED);
        player1health = new Label("Health:" + players.get(0).currentHealth());
        player2health = new Label("Health:" + players.get(1).currentHealth());
        player1health.setTextFill(Color.RED);
        player2health.setTextFill(Color.RED);
        player1ammo = new Label("Health:" + players.get(0).currentAmmo());
        player2ammo = new Label("Health:" + players.get(1).currentAmmo());
        player1ammo.setTextFill(Color.RED);
        player2ammo.setTextFill(Color.RED);
        VBox playerinfocon1 = new VBox(5);
        playerinfocon1.getChildren().addAll(player1name, player1health, player1ammo);
        VBox playerinfocon2 = new VBox(5);
        playerinfocon2.getChildren().addAll(player2name, player2health, player2ammo);
        HBox namecon = new HBox(880);
        namecon.setAlignment(Pos.BOTTOM_CENTER);
        namecon.getChildren().addAll(playerinfocon1, playerinfocon2);

        root.getChildren().addAll(namecon);
    }

    public void setPlayers(ArrayList<Player> players_) {
        this.players = players_;

    }

    @Override
    public void update(Observable o, Object arg) {
        
        this.player1health.setText("Health:" + players.get(0).currentHealth());
        this.player2health.setText("Health:" + players.get(1).currentHealth());
    }

}
