/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import data.TankData;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */
public class Frame extends JFrame implements ActionListener, KeyListener {
    
    public static boolean gameOver = false;
    final int delay = 16; //Closest to 60fps I can get
    Timer timer; 
    Renderer renderer;
    Tank tank1, tank2;
    boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
    boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
    Graphics2D g2d;
    Image tank1Image, tank2Image; 
    Shell shell1, shell2;
    int player1, player2;
    ArrayList<TankData> tanks;
    
    public Frame(int player1, int player2, ArrayList<TankData> tanks) throws IOException
    {
        this.tanks = tanks;
        this.player1 = player1;
        this.player2 = player2;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int screen_Width = dim.width;
        final int screen_Height = dim.height;
        setSize(screen_Width , screen_Height);
        renderer = new Renderer();
        add(renderer);
        addKeyListener(this);
        
        timer = new Timer(16, this);
        tank1 = new Tank(100, 100, 128, 256, 0.0, ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player1 +".png")), ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player1 +"TURRET.png")), tanks.get(player1));
        tank2 = new Tank(screen_Width - 100 - 256, screen_Height - 100 - 128, 128, 256, 180.0, ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player2 + ".png")), ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player2 +"TURRET.png")), tanks.get(player2));
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
        
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        renderer.repaint();
    }
    
    public void repaint(Graphics g) throws InterruptedException, IOException
    {
        g2d = (Graphics2D)g;
        g2d.setColor(Color.GREEN.darker());
        g2d.fillRect(0, 0, 1920, 1080);

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
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank1.getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        g2d.drawImage(tank1.getImage(), (int)tank1.getX(), (int)tank1.getY(), tank1.width, tank1.height, renderer);
        g2d.setTransform(old);

        //Tank 1 Turret
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank1.getTurret().getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        g2d.drawImage(tank1.getTurret().getImage(), tank1.getTurret().x, tank1.getTurret().y, renderer);
        g2d.setTransform(old);

        //Tank 1 shell
        if(shell1 != null)
        {
            g2d.setColor(Color.RED);
            g2d.fillRect(shell1.x, shell1.y, 10, 10);
            shell1.move();
            if(shell1.outOfBounds())
            {
                shell1 = null;
            }
        }

        //Tank 2 
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getImage(), (int)tank2.getX(), (int)tank2.getY(), tank2.width, tank2.height, renderer);
        g2d.setTransform(old);     

        //Tank 2 Turret
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getTurret().getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getTurret().getImage(), tank2.getTurret().x, tank2.getTurret().y, renderer);
        g2d.setTransform(old);

        //Tank 2 shell
        if(shell2 != null)
        {
            g2d.setColor(Color.RED);
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
            if(shell1.checkPenetration(tank2))
            {
                g2d.setColor(Color.pink);
                g2d.drawRect((int)tank2.getX(), (int)tank2.getY(), 100, 100);
                tank2.destroy();
            }
            else
            {
                shell1.bounce(tank2.getAngle());
            }
        }

        if(shell2 != null && shell2.checkCollision(tank1))
        {
            if(shell2.checkPenetration(tank1))
            {
                g2d.setColor(Color.pink);
                g2d.drawRect((int)tank1.getX(), (int)tank1.getY(), 100, 100);
                tank1.destroy();
            }
            else
            {
                shell2.bounce(tank1.getAngle());
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        if(e.getKeyChar() == '')
        {
            System.exit(0);
        }
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
            //if(tank1.getTurret().canShoot)
            {
                shell1 = new Shell(tank1.getTurret());
                tank1.getTurret().shoot();
            }
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
            if(tank2.getTurret().canShoot)
            {
                shell2 = new Shell(tank2.getTurret());
                tank2.getTurret().shoot();
            }
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
    
    public void reset() throws InterruptedException, IOException
    {
        /*boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
        boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
        Graphics2D g2d;
        Image tank1Image, tank2Image; 
        Shell shell1, shell2;*/
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int screen_Width = dim.width;
        final int screen_Height = dim.height;
        timer = new Timer(16, this);
        tank1 = new Tank(100, 100, 128, 256, 0.0, ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player1 +".png")), ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player1 +"TURRET.png")), tanks.get(player1));
        tank2 = new Tank(screen_Width - 100 - 256, screen_Height - 100 - 128, 128, 256, 180.0, ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player2 + ".png")), ImageIO.read(getClass().getResourceAsStream("/Images/TANK" + player2 +"TURRET.png")), tanks.get(player2));
        move1Forward = false;
        move2Forward = false;
        move1Back = false;
        move2Back = false;
        turn1Left = false;
        turn1Right = false;
        turn2Left = false;
        turn2Right = false;
        shell1 = null;
        shell2 = null;
    }
}