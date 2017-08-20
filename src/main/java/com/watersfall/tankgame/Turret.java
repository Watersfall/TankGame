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
public class Turret {
    
    
    double angle;
    Point p;
    
    public Turret(Double angle, Point p)
    {
        this.angle = angle;
        this.p = p;
        p.x = p.x + 25;
        p.y = p.y + 25;
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
        this.p = p;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public int getX()
    {
        return p.x;
    }
    
    public int getY()
    {
        return p.y;
    }
}
