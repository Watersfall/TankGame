/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import com.watersfall.tankgame.Main;
import data.TankData;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */
public class Tank extends Rectangle implements ActionListener{
    
    private double angle;
    private Turret turret;
    private Image image;
    private Timer gameOverTimer;
    public double frontArmor, sideArmor, rearArmor, penetration;
    
    public Tank(int x, int y, int height, int width, double angle, BufferedImage image, BufferedImage turretImage, TankData data) throws IOException
    {
        super(x, y, width, height);
        frontArmor = java.lang.Double.parseDouble(data.tankData[1]);
        System.out.println(frontArmor);
        sideArmor = java.lang.Double.parseDouble(data.tankData[2]);
        rearArmor = java.lang.Double.parseDouble(data.tankData[3]);
        penetration = java.lang.Double.parseDouble(data.tankData[4]);
        gameOverTimer = new Timer(2500, this);
        this.angle = angle;
        turret = new Turret(x, y, angle, turretImage, penetration);
        this.image = image;
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
    }
    
    public double getX()
    {
        return x;
    }
    
    public double getY()
    {
        return y;
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    public void moveForward()
    {
        x = x + (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
        if ((getMaxX() + (width * Math.cos(Math.toRadians(angle))) <= 1))
        {
            x += 10;
        }
        if (getMaxY() + (height * Math.sin(Math.toRadians(angle))) <= 1)
        {
            y += 10;
        }
        if (getMinX() + (width * Math.cos(Math.toRadians(angle))) >= Main.selectionFrame.frame.getWidth())
        {
            x -= 10;
        }
        if(getMinY() + (height * Math.cos(Math.toRadians(angle))) >= Main.selectionFrame.frame.getHeight())
        {
            y -= 10;
        }
    }
    
    public void moveBack()
    {
        x = x - (int)(Math.cos(Math.toRadians(angle)) * 10);
        y = y - (int)(Math.sin(Math.toRadians(angle)) * 10);
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
        if ((getMaxX() + (width * Math.cos(Math.toRadians(angle))) <= 0))
        {
            x += 10;
        }
        if (getMaxY() + (height * Math.sin(Math.toRadians(angle))) <= 0)
        {
            y += 10;
        }
        if (getMinX() + (width * Math.cos(Math.toRadians(angle))) >= 1920)
        {
            x -= 10;
        }
        if(getMinY() + (height * Math.cos(Math.toRadians(angle))) >= 1080)
        {
            y -= 10;
        }
    }
    
    public void turnRight()
    {
        angle = (angle + 4) % 360;
        turret.turnRight();
    }
    
    public void turnLeft()
    {
        angle = (angle - 4) % 360;
        turret.turnLeft();
    }
    
    public Turret getTurret()
    {
        return turret;
    }
    
    public Image getImage()
    {
        return image;
    } 
    
    public void destroy() throws InterruptedException, IOException
    {
        x = -10000;
        y = -10000;
        turret.x = -10000;
        turret.y = -10000;
        Frame.gameOver = true;
        gameOverTimer.start();
    }
    
    public boolean outOfBounds()
    {
        return (this.getMinY() < 0 || this.getMinX() < 0 || this.getMaxX() > 1920 || this.getMaxY() > 1080);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        try {
            Main.selectionFrame.frame.reset();
            gameOverTimer.stop();
        } catch (InterruptedException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
