/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Christopher
 */

//Future Class that will be used to create obstacles in the game
//Trees, walls, etc.
public class Obstacle extends Rectangle {
    
    public int angle;
    public BufferedImage image;
    
    public Obstacle(String data)
    {
        System.out.println(data);
        width = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        height = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        x = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        y = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
    }
}
