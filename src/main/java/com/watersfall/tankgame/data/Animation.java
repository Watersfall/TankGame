package com.watersfall.tankgame.data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.watersfall.tankgame.Objects.Sprite;
import com.watersfall.tankgame.game.Renderer;

public class Animation extends Sprite
{
    private BufferedImage[] frames;
    private boolean playing = false;
    private boolean done = true;
    private int index = 0;
    private boolean reversed = false;
    private int frameSkip;
    private int frameCount;

    public Animation(float x, float y, int width, int height, double angle, int countX, int countY, String name) throws IOException
    {
        super(x, y, width, height, angle, new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR));
        BufferedImage temp = ImageIO.read(this.getClass().getResourceAsStream("/Animation/" + name + ".png"));

        int frameWidth = temp.getWidth() / countX;
        int frameHeight = temp.getHeight() / countY;
        frames = new BufferedImage[temp.getWidth() * temp.getHeight()];

        int index = 0;
        for(int i = 0; i < countY; i++)
        {
            for(int o = 0; o < countX; o++)
            {
                frames[index] = temp.getSubimage(o * frameWidth, i * frameHeight, frameWidth, frameHeight);
                index++;
            }
        }
        super.setImage(frames[index]);
        playing = true;
        done = false;
        frameSkip = 1;
        frameCount = 0;
    }

    public Animation(float x, float y, int width, int height, double angle, int countX, int countY, String name, int frameSkip) throws IOException
    {
        super(x, y, width, height, angle, new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR));
        BufferedImage temp = ImageIO.read(this.getClass().getResourceAsStream("/Animation/" + name + ".png"));

        int frameWidth = temp.getWidth() / countX;
        int frameHeight = temp.getHeight() / countY;
        frames = new BufferedImage[temp.getWidth() * temp.getHeight()];

        int index = 0;
        for(int i = 0; i < countY; i++)
        {
            for(int o = 0; o < countX; o++)
            {
                frames[index] = temp.getSubimage(o * frameWidth, i * frameHeight, frameWidth, frameHeight);
                index++;
            }
        }
        super.setImage(frames[index]);
        playing = true;
        done = false;
        this.frameSkip = frameSkip;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
        g2d.rotate(Math.toDegrees(this.getAngle()), this.getCenterX(), this.getCenterY());
        g2d.drawImage(this.getImage(), this.getIntX(), this.getIntY(), this.getWidth(), this.getHeight(), renderer);
	}

	@Override
    public void update() 
    {
        frameCount++;
        if(playing && !reversed && frameCount == frameSkip)
        {
            this.index++;
            frameCount = 0;
        }
        if(playing && reversed && frameCount == frameSkip)
        {
            this.index--;
            frameCount = 0;
        }
        if(this.index > frames.length)
            done = true;
        if(this.index < 0)
            done = true;
        if(done)
            this.index = 0;
        super.setImage(frames[index]);
	}


}