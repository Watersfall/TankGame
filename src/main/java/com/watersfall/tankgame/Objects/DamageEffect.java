/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.Objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.watersfall.tankgame.game.Renderer;

/**
 *
 * @author Christopher
 */
public class DamageEffect extends Sprite{
    
    public double x;
    public double y;
    public double angle;
    private Image image;
    
    public DamageEffect(double x, double y, double angle, double otherAngle) throws IOException
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.image = ImageIO.read(getClass().getResourceAsStream("/Images/HitEffects/HIT0.png"));
        /*Main.selectionFrame.frame.particles.add(new Particles(
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
            
        );*/
    }
    
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		
	}

	@Override
    public void update() 
    {
		
	}
}
