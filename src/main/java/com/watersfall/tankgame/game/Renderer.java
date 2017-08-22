/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import com.watersfall.tankgame.Main;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Christopher
 */
public class Renderer extends JPanel {
     
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try 
        {
            Main.selectionFrame.frame.repaint(g);
        } 
        catch (InterruptedException | IOException ex) 
        {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
