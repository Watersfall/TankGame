
package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.game.Renderer;
import java.awt.Color;
import java.awt.Graphics2D;

public class Particle extends Sprite
{
    private double x;
    private double y;
    private double angle;
    private int size;
    private double velocity;
    private double acceleration;
    private Color color;
    private double initialVelocity;

    public Particle(double x, double y, double angle, int size, double velocity, double acceleration, Color color)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.size = size;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.color = color;
        this.initialVelocity = velocity;
    }

    public void move()
    {
        x = x + velocity * Math.cos(Math.toRadians(angle));
        y = y + velocity * Math.sin(Math.toRadians(angle));
        velocity = velocity + acceleration;
        if(velocity < 0)
        {
            velocity = 0;
        }
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(velocity / initialVelocity * 255));
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		g2d.setColor(this.color);
        g2d.fillOval((int)x, (int)y, size, size);
	}

	@Override
    public void update() 
    {
		this.move();
	}
}