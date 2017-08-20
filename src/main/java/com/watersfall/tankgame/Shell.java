/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

/**
 *
 * @author Christopher
 */
public class Shell {
    
    private Turret turret;
    public int x;
    public int y;
    private double angle;
    
    public Shell(Turret turret)
    {
        this.turret = turret;
        x = turret.getX();
        y = turret.getY();
        angle = turret.getAngle();
    }
    
    public void move()
    {
        x = x + (int)(Math.cos(Math.toRadians(angle)) * 15);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * 15);
    }
    
    public boolean outOfBounds()
    {
        return false;
    }
}
