package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.Objects.Sprite;
import com.watersfall.tankgame.Objects.Tank;
import com.watersfall.tankgame.data.MapData;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

public class GameFrame extends JFrame implements KeyListener, ActionListener
{
    private Tank tank1, tank2;
    private int player1Wins, player2Wins;
    public Renderer renderer;
    private ArrayList<Sprite> drawables = new ArrayList<Sprite>();
    private final int DELAY = 16;
    private Timer timer;

    public GameFrame()
    {
        initFrame();
    }

    public GameFrame(int player1Selection, int player2Selection, ArrayList<TankData> tankArray, int mapSelection, ArrayList<MapData> mapArray) throws IOException 
    {
        renderer = new Renderer();
        this.tank1 = new Tank(
            0, 
            0, 
            256, 
            128, 
            0, 
            ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1Selection +".png")), 
            ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player1Selection +"TURRET.png")), 
            tankArray.get(player1Selection)
        );
        drawables.add(tank1);

        this.tank2 = new Tank(
            0, 
            0, 
            256, 
            128, 
            0, 
            ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2Selection +".png")), 
            ImageIO.read(getClass().getResourceAsStream("/Images/Tanks/TANK" + player2Selection +"TURRET.png")), 
            tankArray.get(player2Selection)
        );
        drawables.add(tank2);

        initFrame();
        this.timer = new Timer(this.DELAY, this);
        timer.start();
    }
    
    private void initFrame()
    {
        this.addKeyListener(this);
        this.setSize(Math.round((float)Constants.SCALE_X * 1920), Math.round((float)Constants.SCALE_Y * 1080));
        this.setAutoRequestFocus(true);
        this.requestFocus();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.pack();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.add(renderer);
        this.setVisible(true);
    }

    public void repaint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform old = g2d.getTransform();
        for(Sprite i : drawables)
        {
            i.draw(g2d, renderer);
            g2d.setTransform(old);
        }
    }

	@Override
    public void keyTyped(KeyEvent e) 
    {

	}

	@Override
    public void keyPressed(KeyEvent e) 
    {
        if(e.getKeyCode() == KeyEvent.VK_W)
            tank1.moveForward = true;
        if(e.getKeyCode() == KeyEvent.VK_A)
            tank1.turnLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_D)
            tank1.turnRight = true;
        if(e.getKeyCode() == KeyEvent.VK_S)
            tank1.moveBackward = true;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            tank2.moveForward = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            tank2.turnLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            tank2.turnRight = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            tank2.moveBackward = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Main.optionsFrame.setVisible(true);
	}

	@Override
    public void keyReleased(KeyEvent e) 
    {
		if(e.getKeyCode() == KeyEvent.VK_W)
            tank1.moveForward = false;
        if(e.getKeyCode() == KeyEvent.VK_A)
            tank1.turnLeft = false;
        if(e.getKeyCode() == KeyEvent.VK_D)
            tank1.turnRight = false;
        if(e.getKeyCode() == KeyEvent.VK_S)
            tank1.moveBackward = false;
        if(e.getKeyCode() == KeyEvent.VK_UP)
            tank2.moveForward = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            tank2.turnLeft = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            tank2.turnRight = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            tank2.moveBackward = false;
	}


    //The actual game loop
	@Override
    public void actionPerformed(ActionEvent e) 
    {
        this.renderer.repaint();
        for(Sprite i : drawables)
        {
            i.update();
        }
	}
}