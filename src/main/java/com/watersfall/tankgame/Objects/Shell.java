package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;

import java.awt.Color;
import java.awt.Graphics2D;

public class Shell extends Sprite
{
    private double penetration, velocity, shellDamage;

    public Shell()
    {
        super();
    }

    public Shell(float x, float y, int width, int height, double angle, Color color, TankData data)
    {
        super(x, y, width, height, angle, color);
        penetration = java.lang.Double.parseDouble(data.tankData[4]);
        velocity = java.lang.Double.parseDouble(data.tankData[5]) / 50.0;
        shellDamage = java.lang.Double.parseDouble(data.tankData[11]);
        super.setType("SHELL");
    }

    public double getPenetration()
    {
        return penetration;
    }

    public void bounce(Sprite sprite, int side)
    {
        Main.sound.playSound("bounce");
        if(side == FRONT || side == BACK)
            this.setAngle((sprite.getAngle() + 180) * 2 - this.getAngle() - 180);
        else if (side == LEFT)
            this.setAngle((sprite.getAngle() * 2) - this.getAngle() + 180 - 180);
    }

    public double getDamage()
    {
        return this.shellDamage;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
        g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterIntX(), this.getCenterIntY());
        g2d.setColor(this.getColor());
        g2d.fillOval(this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight());
    }

	public void update()
    {
        //Since the shell updates ~8 times for every frame, it's movement is scaled down by 8
        this.setLocation(
            (float)(this.getX() + this.velocity * Math.cos(Math.toRadians(this.getAngle())) / 8),
            (float)(this.getY() + this.velocity * Math.sin(Math.toRadians(this.getAngle())) / 8)
        );
    }
}