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
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */

//Class that represents the tankData
//Extends Rectangle because tankData are rectangles
//Implements ActionListener for the timer to work
public class Tank extends Rectangle2D implements ActionListener{
    
    //Information for the rectangle
    public double x;
    public double y;
    public double height;
    public double width;
    
    //Variables that are needed that are not in the Rectangle class
    //angle: the angle the tank is at in degrees
    //turret: this tankData turret
    //image: the image that the tank uses
    //DELAY: The delay of the timer
    //gameOverTime: the timer that delays the resetting of the game for DELAY milliseconds
    //The other doubles represent the tank's information
    public double angle;
    private Turret turret;
    private Image image;
    private final int DELAY = 2500;
    private Timer gameOverTimer;
    public double frontArmor, sideArmor, rearArmor, penetration, velocity, speed, turretRotation, tankRotation, reload, health, shellDamage;
    public ArrayList<DamageEffect> damage;
    
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
        super();
        
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        
        //Adding all the info to the tank from data
        frontArmor = java.lang.Double.parseDouble(data.tankData[1]);
        sideArmor = java.lang.Double.parseDouble(data.tankData[2]);
        rearArmor = java.lang.Double.parseDouble(data.tankData[3]);
        penetration = java.lang.Double.parseDouble(data.tankData[4]);
        velocity = java.lang.Double.parseDouble(data.tankData[5]) / 50.0;
        speed = java.lang.Double.parseDouble(data.tankData[6]) / 5.0;
        turretRotation = java.lang.Double.parseDouble(data.tankData[7]) / 10.0;
        tankRotation = java.lang.Double.parseDouble(data.tankData[8]);
        reload = java.lang.Double.parseDouble(data.tankData[9]) * 1000;
        health = java.lang.Double.parseDouble(data.tankData[10]);
        shellDamage = java.lang.Double.parseDouble(data.tankData[11]);
        
        damage = new ArrayList<DamageEffect>();
        
        //Timer to reset the game after a player has won
        gameOverTimer = new Timer(DELAY, this);
        this.angle = angle;
        this.image = image;
        
        //Creating the tank's turret and centering the turret on the tank
        turret = new Turret(x, y, angle, turretImage, penetration, velocity, turretRotation, shellDamage);
        turret.setLocation(x + (width / 2) - (turret.getWidth() / 2), y + (height / 2) - (turret.getHeight() / 2));
    }
    
    public double getAngle()
    {
        return angle;
    }
    
    //Method for moving the tank forward
    //The method also checks if it is outside the edges of the screen, and pushes it back in
    public void moveForward(Tank tank)
    {
        //Moving the tank in whatever angle it's facing
        double oldX = x;
        double oldY = y;
        x = x + (Math.cos(Math.toRadians(angle)) * this.speed);
        y = y + (Math.sin(Math.toRadians(angle)) * this.speed);

        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY());
        Shape shape = rotate.createTransformedShape(this);

        if(shape.intersects(Frame.TOP))
        {
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed);
        }
        if(shape.intersects(Frame.LEFT))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed);
        }
        if(shape.intersects(Frame.RIGHT))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed);
        }
        if(shape.intersects(Frame.BOTTOM))
        {
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed);
        }

        for(int i = 0; i < Main.selectionFrame.frame.map.obstacles.length; i++)
        {
            if(Main.selectionFrame.frame.map.obstacles[i] != null)
            {
                int o = 0;
                String collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                while(Main.selectionFrame.frame.map.obstacles[i] != null && collide != null)
                {
                    if(collide.equals("TOP"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle < 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle < 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle > 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle > 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("BOTTOM"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 180 
                    || this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 0 
                    || this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 360)
                    {
                        x = oldX;
                        y = oldY;
                    }
                    o++;
                    if(o > 8)
                    {
                        o = 0;
                        x = oldX;
                        y = oldY;
                        break;
                    }
                    collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                }
            }
        }

        //Moving the turret with the tank
        turret.setLocation(x + (width / 2) - (turret.getWidth() / 2), y + (height / 2) - (turret.getHeight() / 2));
        
        for(int i = 0; i < this.damage.size(); i++)
        {
            damage.get(i).setLocation(damage.get(i).x - (oldX - this.x), damage.get(i).y - (oldY - this.y));
        } 
    }
    
    //Method for moving the tank backward
    //The method also checks if it is outside the edges of the screen, and pushes it back in
    public void moveBack(Tank tank)
    {
        //Moving the tank backwards in whatever angle it's facing
        double oldX = x;
        double oldY = y;
        x = x - (Math.cos(Math.toRadians(angle)) * (this.speed / 3));
        y = y - (Math.sin(Math.toRadians(angle)) * (this.speed / 3));
        
        //Checking if the tank is out of bounds, and if it is, moving it back in bounds
        //THIS NEEDS TO BE FIXED, BUT IS FINE FOR NOW
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY());
        Shape shape = rotate.createTransformedShape(this);

        if(shape.intersects(Frame.TOP))
        {
            y = y + (Math.sin(Math.toRadians(angle)) * (this.speed / 3));
        }
        if(shape.intersects(Frame.LEFT))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * (this.speed / 3));
        }
        if(shape.intersects(Frame.RIGHT))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * (this.speed / 3));
        }
        if(shape.intersects(Frame.BOTTOM))
        {
            y = y + (Math.sin(Math.toRadians(angle)) * (this.speed / 3));
        }
        
        for(int i = 0; i < Main.selectionFrame.frame.map.obstacles.length; i++)
        {
            if(Main.selectionFrame.frame.map.obstacles[i] != null)
            {
                int o = 0;
                String collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                while(Main.selectionFrame.frame.map.obstacles[i] != null && collide != null)
                {
                    if(collide.equals("TOP"))
                    {
                        y = y + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        x = x + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(collide.equals("LEFT") && this.angle < 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(collide.equals("RIGHT") && this.angle < 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(collide.equals("LEFT") && this.angle > 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(collide.equals("RIGHT") && this.angle > 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(collide.equals("BOTTOM"))
                    {
                        y = y + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                        x = x + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * (this.speed / 3));
                    }
                    if(this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 180 
                    || this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 0 
                    || this.angle - Main.selectionFrame.frame.map.obstacles[i].angle % 360 == 360)
                    {
                        x = oldX;
                        y = oldY;
                    }
                    o++;
                    if(o > 8)
                    {
                        o = 0;
                        x = oldX;
                        y = oldY;
                        break;
                    }
                    collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                }
            }
        }

        for(int i = 0; i < this.damage.size(); i++)
        {
            damage.get(i).setLocation(damage.get(i).x - (oldX - this.x), damage.get(i).y - (oldY - this.y));
        }
        
        //Moving the turret with the tank
        turret.setLocation(x + (width / 2) - (turret.getWidth() / 2), y + (height / 2) - (turret.getHeight() / 2));
    }
    
    //Turns the tankData to the right
    //Also turns the turret to the right
    public void turnRight(Tank tank)
    {
        double oldX = x;
        double oldY = y;
        angle = (angle + this.tankRotation) % 360;
        turret.turnWithTank("RIGHT", tankRotation);

        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY());
        Shape shape = rotate.createTransformedShape(this);
        if(shape.intersects(Frame.TOP))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y + (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.LEFT))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y + (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.RIGHT))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.BOTTOM))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }

        for(int i = 0; i < Main.selectionFrame.frame.map.obstacles.length; i++)
        {
            if(Main.selectionFrame.frame.map.obstacles[i] != null)
            {
                int o = 0;
                String collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                while(Main.selectionFrame.frame.map.obstacles[i] != null && collide != null)
                {
                    if(collide.equals("TOP"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle < 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle < 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle > 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle > 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("BOTTOM"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    o++;
                    if(o > 8)
                    {
                        o = 0;
                        break;
                    }
                    collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                }
            }
        }

        for(int i = 0; i < this.damage.size(); i++)
        {
            damage.get(i).setLocation(damage.get(i).x - (oldX - this.x), damage.get(i).y - (oldY - this.y));
        } 
        
        //Moving the turret with the tank
        turret.setLocation(x + (width / 2) - (turret.getWidth() / 2), y + (height / 2) - (turret.getHeight() / 2));
    }
    
    //Turns the tank to the left
    //Also turns the turret to the left
    public void turnLeft(Tank tank)
    {
        double oldX = x;
        double oldY = y;
        angle = (angle - this.tankRotation + 360) % 360;
        turret.turnWithTank("LEFT", tankRotation);

        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY());
        Shape shape = rotate.createTransformedShape(this);
        if(shape.intersects(Frame.TOP))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y + (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.LEFT))
        {
            x = x + (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y + (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.RIGHT))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }
        if(shape.intersects(Frame.BOTTOM))
        {
            x = x - (Math.cos(Math.toRadians(angle)) * this.speed) * Math.cos(Math.toRadians(this.angle));
            y = y - (Math.sin(Math.toRadians(angle)) * this.speed) * Math.sin(Math.toRadians(this.angle));
        }

        for(int i = 0; i < Main.selectionFrame.frame.map.obstacles.length; i++)
        {
            
            if(Main.selectionFrame.frame.map.obstacles[i] != null)
            {
                int o = 0;
                String collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                while(Main.selectionFrame.frame.map.obstacles[i] != null && collide != null)
                {
                    if(collide.equals("TOP"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle < 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle < 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("LEFT") && this.angle > 180)
                    {
                        x = x + (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y + (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("RIGHT") && this.angle > 180)
                    {
                        x = x - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        y = y - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    if(collide.equals("BOTTOM"))
                    {
                        y = y - (Math.sin(Math.toRadians(angle - Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                        x = x - (Math.sin(Math.toRadians(Main.selectionFrame.frame.map.obstacles[i].angle)) * this.speed);
                    }
                    o++;
                    if(o > 8)
                    {
                        o = 0;
                        break;
                    }
                    collide = Main.selectionFrame.frame.map.obstacles[i].checkTankCollision(this);
                }
            }
        }

        for(int i = 0; i < this.damage.size(); i++)
        {
            damage.get(i).setLocation(damage.get(i).x - (oldX - this.x), damage.get(i).y - (oldY - this.y));
        } 

        //Moving the turret with the tank
        turret.setLocation(x + (width / 2) - (turret.getWidth() / 2), y + (height / 2) - (turret.getHeight() / 2));
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
        gameOverTimer.start();
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
    
    public void addDamage(int x, int y, double angle) throws IOException
    {
        damage.add(new DamageEffect(x + (this.x - x) / 8, y + (this.y - y) / 8, angle));
    }

    @Override
    public void setRect(double x, double y, double w, double h) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int outcode(double x, double y) 
    {
        int out = 0;
        if (this.width <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;
        } else if (x < this.x) {
            out |= OUT_LEFT;
        } else if (x > this.x + (double) this.width) {
            out |= OUT_RIGHT;
        }
        if (this.height <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;
        } else if (y < this.y) {
            out |= OUT_TOP;
        } else if (y > this.y + (double) this.height) {
            out |= OUT_BOTTOM;
        }
        return out;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D r) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D r) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
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
