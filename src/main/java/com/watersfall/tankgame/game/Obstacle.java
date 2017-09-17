/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Christopher
 */

//Future Class that will be used to create obstacles in the game
//Trees, walls, etc.
public class Obstacle extends Rectangle {
    
    public int angle;
    public BufferedImage image;
    public int collisionX, collisionY, collisionWidth, collisionHeight;
    public Line2D TOP, BOTTOM, LEFT, RIGHT; 
    public Rectangle collisionRectangle;
    
    public Obstacle(String data) throws IOException
    {
        width = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        height = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        x = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        y = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        angle = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
        data = data.substring(data.indexOf(",") + 1);
        image = ImageIO.read(getClass().getResourceAsStream("/Images/MapObjects/" + data.trim() + ".png"));
        collisionWidth = width;
        collisionHeight = height;
        collisionX = x;
        collisionY = y;
        if(data.trim().equalsIgnoreCase("TREE"))
        {
            collisionWidth = width / 2;
            collisionHeight = height / 2;
            collisionX = x + width / 4;
            collisionY = y + height / 4;
        }

        Point2D TL = new Point2D.Double(collisionX, collisionY);
        Point2D TR = new Point2D.Double(collisionX + collisionWidth, collisionY);
        Point2D BL = new Point2D.Double(collisionX, collisionY + collisionHeight);
        Point2D BR = new Point2D.Double(collisionX + collisionWidth, collisionY + collisionHeight);

        double[] pt = {TL.getX(), TL.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY())
          .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
        TL = new Point2D.Double(pt[0], pt[1]);
        
        double[] pt1 = {TR.getX(), TR.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY())
          .transform(pt1, 0, pt1, 0, 1); // specifying to use this double[] to hold coords
        TR = new Point2D.Double(pt1[0], pt1[1]);

        double[] pt2 = {BL.getX(), BL.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY())
          .transform(pt2, 0, pt2, 0, 1); // specifying to use this double[] to hold coords
        BL = new Point2D.Double(pt2[0], pt2[1]);

        double[] pt3 = {BR.getX(), BR.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(this.angle), this.getCenterX(), this.getCenterY())
          .transform(pt3, 0, pt3, 0, 1); // specifying to use this double[] to hold coords
        BR = new Point2D.Double(pt3[0], pt3[1]);

        TOP = new Line2D.Double(TL, TR);
        LEFT = new Line2D.Double(TL, BL);
        RIGHT = new Line2D.Double(TR, BR);
        BOTTOM = new Line2D.Double(BL, BR);
        
    }

    public String checkTankCollision(Tank tank)
    {
        Line2D TOP, BOTTOM, LEFT, RIGHT; 
        Point2D TL = new Point2D.Double(tank.x, tank.y);
        Point2D TR = new Point2D.Double(tank.x + tank.width, tank.y);
        Point2D BL = new Point2D.Double(tank.x, tank.y + tank.height);
        Point2D BR = new Point2D.Double(tank.x + tank.width, tank.y + tank.height);

        double[] pt = {TL.getX(), TL.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(tank.angle), tank.getCenterX(), tank.getCenterY())
          .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
        TL = new Point2D.Double(pt[0], pt[1]);
        
        double[] pt1 = {TR.getX(), TR.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(tank.angle), tank.getCenterX(), tank.getCenterY())
          .transform(pt1, 0, pt1, 0, 1); // specifying to use this double[] to hold coords
        TR = new Point2D.Double(pt1[0], pt1[1]);

        double[] pt2 = {BL.getX(), BL.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(tank.angle), tank.getCenterX(), tank.getCenterY())
          .transform(pt2, 0, pt2, 0, 1); // specifying to use this double[] to hold coords
        BL = new Point2D.Double(pt2[0], pt2[1]);

        double[] pt3 = {BR.getX(), BR.getY()};
        AffineTransform.getRotateInstance(Math.toRadians(tank.angle), tank.getCenterX(), tank.getCenterY())
          .transform(pt3, 0, pt3, 0, 1); // specifying to use this double[] to hold coords
        BR = new Point2D.Double(pt3[0], pt3[1]);

        TOP = new Line2D.Double(TL, TR);
        LEFT = new Line2D.Double(TL, BL);
        RIGHT = new Line2D.Double(TR, BR);
        BOTTOM = new Line2D.Double(BL, BR);

        if(this.TOP.intersectsLine(TOP) || this.TOP.intersectsLine(LEFT) || this.TOP.intersectsLine(RIGHT) || this.TOP.intersectsLine(BOTTOM))
            return "TOP";
        if(this.LEFT.intersectsLine(TOP) || this.LEFT.intersectsLine(LEFT) || this.LEFT.intersectsLine(RIGHT) || this.LEFT.intersectsLine(BOTTOM))
            return "LEFT";
        if(this.RIGHT.intersectsLine(TOP) || this.RIGHT.intersectsLine(LEFT) || this.RIGHT.intersectsLine(RIGHT) || this.RIGHT.intersectsLine(BOTTOM))
            return "RIGHT";
        if(this.BOTTOM.intersectsLine(TOP) || this.BOTTOM.intersectsLine(LEFT) || this.BOTTOM.intersectsLine(RIGHT) || this.BOTTOM.intersectsLine(BOTTOM))
            return "BOTTOM";
        return null;
    }

    public boolean checkShellCollision(Shell shell)
    {
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(this.angle, this.getCenterX(), this.getCenterY());
        Shape shape = rotate.createTransformedShape(collisionRectangle);
        return shape.intersects(shell);
    }
}
