/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */

//Class representing the turret on the tank
//Extends rectangle because turrets are basically rectangles
//implements ActionListener for the reload timer to work
public class Turret extends Rectangle implements ActionListener {
    
    //angle: the angle the turret is facing
    //image: the image the turret uses
    //canShoot: true if the turret is reloaded and can shoot, false if not
    //reloadTimer: The timer for the reloading of the tank
    //penetration: how much armor the shell from this turret can penetrate
    double angle;
    private BufferedImage image;
    public boolean canShoot;
    private Timer reloadTimer;
    public double penetration, velocity, turretRotation;
    
    //x: the x location for the turret
    //y: the y location for the turret
    //angle: the default angle of the turret
    //image: the image to represent the turret
    //penetration: how much armor the shell from this turret can penetrate
    public Turret(int x, int y, double angle, BufferedImage image, double penetration, double velocity, double turretRotation)
    {
        //Calling the super to create the rectangle
        super(x, y, image.getWidth(), image.getHeight());
        this.penetration = penetration;
        this.angle = angle;
        this.image = image;
        canShoot = true;
        reloadTimer = new Timer(5000, this);
        this.velocity = velocity;
        this.turretRotation = turretRotation;
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
    public void setLocation(Point p)
    {
        this.x = p.x;
        this.y = p.y;
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
}
