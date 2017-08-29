/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.TankData;
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

//Class that represents the tanks
//Extends Rectangle because tanks are rectangles
//Implements ActionListener for the timer to work
public class Tank extends Rectangle implements ActionListener{
    
    //Variables that are needed that are not in the Rectangle class
    //angle: the angle the tank is at in degrees
    //turret: this tanks turret
    //image: the image that the tank uses
    //DELAY: The delay of the timer
    //gameOverTime: the timer that delays the resetting of the game for DELAY milliseconds
    //The other doubles represent the tank's information
    public double angle;
    private Turret turret;
    private Image image;
    private final int DELAY = 2500;
    private Timer gameOverTimer;
    public double frontArmor, sideArmor, rearArmor, penetration, velocity, speed, turretRotation, tankRotation, reload;
    
    //x: the x location of the tank
    //y: the y location of the tank
    //height: the vertical size of the tank
    //width: the horizonal size of the tnak
    //angle: the starting angle of the tank
    //image: the image the tank uses
    //turretImage: the image passed to the turret
    //data: the TankData object that contains the armor and other stats for the tank
    public Tank(int x, int y, int height, int width, double angle, BufferedImage image, BufferedImage turretImage, TankData data) throws IOException
    {
        //Calling the super to create the basic rectangle
        super(x, y, width, height);
        
        //Adding all the info to the tank from data
        frontArmor = java.lang.Double.parseDouble(data.tankData[1]);
        sideArmor = java.lang.Double.parseDouble(data.tankData[2]);
        rearArmor = java.lang.Double.parseDouble(data.tankData[3]);
        penetration = java.lang.Double.parseDouble(data.tankData[4]);
        velocity = 15;
        speed = 10;
        turretRotation = 4;
        tankRotation = 2;
        reload = 5000;
        
        //Timer to reset the game after a player has won
        gameOverTimer = new Timer(DELAY, this);
        this.angle = angle;
        this.image = image;
        
        //Creating the tank's turret and centering the turret on the tank
        turret = new Turret(x, y, angle, turretImage, penetration, velocity, turretRotation);
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
    
    //Method for moving the tank forward
    //The method also checks if it is outside the edges of the screen, and pushes it back in
    public void moveForward()
    {
        //Moving the tank in whatever angle it's facing
        x = x + (int)(Math.cos(Math.toRadians(angle)) * this.speed);
        y = y + (int)(Math.sin(Math.toRadians(angle)) * this.speed);
        
        //Moving the turret with the tank
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
        
        //Checking if the tank is out of bounds, and if it is, moving it back in bounds
        //THIS NEEDS TO BE FIXED, BUT IS FINE FOR NOW
        if ((getMaxX() + (width * Math.cos(Math.toRadians(angle))) <= 1))
        {
            x += this.speed;
        }
        if (getMaxY() + (height * Math.sin(Math.toRadians(angle))) <= 1)
        {
            y += this.speed;
        }
        if (getMinX() + (width * Math.cos(Math.toRadians(angle))) >= Main.selectionFrame.frame.getWidth())
        {
            x -= this.speed;
        }
        if(getMinY() + (height * Math.cos(Math.toRadians(angle))) >= Main.selectionFrame.frame.getHeight())
        {
            y -= this.speed;
        }
    }
    
    //Method for moving the tank backward
    //The method also checks if it is outside the edges of the screen, and pushes it back in
    public void moveBack()
    {
        //Moving the tank backwards in whatever angle it's facing
        x = x - (int)(Math.cos(Math.toRadians(angle)) * (this.speed / 3));
        y = y - (int)(Math.sin(Math.toRadians(angle)) * (this.speed / 3));
        
        //Moving the turret with the tank
        turret.setLocation(new Point(x + (width / 2) - (turret.width / 2), y + (height / 2) - (turret.height / 2)));
        
        //Checking if the tank is out of bounds, and if it is, moving it back in bounds
        //THIS NEEDS TO BE FIXED, BUT IS FINE FOR NOW
        if ((getMaxX() + (width * Math.cos(Math.toRadians(angle))) <= 0))
        {
            x += this.speed / 3;
        }
        if (getMaxY() + (height * Math.sin(Math.toRadians(angle))) <= 0)
        {
            y += this.speed / 3;
        }
        if (getMinX() + (width * Math.cos(Math.toRadians(angle))) >= 1920)
        {
            x -= this.speed / 3;
        }
        if(getMinY() + (height * Math.cos(Math.toRadians(angle))) >= 1080)
        {
            y -= this.speed / 3;
        }
    }
    
    //Turns the tanks to the right
    //Also turns the turret to the right
    public void turnRight()
    {
        angle = (angle + this.tankRotation) % 360;
        turret.turnWithTank("RIGHT", tankRotation);
    }
    
    //Turns the tank to the left
    //Also turns the turret to the left
    public void turnLeft()
    {
        angle = (angle - this.tankRotation) % 360;
        turret.turnWithTank("LEFT", tankRotation);
    }
    
    public Turret getTurret()
    {
        return turret;
    }
    
    public Image getImage()
    {
        return image;
    } 
    
    //Method called when the tank is shot and killed
    //Sets the position of the tank to a spot off the screen
    //Triggers the game over timer
    public void destroy() throws InterruptedException, IOException
    {
        x = -10000;
        y = -10000;
        turret.x = -10000;
        turret.y = -10000;
        Frame.gameOver = true;
        gameOverTimer.start();
    }
    
    //Method to check if the tank is out of bounds
    public boolean outOfBounds()
    {
        return (this.getMinY() < 0 || this.getMinX() < 0 || this.getMaxX() > 1920 || this.getMaxY() > 1080);
    }

    //Action listener for the timer
    //Calls the Frame's reset() method after DELAY milliseconds
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        try 
        {
            Main.selectionFrame.frame.reset();
            gameOverTimer.stop();
        } 
        catch (InterruptedException | IOException ex) 
        {
            Logger.getLogger(Tank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
