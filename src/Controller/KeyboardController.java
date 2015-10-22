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
 * This is the main keyboard input controller, this tracks all keyboard settings
 * and builds all events handlers for it
 * @author o_0
 */
public class KeyboardController {

    private KeyCode moveLeft = KeyCode.A;
    private KeyCode moveRight = KeyCode.D;
    private KeyCode aimUp = KeyCode.W;
    private KeyCode aimDown = KeyCode.S;
    private KeyCode jetpackOn = KeyCode.SPACE;
    private KeyCode fireWeapon = KeyCode.SHIFT;

    /**
     * Create a new keyboardController, with these keys
     * @param moveLeft move unit left
     * @param moveRight move unit rigth
     * @param aimUp aim up
     * @param aimDown aim down
     * @param fire fire the weapon
     * @param jetpackOn turns on jetpack
     */
    public KeyboardController(KeyCode moveLeft, KeyCode moveRight, KeyCode aimUp, KeyCode aimDown,KeyCode fire, KeyCode jetpackOn) {
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.jetpackOn = jetpackOn;
        this.aimUp = aimUp;
        this.aimDown = aimDown;
        this.fireWeapon = fire;
    }

    /**
     * Setter for key action
     * @param moveLeft 
     */
    public void setKeyMoveLeft(KeyCode moveLeft) {
        this.moveLeft = moveLeft;
    }

    /**
     * Setter for key action
     * @param moveRight 
     */
    public void setKeyMoveRight(KeyCode moveRight) {
        this.moveRight = moveRight;
    }

    /**
     * Setter for key action
     * @param jetpackOn 
     */
    public void setKeyJetpackOn(KeyCode jetpackOn) {
        this.jetpackOn = jetpackOn;
    }

    /**
     * Setter for key action
     * @param aimUp 
     */
    public void setKeyAimUp(KeyCode aimUp) {
        this.aimUp = aimUp;
    }
    
    /**
     * Setter for key action
     * @param aimDown 
     */
    public void setKeyAimDown(KeyCode aimDown) {
        this.aimDown = aimDown;
    }
    
    /**
     * Setter for key action
     * @param fire 
     */
    public void setKeyFireWeapon(KeyCode fire) {
        this.fireWeapon = fire;
    }
    
    /**
     * This creates a new keyboard event handler, for a specifik player used for
     * key pressed down
     * @param player the player this input should be tied to
     * @return EventHandler<KeyEvent> to be used to control the player
     */
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
    /**
     * * This creates a new keyboard event handler, for a specifik player used for
     * key released 
     * @param player the player this input should be tied to
     * @return EventHandler<KeyEvent> to be used to control the player
     */
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

