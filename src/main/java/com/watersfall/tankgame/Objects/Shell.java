package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Shell extends Sprite
{
    private double penetration, velocity, shellDamage;

    public Shell()
    {
        super();
    }

    public Shell(float x, float y, int width, int height, double angle, Image image, TankData data)
    {
        super(x, y, width, height, angle, image);
        penetration = java.lang.Double.parseDouble(data.tankData[4]);
        velocity = java.lang.Double.parseDouble(data.tankData[5]) / 50.0;
        shellDamage = java.lang.Double.parseDouble(data.tankData[11]);
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterIntX(), this.getCenterIntY());
        g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
    }
    
    public void update()
    {
        this.setLocation(
            (float)(this.getX() + this.velocity * Math.cos(Math.toRadians(this.getAngle()))),
            (float)(this.getY() + this.velocity * Math.sin(Math.toRadians(this.getAngle())))
        );
    }
}