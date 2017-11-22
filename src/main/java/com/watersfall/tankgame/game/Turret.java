/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.geom.Rectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import com.watersfall.tankgame.ui.Frame;
import com.watersfall.tankgame.Main;

/**
 *
 * @author Christopher
 */

//Class representing the turret on the tank
//Extends rectangle because turrets are basically rectangles
//implements ActionListener for the reload timer to work
public class Turret extends Rectangle2D implements ActionListener {
    
    public double x;
    public double y;
    public double height;
    public double width;
    
    //angle: the angle the turret is facing
    //image: the image the turret uses
    //canShoot: true if the turret is reloaded and can shoot, false if not
    //reloadTimer: The timer for the reloading of the tank
    //penetration: how much armor the shell from this turret can penetrate
    double angle;
    private BufferedImage image;
    public boolean canShoot;
    private Timer reloadTimer;
    public double penetration, velocity, turretRotation, shellDamage;
    
    //x: the x location for the turret
    //y: the y location for the turret
    //angle: the default angle of the turret
    //image: the image to represent the turret
    //penetration: how much armor the shell from this turret can penetrate
    public Turret(int x, int y, double angle, BufferedImage image, double penetration, double velocity, double turretRotation, double shellDamage)
    {
        //Calling the super to create the rectangle
        super();
        image = Main.resize(image, (int)(image.getWidth() * Frame.SCALE_X), (int)(image.getHeight() * Frame.SCALE_Y));
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.penetration = penetration;
        this.angle = angle;
        this.image = image;
        canShoot = true;
        reloadTimer = new Timer(5000, this);
        this.velocity = velocity;
        this.turretRotation = turretRotation;
        this.shellDamage = shellDamage;
    }
    
    //Method to rotate the turret left
    public void turnLeft()
    {
        angle = angle - turretRotation;
    }
    
    //Method to rotate the turret right
    public void turnRight()
    {
        angle = angle + turretRotation;
    }
    
    public void turnWithTank(String direction, double turretRotation)
    {
        if(direction.equals("LEFT"))
            angle = angle - turretRotation;
        else
            angle = angle + turretRotation;
    }
    
    //Method to update the location of the turret
    //Used for when the tank moves
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    //Method to shoot the turret
    //Sets the turret to be unable to shoot and starts the reload timer
    public void shoot()
    {
        reloadTimer.start();
        canShoot = false;
    }

    //Event for when the turret finishes reloading
    //Sets the turret to be able to shoot and stops the timer
    @Override
    public void actionPerformed(ActionEvent e) {
        canShoot = true;
        reloadTimer.stop();
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
