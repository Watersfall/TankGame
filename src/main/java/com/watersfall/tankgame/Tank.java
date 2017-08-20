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
    private Turret turret;
    
    public Tank(int x, int y)
    {
        p = new Point(x, y);
        angle = 90;
        turret = new Turret(angle, p);
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
        turret.setLocation(new Point(p.x + 25, p.y + 25));
    }
    
    public void moveBack()
    {
        p.x = p.x - (int)(Math.cos(Math.toRadians(angle)) * 10);
        p.y = p.y - (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(p.x + 25, p.y + 25));
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
    
}
