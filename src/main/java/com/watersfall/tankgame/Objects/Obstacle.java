package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.game.Renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Obstacle extends Sprite
{
    private double hp = 5000;
    private boolean destroyed = false;

    public Obstacle()
    {
        super();
    }

    public Obstacle(float x, float y, int width, int height, double angle, Image image)
    {
        super(x, y, width, height, angle, image);
    }

    public Obstacle(float x, float y, int width, int height, int collisionWidth, int collisionHeight, double angle, Image image)
    {
        super(x, y, width, height, x + ((width - collisionWidth) / 2), y + ((height - collisionHeight) / 2), collisionWidth, collisionHeight, angle, image);
    }

    /**
     * @param damage the amount of damage to deal to the obstacle
     * @return true if destroyed, false if not
     */
    public boolean damage(double damage)
    {
        this.hp -= damage;
        if(hp <= 0)
            return this.destroyed = true;
        return this.destroyed;
    }

    public boolean isDestroyed()
    {
        return this.destroyed;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
        if(!destroyed)
        {
            g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterX(), this.getCenterY());
            g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
        }
	}

	@Override
    public void update() 
    {
        if(hp <= 0)
            this.destroyed = true;
    }
}