/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Christopher
 */
public class DamageEffect {
    
    public int x;
    public int y;
    public double angle;
    private BufferedImage image;
    
    public DamageEffect(int x, int y, double angle) throws IOException
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image = ImageIO.read(getClass().getResourceAsStream("/Images/HitEffects/HIT0.png"));
    }
    
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
}
