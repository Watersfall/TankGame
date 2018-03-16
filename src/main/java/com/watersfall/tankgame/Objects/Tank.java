package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Tank extends Sprite
{
    private double frontArmor, sideArmor, rearArmor, speed, tankRotation, health;
    private String name, nation;
    private Turret turret;
    public boolean moveForward, moveBackward, turnLeft, turnRight;

    public Tank()
    {
        super();
    }

    public Tank(float x, float y, int width, int height, double angle, Image image, Image turretImage, TankData data)
    {
        super(x, y, width, height, angle, image);
        name = data.tankData[0];
        frontArmor = java.lang.Double.parseDouble(data.tankData[1]);
        sideArmor = java.lang.Double.parseDouble(data.tankData[2]);
        rearArmor = java.lang.Double.parseDouble(data.tankData[3]);
        speed = java.lang.Double.parseDouble(data.tankData[6]) / 5.0;
        tankRotation = java.lang.Double.parseDouble(data.tankData[8]);
        health = java.lang.Double.parseDouble(data.tankData[10]);
        nation = data.tankData[12];
        this.turret = new Turret(x, y, turretImage.getWidth(null), turretImage.getHeight(null), angle, turretImage, data);
    }

    public void moveForward()
    {
        this.setX(this.getX() + (float)(Math.cos(Math.toRadians(this.getAngle())) * this.speed));
        this.setY(this.getY() + (float)(Math.sin(Math.toRadians(this.getAngle())) * this.speed));
    }

    public void moveBackward()
    {
        this.setX(this.getX() - (float)(Math.cos(Math.toRadians(this.getAngle())) * this.speed));
        this.setY(this.getY() - (float)(Math.sin(Math.toRadians(this.getAngle())) * this.speed));
    }

    public void turnRight()
    {
        this.setAngle(this.getAngle() + this.tankRotation);
    }

    public void turnLeft()
    {
        this.setAngle(this.getAngle() - this.tankRotation);
    }

    public void destroy()
    {
        
    }

    public Turret getTurret()
    {
        return this.turret;
    }

    public void setMoveForward(boolean moveForward)
    {
        this.moveForward = moveForward;
    }

    public void setMoveBackward(boolean moveBackward)
    {
        this.moveBackward= moveBackward;
    }

    public void setTurnLeft(boolean turnLeft)
    {
        this.turnLeft = turnLeft;
    }
    
    public void setTurnRight(boolean turnRight)
    {
        this.turnRight = turnRight;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterIntX(), this.getCenterIntY());
        g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
        this.getTurret().draw(g2d, renderer);
	}

	@Override
    public void update() 
    {
        if(this.moveForward)
            this.moveForward();
        if(this.moveBackward)
            this.moveBackward();
        if(this.turnLeft)
            this.turnLeft();
        if(this.turnRight)
            this.turnRight();
        this.turret.setLocation(
            this.getCenterX() - (this.turret.getWidth() / 2),
            this.getCenterY() - (this.turret.getHeight() / 2)
        );
        turret.update();
	}
}