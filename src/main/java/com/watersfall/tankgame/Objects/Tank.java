package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;

public class Tank extends Sprite
{
    public static final int FRONT = 0;
    public static final int SIDE = 1;
    public static final int BACK = 2;

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
        if(this.getAngle() < 0)
            this.setAngle(this.getAngle() + 360);
        else if (this.getAngle() <= 360)
            this.setAngle(this.getAngle() % 360);
    }

    public void turnLeft()
    {
        this.setAngle(this.getAngle() - this.tankRotation);
        if(this.getAngle() < 0)
            this.setAngle(this.getAngle() + 360);
        else if (this.getAngle() <= 360)
            this.setAngle(this.getAngle() % 360);
    }

    public void destroy()
    {
        Main.sound.playSound("death");
        this.setX(-10000);
        this.setY(-10000);
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

    public boolean checkPenetration(Shell shell)
    {
        double angle = (Math.toDegrees(Math.atan2(shell.getCenterY() - this.getCenterY(), shell.getCenterX() - this.getCenterX())) - (this.getAngle() % 180)) % 180;
        System.out.println(angle);
        //System.out.println(Math.abs(this.frontArmor / Math.cos(Math.toRadians(shell.getAngle() - this.getAngle()))));
        if(angle < 26.565 && angle > -26.565)
            if (Math.abs(this.frontArmor / Math.cos(Math.toRadians(shell.getAngle() - this.getAngle()))) < Math.abs(shell.getPenetration()))
                return true;
            else
                shell.bounce(this, FRONT);
        else if((angle > 26.565 && angle < 153.435) || (angle < -26.565 && angle > -153.435))
            if (Math.abs(this.sideArmor / Math.sin(Math.toRadians(shell.getAngle() - this.getAngle()))) < Math.abs(shell.getPenetration()))
                return true;
            else
                shell.bounce(this, SIDE);
        else
            if (Math.abs(this.rearArmor / Math.cos(Math.toRadians(shell.getAngle() - this.getAngle()))) < Math.abs(shell.getPenetration()))
                return true;
            else
                shell.bounce(this, BACK);
        return false;
    }

    public String getNation()
    {
        return nation;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		g2d.rotate(Math.toRadians(this.getAngle()), this.getCenterIntX(), this.getCenterIntY());
        g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
        g2d.setTransform(Main.selectionFrame.frame.old);
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
        {
            this.turnLeft();
            this.turret.turnLeft(this.tankRotation);
        }
        if(this.turnRight)
        {
            this.turnRight();
            this.turret.turnRight(this.tankRotation);
        }
        this.turret.setLocation(
            this.getCenterX() - (this.turret.getWidth() / 2),
            this.getCenterY() - (this.turret.getHeight() / 2)
        );
        turret.update();
	}
}