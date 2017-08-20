/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Point;

/**
 *
 * @author Christopher
 */
public class Tank {
    
    private Point p;
    private double angle;
    
    public Tank(int x, int y)
    {
        p = new Point(x, y);
        angle = 90;
    }
    
    public int getX()
    {
        return p.x;
    }
    
    public int getY()
    {
        return p.y;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public void moveForward()
    {
        p.x = p.x + (int)(Math.cos(Math.toRadians(angle)) * 10);
        p.y = p.y + (int)(Math.sin(Math.toRadians(angle)) * 10);
    }
    
    public void moveBack()
    {
        p.x = p.x - (int)(Math.cos(Math.toRadians(angle)) * 10);
        p.y = p.y - (int)(Math.sin(Math.toRadians(angle)) * 10);
    }
    
    public void turnRight()
    {
        angle = angle + 4;
    }
    
    public void turnLeft()
    {
        angle = angle - 4;
    }
}
