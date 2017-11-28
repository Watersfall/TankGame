/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.data.MapData;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Particles;
import com.watersfall.tankgame.game.Renderer;
import com.watersfall.tankgame.game.Shell;
import com.watersfall.tankgame.game.Tank;
import com.watersfall.tankgame.ui.HealthBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
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
    
    //Setting up the screen bounds
    public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREENWIDTH = dim.width;
    public static final int SCREENHEIGHT = dim.height;
    public static final Rectangle2D TOP = new Rectangle2D.Float(0, 0, SCREENWIDTH, 1);
    public static final Rectangle2D LEFT = new Rectangle2D.Float(0, 0, 1, SCREENHEIGHT);
    public static final Rectangle2D RIGHT = new Rectangle2D.Float(SCREENWIDTH, 0, 1, SCREENHEIGHT);
    public static final Rectangle2D BOTTOM = new Rectangle2D.Float(0, SCREENHEIGHT, SCREENWIDTH, 1);
    public static final double SCALE_X = SCREENWIDTH / 1920.0;
    public static final double SCALE_Y = SCREENHEIGHT / 1080.0;
    public static final int FONTSIZE = (int)(56 * SCALE_X);

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
    final int DELAY = 16; //Closest to 60fps I can get
    public Timer timer; 
    public Renderer renderer;
    public Tank tank1, tank2;
    public boolean move1Forward, move2Forward, move1Back, move2Back, turn1Left, turn2Left, turn1Right, turn2Right;
    public boolean turret1RotateLeft, turret1RotateRight, turret2RotateLeft, turret2RotateRight;
    public Graphics2D g2d;
    public Image tank1Image, tank2Image; 
    public Shell shell1, shell2;
    public int player1, player2;
    public ArrayList<TankData> tankData;
    public Clip shoot, hit, death, bounce;
    public HealthBar player1Health, player2Health;
    public int player1Wins, player2Wins;
    private Sequencer battleMusic;
    private InputStream battleMusicInput;
    
    public MapData map;
    BufferedImage obstacle;
    
    public ArrayList<Particles> particles;
    
    public Frame(int player1, int player2, ArrayList<TankData> tankData, int mapSelection, ArrayList<MapData> map) throws IOException, LineUnavailableException, UnsupportedAudioFileException, MidiUnavailableException, InvalidMidiDataException
    {
        System.out.println(SCREENWIDTH + "\n" + SCREENHEIGHT);
        //This creates each of the sound clips
        shoot = AudioSystem.getClip();
        bounce = AudioSystem.getClip();
        death = AudioSystem.getClip();
        hit = AudioSystem.getClip();

        //Music
        battleMusic = MidiSystem.getSequencer();
        battleMusic.open();
        battleMusicInput = new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Music/BattleSong" + ((int)(Math.random() * 3) + 1) + ".mid"));
        battleMusic.setSequence(battleMusicInput);
        battleMusic.start();
        
        //This loads in the sound clips from the JAR resources
        //They need to be read by a BufferedInputStream so that they can be set back to their start
        //This enables them to be played multiple times
        shoot.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/shoot.wav"))));
        bounce.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/bounce.wav"))));
        hit.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/hit.wav"))));
        death.open(AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/Sounds/Gameplay/death.wav"))));
        
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
        tank2 = new Tank((int)((SCREENWIDTH - 100 - 256)), (int)((SCREENHEIGHT - 100 - 128)), 128, 256, 180.0, ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 + ".png")), ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2 +"TURRET.png")), tankData.get(player2));
        
        //Health bars
        player1Health = new HealthBar(tank1.health, 300 * this.SCALE_X, 50 * this.SCALE_Y);
        player2Health = new HealthBar(tank2.health, 1220 * this.SCALE_X, 50 * this.SCALE_Y);

        //Particles
        particles = new ArrayList<Particles>();

        //Finishing the JFrame
        //setUndecorated(true) and then pack() causes the game to be run in Borderless Window mode
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        pack();
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
        
        timer.start();
        
        this.map = map.get(mapSelection);

        player1Wins = 0;
        player2Wins = 0;
        Main.selectionFrame.setVisible(false);
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
        g2d.fillRect(0, 0, this.SCREENWIDTH, this.SCREENHEIGHT);

        if(move1Forward)
        {
            tank1.moveForward(tank2);
        }
        if(move1Back)
        {
            tank1.moveBack(tank2);
        }
        if(turn1Left)
        {
            tank1.turnLeft(tank2);
        }
        if(turn1Right)
        {
            tank1.turnRight(tank2);
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
            tank2.moveForward(tank1);
        }
        if(move2Back)
        {
            tank2.moveBack(tank1);
        }
        if(turn2Left)
        {
            tank2.turnLeft(tank1);
        }
        if(turn2Right)
        {
            tank2.turnRight(tank1);
        }
        if(turret2RotateLeft)
        {
            tank2.getTurret().turnLeft();
        }
        if(turret2RotateRight)
        {
            tank2.getTurret().turnRight();
        }
        

        
        AffineTransform old = g2d.getTransform();

        //Tank 1
        g2d.rotate(Math.toRadians(tank1.getAngle()), tank1.getCenterX(), tank1.getCenterY());
        g2d.drawImage(tank1.getImage(), (int)tank1.getX(), (int)tank1.getY(), (int)tank1.width, (int)tank1.height, renderer);
        g2d.setTransform(old);
        
        for(int i = 0; i < tank1.damage.size(); i++)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(tank1.getAngle() - tank1.damage.get(i).angle), tank1.getX() + tank1.width / 2, tank1.getY() + tank1.height / 2);
            g2d.drawImage(tank1.damage.get(i).getImage(), (int)tank1.damage.get(i).getX(), (int)tank1.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }

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
        
        for(int i = 0; i < tank2.damage.size(); i++)
        {
            old = g2d.getTransform();
            g2d.rotate(Math.toRadians(tank2.getAngle() - tank2.damage.get(i).angle), tank2.getX() + tank2.width / 2, tank2.getY() + tank2.height / 2);
            g2d.drawImage(tank2.damage.get(i).getImage(), (int)tank2.damage.get(i).getX(), (int)tank2.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }

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
            g2d.drawImage(tank1.damage.get(i).getImage(), tank1.damage.get(i).getX(), tank1.damage.get(i).getY(), renderer);
            g2d.setTransform(old);
        }
        g2d.setTransform(old);
        
        //Collision check for tank2
        if(shell1 != null && shell1.checkCollision(tank2))
        {
            shell1.move();
            tank2.addDamage((int)shell1.getCenterX(), (int)shell1.getCenterY(), tank2.angle, shell1.angle);
            shell1.moveBack();
            if(shell1.checkPenetration(tank2))
            { 
                tank2.health = tank2.health - tank1.getTurret().shellDamage;
                player2Health.setProgress(tank2.health);
                shell1 = null;
                if(tank2.health <= 0)
                {
                    death.setMicrosecondPosition(0);
                    death.start();
                    tank2.destroy();
                    player1Wins++;
                    battleMusic.stop();
                    tank1.playAnthem();
                }
                else
                {
                    hit.setMicrosecondPosition(0);
                    hit.start();
                }
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
            shell2.move();
            tank1.addDamage((int)shell2.getCenterX(), (int)shell2.getCenterY(), tank1.angle, shell2.angle);
            shell2.moveBack();
            if(shell2.checkPenetration(tank1))
            {
                tank1.health = tank1.health - tank2.getTurret().shellDamage;
                player1Health.setProgress(tank1.health);
                shell2 = null;
                if(tank1.health <= 0)
                {
                    death.setMicrosecondPosition(0);
                    death.start();
                    tank1.destroy();
                    player2Wins++;
                    battleMusic.stop();
                    tank2.playAnthem();

                }
                else
                {
                    hit.setMicrosecondPosition(0);
                    hit.start();
                }
            }
            else
            {
                bounce.setMicrosecondPosition(0);
                bounce.start();
                shell2.bounce(tank1);
            }
        }

        //Map Obstacles
        for(int i = 0; i < map.obstacles.length; i++)
        {
            if(map.obstacles[i] != null)
            {
                old = g2d.getTransform();
                g2d.rotate(Math.toRadians(map.obstacles[i].angle), map.obstacles[i].getCenterX(), map.obstacles[i].getCenterY());
                g2d.drawImage(map.obstacles[i].image, map.obstacles[i].x, map.obstacles[i].y, map.obstacles[i].width, map.obstacles[i].height, null);
                g2d.setTransform(old);
                g2d.setColor(Color.RED);
                g2d.drawLine((int)map.obstacles[i].TOP.getX1(), (int)map.obstacles[i].TOP.getY1(), (int)map.obstacles[i].TOP.getX2(), (int)map.obstacles[i].TOP.getY2());
                g2d.drawLine((int)map.obstacles[i].LEFT.getX1(), (int)map.obstacles[i].LEFT.getY1(), (int)map.obstacles[i].LEFT.getX2(), (int)map.obstacles[i].LEFT.getY2());
                g2d.drawLine((int)map.obstacles[i].RIGHT.getX1(), (int)map.obstacles[i].RIGHT.getY1(), (int)map.obstacles[i].RIGHT.getX2(), (int)map.obstacles[i].RIGHT.getY2());
                g2d.drawLine((int)map.obstacles[i].BOTTOM.getX1(), (int)map.obstacles[i].BOTTOM.getY1(), (int)map.obstacles[i].BOTTOM.getX2(), (int)map.obstacles[i].BOTTOM.getY2());
                
                if (shell1 != null && map.obstacles[i].checkShellCollision(shell1))
                {
                    map.obstacles[i] = null;
                    hit.setMicrosecondPosition(0);
                    hit.start();
                    shell1 = null;
                }
                if (shell2 != null && map.obstacles[i].checkShellCollision(shell2))
                {
                    map.obstacles[i] = null;
                    hit.setMicrosecondPosition(0);
                    hit.start();
                    shell2 = null;
                }
            }
        }

        for(int i = 0; i < particles.size(); i++)
        {
            particles.get(i).draw(g2d);
        }
        g2d.setColor(player1Health.backgroundColor);
        g2d.fillRect(player1Health.background.x, player1Health.background.y, player1Health.background.width, player1Health.background.height);
        g2d.fillRect(player2Health.background.x, player2Health.background.y, player2Health.background.width, player2Health.background.height);
        g2d.setColor(player1Health.foregroundColor);
        g2d.fillRect(player2Health.foreground.x, player2Health.foreground.y, player2Health.foreground.width, player2Health.foreground.height);
        g2d.fillRect(player1Health.foreground.x, player1Health.foreground.y, player1Health.foreground.width, player1Health.foreground.height);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, FONTSIZE)); 
        g2d.setColor(Color.BLACK);
        g2d.drawString(Integer.toString(player1Wins), (int)player1Health.x + (int)player1Health.width + (int)(25 * SCALE_X), (int)player1Health.y + (int)player1Health.height);
        g2d.drawString(Integer.toString(player2Wins), (int)player2Health.x - (int)(25 * SCALE_X) - (int)(SCALE_X * (Integer.toString(player2Wins).length() * FONTSIZE / 3)), (int)player2Health.y + (int)player2Health.height);
    }

    @Override
    public void keyTyped(KeyEvent e) 
    {
        if(e.getKeyChar() == '')
        {
            Main.optionsFrame.setVisible(true);
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
                particles.add(new Particles(
                    10, 
                    tank1.getTurret().getAngle(), 
                    15, 
                    shell1.x - (shell1.width / 2), 
                    shell1.y - (shell1.width / 2), 
                    25, 
                    2, 
                    0.2, 
                    -.02, 
                    0.0075, 
                    Color.GRAY.darker().darker().darker())
                );
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
                particles.add(new Particles(
                    10, 
                    tank2.getTurret().getAngle(), 
                    15, 
                    shell2.x - (shell2.width / 2), 
                    shell2.y - (shell2.width / 2), 
                    25, 
                    2, 
                    0.2, 
                    -.02, 
                    0.0075, 
                    Color.GRAY.darker().darker().darker())
                );
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
    
    public void reset() throws InterruptedException, IOException, MidiUnavailableException, InvalidMidiDataException
    {
        tank1.stopAnthem();
        tank2.stopAnthem();
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
        player1Health.setProgress(tank1.health);
        player2Health.setProgress(tank2.health);
        battleMusic.start();
    }
}