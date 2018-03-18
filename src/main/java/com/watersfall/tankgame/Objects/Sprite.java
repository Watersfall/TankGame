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
    private float x, y;
    private int width, height;
    private double angle;
    private Image image;
    private AffineTransform transform;
    private Color color;

    public Sprite()
    {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.angle = 0;
        this.image = null;
    }

    public Sprite(float x, float y, int width, int height, double angle, Image image)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.image = image;
    }

    public Sprite(float x, float y, int width, int height, double angle, Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.color = color;
    }

    public abstract void draw(Graphics2D g2d, Renderer renderer);

    public boolean intersects(Sprite sprite)
    {
        Rectangle a = new Rectangle(this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight());
        Rectangle b = new Rectangle(sprite.getIntX(), sprite.getIntY(), sprite.getWidth(), sprite.getHeight());
        AffineTransform af = new AffineTransform();
        AffineTransform bf = new AffineTransform();
        af.rotate(Math.toRadians(this.getAngle()), a.getCenterX(), a.getCenterY());
        bf.rotate(Math.toRadians(sprite.getAngle()), b.getCenterX(), b.getCenterY());
        Shape ra = af.createTransformedShape(a);
        Shape rb = bf.createTransformedShape(b);
        return (ra.intersects(rb.getBounds2D()) && rb.intersects(ra.getBounds2D()));
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
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
        this.x = x;
        this.y = y;
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

    public AffineTransform getAffineTransform()
    {
        return this.transform;
    }

    public void setAffineTransform(Graphics2D g2d)
    {
        this.transform = g2d.getTransform();
    }

	public abstract void update();
}