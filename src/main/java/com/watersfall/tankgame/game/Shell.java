/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.IOException;

/**
 *
 * @author Christopher
 */
public class Shell extends Rectangle2D {
    
    public double x, y, height, width;
    private Turret turret;
    public double angle;
    private String HITSIDE;
    
    public Shell(Turret turret)
    {
        super();
        this.turret = turret;
        angle = turret.getAngle();
        this.x = (int)(turret.getCenterX() + ((turret.getWidth() / 2) * Math.cos(Math.toRadians(angle))));
        this.y = (int)(turret.getCenterY() + ((turret.getWidth() / 2) * Math.sin(Math.toRadians(angle))));
        this.width = 15;
        this.height = 7;
        this.x = (turret.getCenterX() - (this.width / 2) + ((turret.getWidth() / 2) * Math.cos(Math.toRadians(angle))));
        this.y = (turret.getCenterY() - (this.height / 2) + ((turret.getWidth() / 2) * Math.sin(Math.toRadians(angle))));
    }
    
    public void move()
    {
        x = x + (Math.cos(Math.toRadians(angle)) * 15);
        y = y + (Math.sin(Math.toRadians(angle)) * 15);
    }
    
    private void moveBack()
    {
        x = x - (Math.cos(Math.toRadians(angle)) * 15);
        y = y - (Math.sin(Math.toRadians(angle)) * 15);
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
        //This first one needs to use the get methods for some reason
        //It didn't work with just the variables
        //All the others do
        Line2D front = new Line2D.Double(tank.getX() + tank.getWidth(), tank.getY(), tank.getX() + tank.getWidth(), tank.getY() + tank.getY());
        Line2D left = new Line2D.Double(tank.x, tank.y, tank.x + tank.width, tank.y);
        Line2D right = new Line2D.Double(tank.x, tank.y + tank.height, tank.x + tank.width, tank.y + tank.height);
        Line2D back = new Line2D.Double(tank.x, tank.y, tank.x, tank.y + tank.height);
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(tank.getAngle()), tank.getX() + tank.width / 2, tank.getY() + tank.height / 2);
        Shape frontShape = rotate.createTransformedShape(front);
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(tank.getAngle()), tank.getX() + tank.width / 2, tank.getY() + tank.height / 2);
        Shape leftShape = rotate.createTransformedShape(left);
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(tank.getAngle()), tank.getX() + tank.width / 2, tank.getY() + tank.height / 2);
        Shape rightShape = rotate.createTransformedShape(right);
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(tank.getAngle()), tank.getX() + tank.width / 2, tank.getY() + tank.height / 2);
        Shape backShape = rotate.createTransformedShape(back);
        tank.addDamage((int)(this.getCenterX()), (int)(this.getCenterY()), tank.angle);
        if(rightShape.intersects(this) || leftShape.intersects(this))
        {
            HITSIDE = "SIDE";
            return (this.turret.penetration > tank.sideArmor / Math.abs(Math.sin(Math.toRadians(this.angle - tank.getAngle()))));
        }
        if(frontShape.intersects(this))
        {
            HITSIDE = "FRONT";
            return (this.turret.penetration > tank.frontArmor / Math.abs(Math.cos(Math.toRadians(this.angle - tank.getAngle()))));
        }
        if(backShape.intersects(this))
        {
            HITSIDE = "BACK";
            return (this.turret.penetration > tank.rearArmor / Math.abs(Math.cos(Math.toRadians(this.angle - tank.getAngle()))));
        }
        return false;
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
