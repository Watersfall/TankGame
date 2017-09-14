/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.IOException;

/**
 *
 * @author Christopher
 */
public class Shell extends Rectangle2D {
    
    public double x, y, height, width;
    private Turret turret;
    public double angle, velocity;
    private String HITSIDE;
    public Rectangle rec;
    
    public Shell(Turret turret)
    {
        super();
        rec = null;
        this.turret = turret;
        angle = turret.getAngle();
        this.x = (int)(turret.getCenterX() + ((turret.getWidth() / 2) * Math.cos(Math.toRadians(angle))));
        this.y = (int)(turret.getCenterY() + ((turret.getWidth() / 2) * Math.sin(Math.toRadians(angle))));
        this.width = 15;
        this.height = 7;
        this.x = (turret.getCenterX() - (this.width / 2) + ((turret.getWidth() / 2) * Math.cos(Math.toRadians(angle))));
        this.y = (turret.getCenterY() - (this.height / 2) + ((turret.getWidth() / 2) * Math.sin(Math.toRadians(angle))));
        this.velocity = turret.velocity;
    }
    
    public void move()
    {
        x = x + (Math.cos(Math.toRadians(angle)) * this.velocity);
        y = y + (Math.sin(Math.toRadians(angle)) * this.velocity);
    }

    public void moveBack()
    {
        x = x - (Math.cos(Math.toRadians(angle)) * this.velocity);
        y = y - (Math.sin(Math.toRadians(angle)) * this.velocity);
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
    
    public Boolean checkPenetration(Tank tank) throws IOException
    {   
        //Instead of dealing with the shell and tank being at angles
        //It is much easier to rotate them back to 0 degrees
        //tempX and tempY are variables that hold the X and Y of this shell rotated back to 0 degrees with reference to the tank
        //Since the tank is always at 0 degrees, it does not need to be rotated
        this.moveBack();
        double tempX = Math.cos(Math.toRadians(tank.angle)) 
        * (this.getCenterX() - tank.getCenterX()) - Math.sin(Math.toRadians(tank.angle)) 
        * (this.getCenterY() - tank.getCenterY()) + tank.getCenterX();
        double tempY = Math.sin(Math.toRadians(tank.angle)) 
        * (this.getCenterX() - tank.getCenterX()) - Math.cos(Math.toRadians(tank.angle)) 
        * (this.getCenterY() - tank.getCenterY()) + tank.getCenterY();

        //Checking which side the shell is hitting the tank at
        //This sets the HITSIDE to whatever side it hits so it can correctly calculate the bounce angle
        //It also checks if the shell penetrates or bounces, based off the penetration of this shell and the armor of the tank
        if(tempX > tank.getCenterX() && tempY > tank.y && tempY < tank.y + tank.height)
        {
            HITSIDE = "FRONT";
            return (this.turret.penetration > tank.frontArmor / Math.abs(Math.cos(Math.toRadians(this.angle - tank.getAngle()))));
        }
        else
        {
            if(tempX < tank.getCenterX() && tempY > tank.y && tempY < tank.y + tank.height)
            {
                HITSIDE = "BACK";
                return (this.turret.penetration > tank.rearArmor / Math.abs(Math.cos(Math.toRadians(this.angle - tank.getAngle()))));
            }
            else
            {
                HITSIDE = "SIDE";
                return (this.turret.penetration > tank.sideArmor / Math.abs(Math.sin(Math.toRadians(this.angle - tank.getAngle()))));
            }
        }
    }

    public void bounce(Tank tank) 
    {
        if(this.HITSIDE != null && this.HITSIDE.equals("SIDE"))
        {
            this.angle = ((tank.angle - 180) * 2) - this.angle;
        }
        else
        {
            this.angle = ((tank.angle) * 2) - this.angle - 180;
        }
        this.move();
    }
    
    public double getAngle()
    {
        return angle;
    }

    @Override
    public void setRect(double x, double y, double w, double h) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int outcode(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

        public double getX()
    {
        return getIntX();
    }
    
    public double getY()
    {
        return getIntY();
    }

    @Override
    public double getWidth() 
    {
        return getIntWidth();
    }

    @Override
    public double getHeight() 
    {
        return getIntHeight();
    }

    @Override
    public boolean isEmpty() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int getIntX()
    {
        return (int)Math.round(x);
    }
    
    public int getIntY()
    {
        return (int)Math.round(y);
    }
    
    public int getIntWidth()
    {
        return (int)Math.round(width);
    }
    
    public int getIntHeight()
    {
        return (int)Math.round(height);
    }
}
