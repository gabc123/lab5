/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GameObjects.Direction;
import GameObjects.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author o_0
 */
public class KeyboardController {

    private KeyCode moveLeft = KeyCode.A;
    private KeyCode moveRight = KeyCode.D;
    private KeyCode moveUp = KeyCode.W;
    private KeyCode moveDown = KeyCode.S;
    private KeyCode jetpackOn = KeyCode.SPACE;

    public KeyboardController(KeyCode moveLeft, KeyCode moveRight, KeyCode jetpackOn) {
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.jetpackOn = jetpackOn;
    }

    public void setKeyMoveLeft(KeyCode moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setKeyMoveRight(KeyCode moveRight) {
        this.moveRight = moveRight;
    }

    public void setKeyJetpackOn(KeyCode jetpackOn) {
        this.jetpackOn = jetpackOn;
    }

    public EventHandler<KeyEvent> getPlayerKeyPressedHandler(Player player) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == moveLeft) {
                    player.setDirection(Direction.LEFT);
                }
                
                if (event.getCode() == moveRight) {
                    player.setDirection(Direction.RIGHT);
                }
                
                if (event.getCode() == moveUp) {
                    player.setDirection(Direction.UP);
                }
                
                if (event.getCode() == moveDown) {
                    player.setDirection(Direction.DOWN);
                }
                
                if (event.getCode() == jetpackOn) {
                    player.setJetpackState(true);
                }
            }
        };
    }
    
    public EventHandler<KeyEvent> getPlayerKeyReleasedHandler(Player player) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == moveLeft) {
                    player.setDirection(Direction.NONE);
                }
                
                if (event.getCode() == moveRight) {
                    player.setDirection(Direction.NONE);
                }
                if (event.getCode() == moveUp) {
                    player.setDirection(Direction.NONE);
                }
                
                if (event.getCode() == moveDown) {
                    player.setDirection(Direction.NONE);
                }
                
                if (event.getCode() == jetpackOn) {
                    player.setJetpackState(false);
                }
            }
        };
    }
}

