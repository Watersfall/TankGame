package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Main;
import com.watersfall.tankgame.Objects.Particles;
import com.watersfall.tankgame.Objects.Shell;
import com.watersfall.tankgame.Objects.Sprite;
import com.watersfall.tankgame.Objects.Tank;
import com.watersfall.tankgame.data.MapData;
import com.watersfall.tankgame.data.TankData;
import com.watersfall.tankgame.game.Renderer;
import java.awt.Color;
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
    private int player1Wins, player2Wins, player1Selection, player2Selection, mapSelection;
    public Renderer renderer;
    private ArrayList<Sprite> drawables = new ArrayList<Sprite>();
    private final int DELAY = 16;
    private final int RESET_DELAY = 7500;
    private Timer timer, resetTimer;
    private Shell shell1, shell2;
    public AffineTransform old;
    private ArrayList<TankData> tankArray;
    private ArrayList<MapData> mapArray;
    private Thread destroy1, destroy2;

    public GameFrame()
    {
        initFrame();
    }

    public GameFrame(int player1Selection, int player2Selection, ArrayList<TankData> tankArray, int mapSelection, ArrayList<MapData> mapArray) throws IOException 
    {
        destroy1 = new Thread()
        {   public void run()
            {
                tank1.destroy();
                resetTimer.start();
                Main.sound.stopMusic();
                Main.sound.playMusic(tank2.getNation());
            }
        };

        destroy2 = new Thread()
        {   public void run()
            {
                tank2.destroy();
                resetTimer.start();
                Main.sound.stopMusic();
                Main.sound.playMusic(tank1.getNation());
            }
        };
        destroy2.setPriority(Thread.MAX_PRIORITY);
        destroy1.setPriority(Thread.MAX_PRIORITY);
        this.player1Selection = player1Selection;
        this.player2Selection = player2Selection;
        this.tankArray = tankArray;
        this.mapArray = mapArray;
        this.mapSelection = mapSelection;
        timer = new Timer(DELAY, this);
        resetTimer = new Timer(RESET_DELAY, this);
        resetTimer.setRepeats(false);
        timer.setActionCommand("redraw");
        resetTimer.setActionCommand("reset");
        timer.start();
        Main.sound.playRandomMusic();
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

        for(int i = 0; i < mapArray.get(mapSelection).obstacles.length; i++)
        {
            drawables.add(mapArray.get(mapSelection).obstacles[i]);
        }
        initFrame();
    }
    
    private void initFrame()
    {
        this.renderer = new Renderer();
        this.add(renderer);
        this.addKeyListener(this);
        this.setSize(Math.round((float)Constants.SCALE_X * 1920), Math.round((float)Constants.SCALE_Y * 1080));
        this.setAutoRequestFocus(true);
        this.requestFocus();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.pack();
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(true);
        Main.selectionFrame.setVisible(false);
    }

    public void repaint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(this.mapArray.get(mapSelection).color.darker());
        g2d.fillRect(0, 0, Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
        
        this.old = g2d.getTransform();
        for(Sprite i : drawables)
        {
            if(i != null)
            {
                i.update();
                i.draw(g2d, renderer);
                g2d.setTransform(old);
            }
        }

        if(shell2 != null && tank1.intersects(shell2))
        {
            if(tank1.checkPenetration(shell2))
            {
                destroy1.start();
            }
            shell2 = null;
        }
        if(shell1 != null && tank2.intersects(shell1))
        {
            if(tank2.checkPenetration(shell1))
            {
                destroy2.start();
            }
            shell1 = null;
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
        else if(e.getKeyCode() == KeyEvent.VK_A)
            tank1.turnLeft = true;
        else if(e.getKeyCode() == KeyEvent.VK_D)
            tank1.turnRight = true;
        else if(e.getKeyCode() == KeyEvent.VK_S)
            tank1.moveBackward = true;
        else if(e.getKeyCode() == KeyEvent.VK_P)
            tank1.getTurret().turnRight = true;
        else if(e.getKeyCode() == KeyEvent.VK_O)
            tank1.getTurret().turnLeft = true;
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            tank2.moveForward = true;
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            tank2.turnLeft = true;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            tank2.turnRight = true;
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
            tank2.moveBackward = true;
        else if(e.getKeyCode() == KeyEvent.VK_NUMPAD2)
            tank2.getTurret().turnRight = true;
        else if(e.getKeyCode() == KeyEvent.VK_NUMPAD1)
            tank2.getTurret().turnLeft = true;
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Main.optionsFrame.setVisible(true);
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            if(tank2.getTurret().shoot())
            {
                shell2 = new Shell(
                    (float)(tank2.getTurret().getCenterX() - (15 / 2) + ((tank2.getTurret().getWidth() / 2) * Math.cos(Math.toRadians(tank2.getTurret().getAngle())))), 
                    (float)(tank2.getTurret().getCenterY() - (7 / 2) + ((tank2.getTurret().getWidth() / 2) * Math.sin(Math.toRadians(tank2.getTurret().getAngle())))), 
                    15, 
                    7, 
                    tank2.getTurret().getAngle(), 
                    Color.ORANGE, 
                    tank2.getTurret().getShellData());
                drawables.add(shell2);
                drawables.add(new Particles(
                    25, 
                    tank2.getTurret().getAngle(), 
                    15, 
                    shell2.getX() - (shell2.getWidth() / 2), 
                    shell2.getY() - (shell2.getWidth() / 2), 
                    25, 
                    2, 
                    0.2, 
                    -.02, 
                    0.0075, 
                    Color.GRAY.darker().darker().darker())
                );
            }
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
            if(tank1.getTurret().shoot())
            {
                shell1 = new Shell(
                    (float)(tank1.getTurret().getCenterX() - (15 / 2) + ((tank1.getTurret().getWidth() / 2) * Math.cos(Math.toRadians(tank1.getTurret().getAngle())))), 
                    (float)(tank1.getTurret().getCenterY() - (7 / 2) + ((tank1.getTurret().getWidth() / 2) * Math.sin(Math.toRadians(tank1.getTurret().getAngle())))), 
                    15, 
                    7, 
                    tank1.getTurret().getAngle(), 
                    Color.ORANGE, 
                    tank1.getTurret().getShellData());
                drawables.add(shell1);
                drawables.add(new Particles(
                    25, 
                    tank1.getTurret().getAngle(), 
                    15, 
                    shell1.getX() - (shell1.getWidth() / 2), 
                    shell1.getY() - (shell1.getWidth() / 2), 
                    25, 
                    2, 
                    0.2, 
                    -.02, 
                    0.0075, 
                    Color.GRAY.darker().darker().darker())
                );
            }
	}

	@Override
    public void keyReleased(KeyEvent e) 
    {
		if(e.getKeyCode() == KeyEvent.VK_W)
            tank1.moveForward = false;
        else if(e.getKeyCode() == KeyEvent.VK_A)
            tank1.turnLeft = false;
        else if(e.getKeyCode() == KeyEvent.VK_D)
            tank1.turnRight = false;
        else if(e.getKeyCode() == KeyEvent.VK_S)
            tank1.moveBackward = false;
        else if(e.getKeyCode() == KeyEvent.VK_P)
            tank1.getTurret().turnRight = false;
        else if(e.getKeyCode() == KeyEvent.VK_O)
            tank1.getTurret().turnLeft = false;
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            tank2.moveForward = false;
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            tank2.turnLeft = false;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            tank2.turnRight = false;
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
            tank2.moveBackward = false;
        else if(e.getKeyCode() == KeyEvent.VK_NUMPAD2)
            tank2.getTurret().turnRight = false;
        else if(e.getKeyCode() == KeyEvent.VK_NUMPAD1)
            tank2.getTurret().turnLeft = false;
	}

    private void reset() throws IOException
    {
        destroy1 = new Thread()
        {   public void run()
            {
                tank1.destroy();
                resetTimer.start();
                Main.sound.stopMusic();
                Main.sound.playMusic(tank2.getNation());
            }
        };

        destroy2 = new Thread()
        {   public void run()
            {
                tank2.destroy();
                resetTimer.start();
                Main.sound.stopMusic();
                Main.sound.playMusic(tank1.getNation());
            }
        };
        drawables.clear();
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
        for(int i = 0; i < mapArray.get(mapSelection).obstacles.length; i++)
        {
            drawables.add(mapArray.get(mapSelection).obstacles[i]);
        }
        Main.sound.stopMusic();
        Main.sound.playRandomMusic();
    }

	@Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getActionCommand().equalsIgnoreCase("redraw"))
            this.renderer.repaint();
        else if (e.getActionCommand().equalsIgnoreCase("reset"))
            try 
            {
				this.reset();
            } 
            catch (IOException e1) 
            {
				e1.printStackTrace();
			}
	}
}