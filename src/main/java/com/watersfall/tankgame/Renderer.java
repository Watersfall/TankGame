/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Christopher
 */
public class Renderer extends JPanel {
     
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Main.frame.repaint(g);
    }
}
