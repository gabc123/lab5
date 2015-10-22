/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Observable;

/**
 *a Observable used to help keep track of health and ammo on
 * the players
 * @author mats
 */
public class GameStatsObservable extends Observable {
    
    /**
     * Method that gets call when changes have been made,
     * after this it notifies the observing classes
     */
    public void checkUIInfo(){
        this.setChanged();
        this.notifyObservers();
    }
    
}
