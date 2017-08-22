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
public class Turret extends Rectangle implements ActionListener {
    
    
    double angle;
    private BufferedImage image;
    public boolean canShoot;
    private Timer reloadTimer;
    public double penetration;
    
    public Turret(int x, int y, double angle, BufferedImage image)
    {
        super(x, y, image.getWidth(), image.getHeight());
        penetration = 100;
        this.angle = angle;
        this.image = image;
        canShoot = true;
        reloadTimer = new Timer(5000, this);
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
    
    public void shoot()
    {
        reloadTimer.start();
        canShoot = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canShoot = true;
        reloadTimer.stop();
    }
}
