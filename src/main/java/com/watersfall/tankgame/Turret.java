/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Christopher
 */
public class Turret extends Rectangle{
    
    
    double angle;
    private BufferedImage image;
    
    public Turret(int x, int y, BufferedImage image)
    {
        super(x, y, image.getWidth(), image.getHeight());
        angle = 0;
        this.image = image;
    }
    
    public void turnLeft()
    {
        angle = angle - 4;
    }
    
    public void turnRight()
    {
        angle = angle + 4;
    }
    
    public void setLocation(Point p)
    {
        this.x = p.x;
        this.y = p.y;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
}
