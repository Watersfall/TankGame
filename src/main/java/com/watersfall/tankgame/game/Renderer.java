/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import com.watersfall.tankgame.Main;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Christopher
 */
public class Renderer extends JPanel {
     
    //Class to render the game
    //Calls the repaint method in the instance of the Frame class in the SelectionFrame created in Main
    //THIS SHOULD NEVER NEED TO BE CHANGED
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Main.selectionFrame.frame.repaint(g);
    }
}
