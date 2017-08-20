/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */
public class Frame extends JFrame implements ActionListener, KeyListener {
    
    Timer timer;
    Renderer renderer;
    Tank tank1;
    Tank tank2;
    boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
    boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
    Graphics2D g2d;
    
    public Frame()
    {
        renderer = new Renderer();
        add(renderer);
        addKeyListener(this);
        
        timer = new Timer(20, this);
        tank1 = new Tank(100, 100);
        tank2 = new Tank(1000, 100);
        
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
        
        //First Tank
        g2d.setColor(Color.red);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank1.getAngle()), tank1.getX() + 50, tank1.getY() + 50);
        Shape transformed = transform.createTransformedShape(new Rectangle(tank1.getX(), tank1.getY(), 100, 100));
        g2d.fill(transformed);
        
        //First Turret
        g2d.setColor(Color.blue);
        transform = new AffineTransform();
        transform.rotate(Math.toRadians(tank1.getTurret().getAngle()), tank1.getX() + 50, tank1.getY() + 50);
        transformed = transform.createTransformedShape(new Rectangle(tank1.getTurret().getX(), tank1.getTurret().getY(), 50, 50));
        g2d.fill(transformed);
        
        //g2d.setColor(Color.green);
        //g2d.fillRect(tank2.getX(), tank2.getY(), 100, 100);
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
    }
}
