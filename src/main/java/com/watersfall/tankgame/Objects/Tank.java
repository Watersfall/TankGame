package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Tank extends Sprite
{
    public static int DIRECTION_FORWARD = 0;
    public static int DIRECTION_LEFT = 1;
    public static int DIRECTION_RIGHT = 2;
    public static int DIRECTION_BACKWARD = 3;
    private double frontArmor, sideArmor, rearArmor, speed, tankRotation, health;
    private String name, nation;
    private Turret turret;
    private ArrayList<DamageEffect> damage;
    public boolean moveForward, moveBackward, turnLeft, turnRight;
    
    public Tank()
    {
        super();
    }

    public Tank(float x, float y, int width, int height, double angle, Image image, Image turretImage, TankData data)
    {
        super(x, y, width, height, angle, image);
        this.name = data.tankData[0];
        this.frontArmor = java.lang.Double.parseDouble(data.tankData[1]);
        this.sideArmor = java.lang.Double.parseDouble(data.tankData[2]);
        this.rearArmor = java.lang.Double.parseDouble(data.tankData[3]);
        this.speed = java.lang.Double.parseDouble(data.tankData[6]) / 5.0;
        this.tankRotation = java.lang.Double.parseDouble(data.tankData[8]);
        this.health = java.lang.Double.parseDouble(data.tankData[10]);
        this.nation = data.tankData[12];
        this.turret = new Turret(x, y, turretImage.getWidth(null), turretImage.getHeight(null), angle, turretImage, data);
        this.damage = new ArrayList<DamageEffect>();
    }

    public void moveForward()
    {
        if(!this.CheckCollision())
        {
            this.setX(this.getX() + (float)(Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            this.setY(this.getY() + (float)(Math.sin(Math.toRadians(this.getAngle())) * this.speed));

            if(this.intersects(Constants.TOP))
            {
                this.setY(Math.round(this.getY() - Math.sin(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() - Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                this.setY(Math.round(this.getY() - Math.sin(Math.toRadians(this.getAngle())) * this.speed));
            }
        }
        else
        {
            this.setX(this.getX() + (float)(Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            this.setY(this.getY() + (float)(Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));

            if(this.intersects(Constants.TOP))
            {
                this.setY(Math.round(this.getY() - Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() - Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                this.setY(Math.round(this.getY() - Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
        }
    }

    public void moveBackward()
    {
        if(!this.CheckCollision())
        {
            this.setX(this.getX() - (float)(Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            this.setY(this.getY() - (float)(Math.sin(Math.toRadians(this.getAngle())) * this.speed));

            if(this.intersects(Constants.TOP))
            {
                this.setY(Math.round(this.getY() + Math.sin(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() + Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + Math.cos(Math.toRadians(this.getAngle())) * this.speed));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                this.setY(Math.round(this.getY() + Math.sin(Math.toRadians(this.getAngle())) * this.speed));
            }
        }
        else
        {
            this.setX(this.getX() - (float)(Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            this.setY(this.getY() - (float)(Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));

            if(this.intersects(Constants.TOP))
            {
                this.setY(Math.round(this.getY() + Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() + Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + Math.cos(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                this.setY(Math.round(this.getY() + Math.sin(Math.toRadians(this.getAngle())) * (this.speed / 5)));
            }
        }
    }

    public void turnRight()
    {
        if(!this.CheckCollision())
        {
            this.setAngle(this.getAngle() + this.tankRotation);
            this.turret.turnRight(this.tankRotation);

            if(this.intersects(Constants.TOP))
            {
                //this.setX(Math.round(this.getX() + (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() - (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * this.speed)));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - (Math.sin(Math.toRadians(this.getAngle()% 180 + 180)) * this.speed)));
                //this.setY(Math.round(this.getY() + (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + (Math.sin(Math.toRadians(this.getAngle()% 180 + 180)) * this.speed)));
                //this.setY(Math.round(this.getY() - (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                //this.setX(Math.round(this.getX() - (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() + (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * this.speed)));
            }
        }
        else
        {
            this.setAngle(this.getAngle() + this.tankRotation / 5);
            this.turret.turnRight(this.tankRotation / 5);

            if(this.intersects(Constants.TOP))
            {
                //this.setX(Math.round(this.getX() + (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() - (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * (this.speed / 5))));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - (Math.sin(Math.toRadians(this.getAngle() % 180 + 180)) * (this.speed / 5))));
                //this.setY(Math.round(this.getY() + (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + (Math.sin(Math.toRadians(this.getAngle() % 180 + 180)) * (this.speed / 5))));
                //this.setY(Math.round(this.getY() - (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                //this.setX(Math.round(this.getX() - (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() + (Math.cos(Math.toRadians(this.getAngle() % 90 + 90)) * (this.speed / 5))));
            }
        }
        if(this.getAngle() < 0)
            this.setAngle(this.getAngle() + 360);
        else if (this.getAngle() <= 360)
            this.setAngle(this.getAngle() % 360);
    }

    public void turnLeft()
    {
        if(!this.CheckCollision())
        {
            this.setAngle(this.getAngle() - this.tankRotation);
            this.turret.turnLeft(this.tankRotation);

            if(this.intersects(Constants.TOP))
            {
                //this.setX(Math.round(this.getX() + (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() - (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * this.speed)));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - (Math.sin(Math.toRadians(this.getAngle()% 180 + 180)) * this.speed)));
                //this.setY(Math.round(this.getY() + (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + (Math.sin(Math.toRadians(this.getAngle()% 180 + 180)) * this.speed)));
                //this.setY(Math.round(this.getY() - (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                //this.setX(Math.round(this.getX() - (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() + (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * this.speed)));
            }
        }
        else
        {
            this.setAngle(this.getAngle() - this.tankRotation / 5);
            this.turret.turnLeft(this.tankRotation / 5);

            if(this.intersects(Constants.TOP))
            {
                //this.setX(Math.round(this.getX() + (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() - (Math.cos(Math.toRadians(this.getAngle()% 90 + 90)) * (this.speed / 5))));
            }
            if(this.intersects(Constants.LEFT))
            {
                this.setX(Math.round(this.getX() - (Math.sin(Math.toRadians(this.getAngle() % 180 + 180)) * (this.speed / 5))));
                //this.setY(Math.round(this.getY() + (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.RIGHT))
            {
                this.setX(Math.round(this.getX() + (Math.sin(Math.toRadians(this.getAngle() % 180 + 180)) * (this.speed / 5))));
                //this.setY(Math.round(this.getY() - (Math.sin(Math.toRadians(this.getAngle())) * this.speed) * Math.sin(Math.toRadians(this.getAngle()))));
            }
            if(this.intersects(Constants.BOTTOM))
            {
                //this.setX(Math.round(this.getX() - (Math.cos(Math.toRadians(this.getAngle())) * this.speed) * Math.cos(Math.toRadians(this.getAngle()))));
                this.setY(Math.round(this.getY() + (Math.cos(Math.toRadians(this.getAngle() % 90 + 90)) * (this.speed / 5))));
            }
        }
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

    public boolean damage(double damage)
    {
        Main.sound.playSound("hit");
        return (this.health -= damage) <= 0;
    }

    public Turret getTurret()
    {
        return this.turret;
    }

    public double getHealth()
    {
        return this.health;
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
        double angle = (Math.toDegrees(Math.atan2(shell.getCenterY() - this.getCenterY(), shell.getCenterX() - this.getCenterX())) - (this.getAngle() /*% 180*/))% 180;
        System.out.println(angle);
        if(angle < 26.565 && angle > -26.565)
            if (Math.abs(this.frontArmor / Math.cos(Math.toRadians(shell.getAngle() - this.getAngle()))) < Math.abs(shell.getPenetration()))
                return true;
            else
                shell.bounce(this, FRONT);
        else if((angle > 26.565 && angle < 153.435) || (angle < -26.565 && angle > -153.435))
            if (Math.abs(this.sideArmor / Math.sin(Math.toRadians(shell.getAngle() - this.getAngle()))) < Math.abs(shell.getPenetration()))
                return true;
            else
                shell.bounce(this, LEFT);
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

    public boolean CheckCollision()
    {
        for(int i = 0; i < Main.selectionFrame.frame.getObstacles().length; i++)
            if(this.intersects(Main.selectionFrame.frame.getObstacles()[i]))
                return true;
        return false;
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