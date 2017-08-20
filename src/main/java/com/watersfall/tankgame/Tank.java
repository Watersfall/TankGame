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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Christopher
 */
public class Tank extends Rectangle {
    
    private double angle;
    private Turret turret;
    private Image image;
    private Point p;
    
    public Tank(int x, int y, int height, int width, BufferedImage image) throws IOException
    {
        super(x, y, height, width);
        p = new Point(x, y);
        angle = 0;
        turret = new Turret(x, y, height / 2, width / 2, ImageIO.read(new File("C:\\Users\\Christopher\\Desktop\\TANK1TURRET.png")));
        this.image = image;
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public Point getPoint()
    {
        return p;
    }
    
    public void moveForward()
    {
        x = x + (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + 25, y + 25));
    }
    
    public void moveBack()
    {
        x = x - (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y - (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + 25, y + 25));
    }
    
    public void turnRight()
    {
        angle = angle + 4;
    }
    
    public void turnLeft()
    {
        angle = angle - 4;
    }
    
    public Turret getTurret()
    {
        return turret;
    }
    
    public Image getImage()
    {
        return image;
    }
    
}
