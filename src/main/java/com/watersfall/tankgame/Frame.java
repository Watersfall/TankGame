/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */
public class Frame extends JFrame implements ActionListener, KeyListener {
    
    final int delay = 16; //Closest to 60fps I can get
    Timer timer; 
    Renderer renderer;
    Tank tank1, tank2;
    boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
    boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
    Graphics2D g2d;
    Image tank1Image, tank2Image; 
    Shell shell1, shell2;
    
    public Frame() throws IOException
    {
        renderer = new Renderer();
        add(renderer);
        addKeyListener(this);
        
        timer = new Timer(16, this);
        tank1 = new Tank(100, 100, 175, 100, ImageIO.read(new File("C:\\Users\\Christopher\\Desktop\\TANK1.png")));
        tank2 = new Tank(1000, 100, 175, 100, ImageIO.read(new File("C:\\Users\\Christopher\\Desktop\\TANK1.png")));
        
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("timer");
        renderer.repaint();
    }
    
    public void repaint(Graphics g)
    {
        g2d = (Graphics2D)g;
        Point p = tank1.getPoint();
        
        
        if(move1Forward)
        {
            tank1.moveForward();
        }
        if(move1Back)
        {
            tank1.moveBack();
        }
        if(turn1Left)
        {
            tank1.turnLeft();
        }
        if(turn1Right)
        {
            tank1.turnRight();
        }
        if(turret1RotateLeft)
        {
            tank1.getTurret().turnLeft();
        }
        if(turret1RotateRight)
        {
            tank1.getTurret().turnRight();
        }
        if(move2Forward)
        {
            tank2.moveForward();
        }
        if(move2Back)
        {
            tank2.moveBack();
        }
        if(turn2Left)
        {
            tank2.turnLeft();
        }
        if(turn2Right)
        {
            tank2.turnRight();
        }
        if(turret2RotateLeft)
        {
            tank2.getTurret().turnLeft();
        }
        if(turret2RotateRight)
        {
            tank2.getTurret().turnRight();
        }
        
        //Tank 1
        //g2d.setColor(Color.red);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank1.getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        Shape transformed = transform.createTransformedShape(new Rectangle((int)tank1.getX(), (int)tank1.getY(), tank1.width, tank1.height));
        g2d.fill(transformed);
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank1.getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        g2d.drawImage(tank1.getImage(), (int)tank1.getX(), (int)tank1.getY(), tank1.width, tank1.height, renderer);
        g2d.setTransform(old);
        
        
        
        //Tank 1 Turret
        g2d.setColor(Color.blue);
        transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank1.getTurret().getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        transformed = transform.createTransformedShape(new Rectangle((int)tank1.getTurret().getX(), (int)tank1.getTurret().getY(), tank1.width / 2, tank1.height / 2));
        g2d.fill(transformed);
        
        
        //Tank 1 shell
        if(shell1 != null)
        {
            g2d.setColor(Color.yellow);
            g2d.fillRect(shell1.x, shell1.y, 10, 10);
            shell1.move();
            if(shell1.outOfBounds())
            {
                shell1 = null;
            }
        }
        
        //Tank 2 
        g2d.setColor(Color.green);
        transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank2.getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        transform.translate(WIDTH, WIDTH);
        transformed = transform.createTransformedShape(new Rectangle((int)tank2.getX(), (int)tank2.getY(), tank2.width, tank2.height));
        g2d.fill(transformed);
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getImage(), (int)tank2.getX(), (int)tank2.getY(), tank2.width, tank2.height, renderer);
        g2d.setTransform(old);
        
        
        //Tank 2 Turret
        /*g2d.setColor(Color.orange);
        transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank2.getTurret().getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        transformed = transform.createTransformedShape(new Rectangle((int)tank2.getTurret().getX(), (int)tank2.getTurret().getY(), tank2.width / 2, tank2.height / 2));
        g2d.fill(transformed);*/
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getTurret().getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getTurret().getImage(), (int)tank2.getTurret().getX(), (int)tank2.getTurret().getY(), tank2.width / 2, tank2.height / 2, renderer);
        g2d.setTransform(old);
        
        //Tank 2 shell
        if(shell2 != null)
        {
            g2d.setColor(Color.yellow);
            g2d.fillRect(shell2.x, shell2.y, 10, 10);
            shell2.move();
            if(shell2.outOfBounds())
            {
                shell2 = null;
            }
        }
        
        //
        if(shell1 != null && shell1.checkCollision(tank2))
        {
            g2d.setColor(Color.pink);
            g2d.drawRect((int)tank2.getX(), (int)tank2.getY(), 100, 100);
        }
        
        if(shell2 != null && shell2.checkCollision(tank1))
        {
            g2d.setColor(Color.pink);
            g2d.drawRect((int)tank1.getX(), (int)tank1.getY(), 100, 100);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyChar() == 'w')
        {
            move1Forward = true;
        }
        if(e.getKeyChar() == 'a')
        {
            turn1Left = true;
        }
        if(e.getKeyChar() == 's')
        {
            move1Back = true;
        }
        if(e.getKeyChar() == 'd')
        {
            turn1Right = true;
        }
        if(e.getKeyChar() == 'o')
        {
            turret1RotateLeft = true;
        }
        if(e.getKeyChar() == 'p')
        {
            turret1RotateRight = true;
        }
        if(e.getKeyCode() == 32)
        {
            shell1 = new Shell(tank1.getTurret());
        }
        if(e.getKeyCode() == 38)
        {
            move2Forward = true;
        }
        if(e.getKeyCode() == 37)
        {
            turn2Left = true;
        }
        if(e.getKeyCode() == 40)
        {
            move2Back = true;
        }
        if(e.getKeyCode() == 39)
        {
            turn2Right = true;
        }
        if(e.getKeyCode() == 98)
        {
            turret2RotateLeft = true;
        }
        if(e.getKeyCode() == 99)
        {
            turret2RotateRight = true;
        }
        if(e.getKeyCode() == 97)
        {
            shell2 = new Shell(tank2.getTurret());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if(e.getKeyChar() == 'w')
        {
            move1Forward = false;
        }
        if(e.getKeyChar() == 'a')
        {
            turn1Left = false;
        }
        if(e.getKeyChar() == 's')
        {
            move1Back = false;
        }
        if(e.getKeyChar() == 'd')
        {
            turn1Right = false;
        }
        if(e.getKeyChar() == 'o')
        {
            turret1RotateLeft = false;
        }
        if(e.getKeyChar() == 'p')
        {
            turret1RotateRight = false;
        }
        if(e.getKeyCode() == 38)
        {
            move2Forward = false;
        }
        if(e.getKeyCode() == 37)
        {
            turn2Left = false;
        }
        if(e.getKeyCode() == 40)
        {
            move2Back = false;
        }
        if(e.getKeyCode() == 39)
        {
            turn2Right = false;
        }
        if(e.getKeyCode() == 98)
        {
            turret2RotateLeft = false;
        }
        if(e.getKeyCode() == 99)
        {
            turret2RotateRight = false;
        }
    }
}