/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import com.watersfall.tankgame.game.Frame;
import com.watersfall.tankgame.ui.SelectionFrame;
import java.io.IOException;

/**
 *
 * @author Christopher
 */
public class Main {
    
    public static Frame frame;
    public static SelectionFrame selectionFrame;
    public static void main(String[] args) throws IOException
    {
        //frame = new Frame();
        selectionFrame = new SelectionFrame();
    }
}
