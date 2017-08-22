/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Christopher
 */
public class Shell extends Rectangle {
    
    private Turret turret;
    private double angle;
    
    public Shell(Turret turret)
    {
        super((int)turret.getCenterX(), (int)turret.getCenterY());
        this.turret = turret;
        angle = turret.getAngle();
        x = (int)(turret.getCenterX() + (turret.getWidth() / 2 * Math.cos(Math.toRadians(angle))));
        y = (int)(turret.getCenterY() + (turret.getHeight() * Math.sin(Math.toRadians(angle))));
        width = 10;
        height = 10;
        
        
    }
    
    public void move()
    {
        x = x + (int)(Math.cos(Math.toRadians(angle)) * 15);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * 15);
    }
    
    public boolean outOfBounds()
    {
        return (x > 1920 || x < 0 || y > 1080 || y < 0);
    }
    
    public boolean checkCollision(Tank tank)
    {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank.getAngle()), tank.getCenterX(), tank.getCenterY());
        Shape shape = transform.createTransformedShape(tank);
        return shape.intersects(this);
    }
}
