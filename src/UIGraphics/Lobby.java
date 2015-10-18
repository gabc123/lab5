/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIGraphics;

import Controller.KeyboardController;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;

/**
 *
 * @author mats
 */
public class Lobby {
    private int numOfAi;
    private KeyboardController input;
    private ArrayList<KeyboardController> keyInputs;
    
    public Lobby(){
        input = new KeyboardController(KeyCode.A, KeyCode.D, KeyCode.SPACE);
        keyInputs = new ArrayList<KeyboardController>();
        keyInputs.add(input);
        numOfAi = 0;
    }
    
    public int getnumOfAi(){
    
    return numOfAi;
    }
    
    public ArrayList getKeyInputs(){
        
        return keyInputs;
    }
}


