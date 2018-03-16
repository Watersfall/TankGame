package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;

public class Turret extends Sprite implements ActionListener
{
    private double turretRotation, reload;
    public boolean turnLeft, turnRight, reloaded;
    Timer reloadTimer;

    public Turret()
    {
        super();
    }

    public Turret(float x, float y, int width, int height, double angle, Image image, TankData data)
    {
        super(x, y, width, height, angle, image);
        turretRotation = java.lang.Double.parseDouble(data.tankData[7]) / 10.0;
        reload = java.lang.Double.parseDouble(data.tankData[9]) * 1000;
        reloadTimer = new Timer((int)reload, this);
    }

    public void turnLeft()
    {
        this.setAngle(this.getAngle() + this.turretRotation);
    }

    public void turnRight()
    {
        this.setAngle(this.getAngle() - this.turretRotation);
    }

    public void shoot()
    {
        if(reloaded)
        {
            this.reloaded = false;
        }
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
        g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterIntX(), this.getCenterIntY());
        g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
	}

	@Override
    public void update() 
    {
		if(this.turnLeft)
            this.turnLeft();
        if(this.turnRight)
            this.turnRight();
    }

	@Override
    public void actionPerformed(ActionEvent e) 
    {
		this.reloaded = true;
	}
}