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
    private KeyCode aimUp = KeyCode.W;
    private KeyCode aimDown = KeyCode.S;
    private KeyCode jetpackOn = KeyCode.SPACE;
    private KeyCode fireWeapon = KeyCode.SHIFT;

    public KeyboardController(KeyCode moveLeft, KeyCode moveRight, KeyCode aimUp, KeyCode aimDown,KeyCode fire, KeyCode jetpackOn) {
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.jetpackOn = jetpackOn;
        this.aimUp = aimUp;
        this.aimDown = aimDown;
        this.fireWeapon = fire;
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

    public void setKeyAimUp(KeyCode aimUp) {
        this.aimUp = aimUp;
    }
    
    public void setKeyAimDown(KeyCode aimDown) {
        this.aimDown = aimDown;
    }
    
    public void setKeyFireWeapon(KeyCode fire) {
        this.fireWeapon = fire;
    }
    
    public EventHandler<KeyEvent> getPlayerKeyPressedHandler(Player player) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == fireWeapon) {
                    player.fireWeapon();
                }
                
                if (event.getCode() == moveLeft) {
                    player.setDirection(Direction.LEFT);
                }
                
                if (event.getCode() == moveRight) {
                    player.setDirection(Direction.RIGHT);
                }
                
                if (event.getCode() == aimUp) {
                    player.setAim(Direction.UP);
                }
                
                if (event.getCode() == aimDown) {
                    player.setAim(Direction.DOWN);
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
                if (event.getCode() == aimUp) {
                    player.setAim(Direction.NONE);
                }
                
                if (event.getCode() == aimDown) {
                    player.setAim(Direction.NONE);
                }
                
                if (event.getCode() == jetpackOn) {
                    player.setJetpackState(false);
                }
            }
        };
    }
}

