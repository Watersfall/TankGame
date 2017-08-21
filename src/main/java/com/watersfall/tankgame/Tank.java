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
    
    public Tank(int x, int y, int height, int width, BufferedImage image) throws IOException
    {
        super(x, y, width, height);
        Rectangle r = new Rectangle();
        angle = 0;
        turret = new Turret(x, y, ImageIO.read(new File("C:\\Users\\Christopher\\Desktop\\TANK1TURRET.png")));
        this.image = image;
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
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
    
    public void moveForward()
    {
        x = x + (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
    }
    
    public void moveBack()
    {
        x = x - (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y - (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
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
