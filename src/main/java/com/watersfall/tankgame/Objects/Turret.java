package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

public class Turret extends Sprite implements ActionListener
{
    private double turretRotation, reload;
    public boolean turnLeft, turnRight;
    private boolean reloaded;
    private TankData data;
    Timer reloadTimer;

    public Turret()
    {
        super();
    }

    public Turret(float x, float y, int width, int height, double angle, Image image, TankData data)
    {
        super(x, y, width, height, angle, Main.resize((BufferedImage) image, image.getWidth(null), image.getHeight(null)));
        turretRotation = java.lang.Double.parseDouble(data.tankData[7]) / 10.0;
        reload = java.lang.Double.parseDouble(data.tankData[9]) * 1000;
        reloadTimer = new Timer((int)reload, this);
        this.data = data;
        reloaded = true;
    }

    public void turnLeft()
    {
        this.setAngle(this.getAngle() - this.turretRotation);
    }

    public void turnLeft(double angle)
    {
        this.setAngle(this.getAngle() - angle);
    }

    public void turnRight()
    {
        this.setAngle(this.getAngle() + this.turretRotation);
    }

    public void turnRight(double angle)
    {
        this.setAngle(this.getAngle() + angle);
    }

    public boolean shoot()
    {
        if(reloaded)
        {
            Main.sound.playSound("shoot");
            this.reloadTimer.start();
            reloaded = false;
            this.reloadTimer.start();
            return true;
        }
        return false;
    }

    public TankData getShellData()
    {
        return data;
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