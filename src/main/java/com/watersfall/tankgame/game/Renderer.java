package com.watersfall.tankgame.game;

import com.watersfall.tankgame.Main;
import java.awt.Graphics;
import javax.swing.JPanel;

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