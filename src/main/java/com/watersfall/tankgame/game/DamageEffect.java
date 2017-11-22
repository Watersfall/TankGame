/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.watersfall.tankgame.Main;

/**
 *
 * @author Christopher
 */
public class DamageEffect {
    
    public double x;
    public double y;
    public double angle;
    private BufferedImage image;
    
    public DamageEffect(double x, double y, double angle, double otherAngle) throws IOException
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image = ImageIO.read(getClass().getResourceAsStream("/Images/HitEffects/HIT0.png"));
        Main.selectionFrame.frame.particles.add(new Particles(
            10, 
            otherAngle, 
            15, 
            this.x,
            this.y, 
            25, 
            2, 
            0.2, 
            -.02, 
            0.0075, 
            Color.GRAY)
        );
    }
    
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    public int getX()
    {
        return (int)Math.round(x);
    }
    
    public int getY()
    {
        return (int)Math.round(y);
    }
    
    public int getAngle()
    {
        return (int)Math.round(angle);
    }
}
