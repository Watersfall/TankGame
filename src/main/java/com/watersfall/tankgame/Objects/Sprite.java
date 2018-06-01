package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.game.Renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public abstract class Sprite
{
    public static final int FRONT = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BACK = 3;

    private float x, y;
    private int width, height;
    private int collisionWidth, collisionHeight;
    private float collisionX, collisionY;
    private double angle;
    private Image image;
    private AffineTransform transform;
    private Color color;
    private String type;

    public Sprite()
    {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.angle = 0;
        this.image = null;
        this.collisionX = x;
        this.collisionY = y;
        this.collisionWidth = width;
        this.collisionHeight = height;
    }

    public Sprite(float x, float y, int width, int height, double angle, Image image)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.image = image;
        this.collisionX = this.x;
        this.collisionY = this.y;
        this.collisionWidth = this.width;
        this.collisionHeight = this.height;
    }

    public Sprite(float x, float y, int width, int height, float collisionX, float collisionY , int collisionWidth, int collisionHeight, double angle, Image image)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.image = image;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
    }

    public Sprite(float x, float y, int width, int height, double angle, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.color = color;
        this.collisionX = x;
        this.collisionY = y;
        this.collisionWidth = width;
        this.collisionHeight = height;
    }

    public Sprite(float x, float y, int width, int height, float collisionX, float collisionY , int collisionWidth, int collisionHeight, double angle, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.color = color;
        this.collisionX = collisionX;
        this.collisionY = collisionY;
        this.collisionWidth = collisionWidth;
        this.collisionHeight = collisionHeight;
    }

    public boolean intersects(Sprite sprite)
    {
        Rectangle a = new Rectangle(this.getIntCollisionX(), this.getIntCollisionY(), this.getCollisionWidth(), this.getCollisionHeight());
        Rectangle b = new Rectangle(sprite.getIntCollisionX(), sprite.getIntCollisionY(), sprite.getCollisionWidth(), sprite.getCollisionHeight());
        AffineTransform af = new AffineTransform();
        AffineTransform bf = new AffineTransform();
        af.rotate(Math.toRadians(this.getAngle()), a.getCenterX(), a.getCenterY());
        bf.rotate(Math.toRadians(sprite.getAngle()), b.getCenterX(), b.getCenterY());
        Shape ra = af.createTransformedShape(a);
        Shape rb = bf.createTransformedShape(b);
        Area aa = new Area(ra);
        Area ab = new Area(rb);
        if(ra.intersects(rb.getBounds2D()) && rb.intersects(ra.getBounds2D()))
        {
            aa.intersect(ab);
            boolean c = (!aa.isEmpty());
            return c;
        }
        return false;
    }

    public boolean intersects(Shape shape)
    {
        Rectangle a = new Rectangle(this.getIntCollisionX(), this.getIntCollisionY(), this.getCollisionWidth(), this.getCollisionHeight());
        AffineTransform af = new AffineTransform();
        af.rotate(Math.toRadians(this.getAngle()), a.getCenterX(), a.getCenterY());
        Shape ra = af.createTransformedShape(a);
        Area aa = new Area(ra);
        Area ab = new Area(shape);
        if(ra.intersects(shape.getBounds2D()) && shape.intersects(ra.getBounds2D()))
        {
            aa.intersect(ab);
            boolean c = (!aa.isEmpty());
            return c;
        }
        return false;
    }

    public int getCollisionSide(Sprite sprite)
    {
        double proportions = Math.toDegrees(Math.atan2(sprite.height, sprite.width));
        double angle = (Math.toDegrees(Math.atan2(sprite.y - this.y, sprite.x - this.x)) - (sprite.angle % 180)) % 180;
        if(angle < proportions && angle < -1 * proportions)
            return FRONT;
        else if((angle > proportions && angle < (180 - proportions)))
            return LEFT;
        else if (angle < -1 * proportions && angle > (180 - -1 * proportions))
            return RIGHT;
        else
            return BACK;
    }

    public void setX(float x)
    {
        float difference = x - this.x;
        this.x = x;
        this.collisionX = this.collisionX + difference;
    }

    public void setY(float y)
    {
        float difference = y - this.y;
        this.y = y;
        this.collisionY = this.collisionY + difference;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
        if (this.angle >= 360)
            this.angle = angle % 360;
        if (this.angle < 0)
            this.angle += 360;
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void setSize(Dimension dimension)
    {
        this.width = dimension.width;
        this.height = dimension.height;
    }

    public void setSize(Rectangle rectangle)
    {
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    public void setLocation(float x, float y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public float getX()
    {
        return x;
    }

    public int getIntX()
    {
        return Math.round(x);
    }

    public float getY()
    {
        return y;
    }

    public int getIntY()
    {
        return Math.round(y);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public double getAngle()
    {
        return angle;
    }

    public Image getImage()
    {
        return image;
    }

    public Color getColor()
    {
        return color;
    }

    public float getCenterX()
    {
        return this.x + this.width / 2;
    }

    public int getCenterIntX()
    {
        return Math.round(this.x + this.width / 2);
    }

    public float getCenterY()
    {
        return this.y + this.height / 2;
    }

    public int getCenterIntY()
    {
        return Math.round(this.y + this.height / 2);
    }

    public float getCollisionX()
    {
        return this.collisionX;
    }

    public float getCollisionY()
    {
        return this.collisionY;
    }

    public int getIntCollisionX()
    {
        return Math.round(this.collisionX);
    }

    public int getIntCollisionY()
    {
        return Math.round(this.collisionY);
    }

    public int getCollisionWidth()
    {
        return this.collisionWidth;
    }

    public int getCollisionHeight()
    {
        return this.collisionHeight;
    }

    public AffineTransform getAffineTransform()
    {
        return this.transform;
    }

    public float getCenterCollisionX()
    {
        return this.collisionX + this.collisionWidth / 2;
    }

    public int getCenterIntCollisionX()
    {
        return Math.round(this.collisionX + this.collisionWidth / 2);
    }

    public float getCenterCollisionY()
    {
        return this.collisionY + this.collisionHeight / 2;
    }

    public int getCenterIntCollisionY()
    {
        return Math.round(this.collisionY + this.collisionHeight / 2);
    }

    public void setAffineTransform(Graphics2D g2d)
    {
        this.transform = g2d.getTransform();
    }

    public String getType()
    {
        return this.type;
    }

    public abstract void draw(Graphics2D g2d, Renderer renderer);

	public abstract void update();
}