/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.game;

import com.watersfall.tankgame.data.TankData;
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
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Christopher
 */

//The main rendering class of the game
//The Constructor creates the JFrame and adds the objects that will be rendered on the screen
//The repaint method redraws the game every frame
//This class also controls the key bindings and resetting the game when one player wins
public class Frame extends JFrame implements ActionListener, KeyListener {
    
    //DELAY: how many ms of delay between each frame render
    //timer: the timer that redraws the game every DELAY ms
    //renderer: the class that calls the repaint method
    //tank1, tank2: the two player controlled tanks
    //move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right: the booleans that are set when the specified keybind is pushed
    //These control both tanks movement
    //turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight: the booleans that control the rotation of the turret
    //g2d: the Graphics2D object that represents the game window
    //tank1Image, tank2Image: the images that are loaded in for the corresponding tanks
    //shell1, shell2: the shells shot by the corresponding tanks
    //player1, player2: the selection index of player 1 and player 2 from the selection form, used to load in their choice of tank
    //tankData: the array that contains all the TankData objects for each of the tanks read from the tank file
    //shoot, hit, death, bounce: the sounds that are played
    //dim: the dimensions of the screen, used to set the JFrame to the correct size
    final int DELAY = 16; //Closest to 60fps I can get
    Timer timer; 
    Renderer renderer;
    Tank tank1, tank2;
    boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
    boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
    Graphics2D g2d;
    Image tank1Image, tank2Image; 
    Shell shell1, shell2;
    int player1, player2;
    ArrayList<TankData> tankData;
    Clip shoot, hit, death, bounce;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    final int SCREENWIDTH = dim.width;
    final int SCREENHEIGHT = dim.height;
    
    
    public Frame(int player1, int player2, ArrayList<TankData> tankData) throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        //This creates each of the sound clips
        shoot = AudioSystem.getClip();
        bounce = AudioSystem.getClip();
        death = AudioSystem.getClip();
        hit = AudioSystem.getClip();
        
        //This loads in the sound clips from the JAR resources
        //They need to be read by a BufferedInputStream so that they can be set back to their start
        //This enables them to be played multiple times, while only being read in once
        shoot.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/shoot.wav"))));
        bounce.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/bounce.wav"))));
        hit.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/death.wav"))));
        death.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/hit.wav"))));
        
        this.tankData = tankData;
        this.player1 = player1;
        this.player2 = player2;
        
        //Setting up the frame in the correct size
        setSize(SCREENWIDTH, SCREENHEIGHT);
        
        //Adding the renderer to the frame, which is what makes everything show up on screen
        renderer = new Renderer();
        add(renderer);
        
        //Adding the keylisteners from this class to the frame
        addKeyListener(this);
        
        timer = new Timer(DELAY, this);
        tank1 = new Tank(100, 100, 128, 256, 0.0, ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1 +".png")), ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1 +"TURRET.png")), tankData.get(player1));
        tank2 = new Tank(SCREENWIDTH - 100 - 256, SCREENHEIGHT - 100 - 128, 128, 256, 180.0, ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 + ".png")), ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 +"TURRET.png")), tankData.get(player2));
        
        //Finishing the JFrame
        //setUndecorated(true) and then pack() causes the game to be run in Borderless Window mode
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
        g2d.drawImage(tank1.getImage(), (int)tank1.getX(), (int)tank1.getY(), (int)tank1.width, (int)tank1.height, renderer);
        g2d.setTransform(old);
        
        old = g2d.getTransform();
        for(int i = 0; i < tank1.damage.size(); i++)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(tank1.getAngle() - tank1.damage.get(i).angle), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
            g2d.drawImage(tank1.damage.get(i).getImage(), (int)tank1.damage.get(i).getX(), (int)tank1.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }
        g2d.setTransform(old);

        //Tank 1 Turret
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank1.getTurret().getAngle()), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
        g2d.drawImage(tank1.getTurret().getImage(), (int)tank1.getTurret().getX(), (int)tank1.getTurret().getY(), renderer);
        g2d.setTransform(old);

        //Tank 2 
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getImage(), (int)tank2.getX(), (int)tank2.getY(), (int)tank2.width, (int)tank2.height, renderer);
        g2d.setTransform(old); 
        
        old = g2d.getTransform();
        for(int i = 0; i < tank2.damage.size(); i++)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(tank2.getAngle() - tank2.damage.get(i).angle), tank1.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
            g2d.drawImage(tank2.damage.get(i).getImage(), (int)tank2.damage.get(i).getX(), (int)tank2.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }
        g2d.setTransform(old);

        //Tank 2 Turret
        old = g2d.getTransform();
        g2d.rotate(Math.toRadians(tank2.getTurret().getAngle()), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
        g2d.drawImage(tank2.getTurret().getImage(), (int)tank2.getTurret().getX(), (int)tank2.getTurret().getY(), renderer);
        g2d.setTransform(old);

        //Tank 2 shell
        if(shell2 != null)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(shell2.getAngle()), shell2.getX() + shell2.width / 2, shell2.getY() + shell2.height / 2);
            g2d.setColor(Color.RED);
            g2d.fillRect((int)shell2.x, (int)shell2.y, (int)shell2.width, (int)shell2.height);
            g2d.setTransform(old);
            shell2.move();
            if(shell2.outOfBounds())
            {
                shell2 = null;
            }
        }

        //Tank 1 shell
        if(shell1 != null)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(shell1.getAngle()), shell1.getX() + shell1.getWidth() / 2, shell1.getY() + shell1.getHeight() / 2);
            g2d.setColor(Color.RED);
            g2d.fillRect((int)shell1.getX(), (int)shell1.getY(), (int)shell1.getWidth(), (int)shell1.getHeight());
            g2d.setTransform(old);
            shell1.move();
            if(shell1.outOfBounds())
            {
                shell1 = null;
            }
        }
        
        old = g2d.getTransform();
        for(int i = 0; i < tank1.damage.size(); i++)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(tank1.getAngle() - tank1.damage.get(i).getAngle()), tank1.getX() + tank1.getWidth() / 2, tank1.getY() + tank1.getHeight() / 2);
            g2d.drawImage(tank1.damage.get(i).getImage(), (int)tank1.damage.get(i).getX(), (int)tank1.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }
        g2d.setTransform(old);
        
        //Collision check for tank2
        if(shell1 != null && shell1.checkCollision(tank2))
        {
            if(shell1.checkPenetration(tank2))
            {
                death.setMicrosecondPosition(0);
                death.start();
                tank2.destroy();
            }
            else
            {
                bounce.setMicrosecondPosition(0);
                bounce.start();
                shell1.bounce(tank2);
            }
        }

        //Collision check for tank1
        if(shell2 != null && shell2.checkCollision(tank1))
        {
            if(shell2.checkPenetration(tank1))
            {
                death.setMicrosecondPosition(0);
                death.start();
                tank1.destroy();
            }
            else
            {
                bounce.setMicrosecondPosition(0);
                bounce.start();
                shell2.bounce(tank1);
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
            if(tank1.getTurret().canShoot)
            {
                shell1 = new Shell(tank1.getTurret());
                shoot.setMicrosecondPosition(0);
                shoot.start();
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
                shoot.setMicrosecondPosition(0);
                shell2 = new Shell(tank2.getTurret());
                shoot.start();
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
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int screen_Width = dim.width;
        final int screen_Height = dim.height;
        timer = new Timer(16, this);
        tank1 = new Tank(100, 100, 128, 256, 0.0, ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1 +".png")), ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1 +"TURRET.png")), tankData.get(player1));
        tank2 = new Tank(SCREENWIDTH - 100 - 256, SCREENHEIGHT - 100 - 128, 128, 256, 180.0, ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 + ".png")), ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 +"TURRET.png")), tankData.get(player2));
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