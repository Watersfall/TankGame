package com.watersfall.tankgame.ui;
import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Objects.Sprite;
import com.watersfall.tankgame.game.Renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class HealthBar extends Sprite
{

    public Rectangle background, foreground;
    public Color backgroundColor, foregroundColor;
    public double x, y;
    public int width, height, newWidth;
    public double maxValue;
    private boolean reverse;
    private int wins, winsX;

    public HealthBar(double maxValue, double x, double y, boolean reverse, int wins, int winsX)
    {
        this.x = x;
        this.y = y;
        this.width = Constants.HP_BAR_WIDTH;
        this.height = Constants.HP_BAR_HEIGHT;
        this.maxValue = maxValue;
        this.backgroundColor = new Color(0, 0, 0);
        this.foregroundColor = new Color(255, 0, 0);
        this.background = new Rectangle((int)x, (int)y, width, height);
        this.foreground = new Rectangle((int)x, (int)y, width, height);
        this.newWidth = this.width;
        this.reverse = reverse;
        this.wins = wins;
        this.winsX = winsX;
    }

    public void setProgress(double newValue)
    {
        if((newValue * (this.width / this.maxValue)) >= 0)
            this.newWidth = (int)(newValue * (this.width / this.maxValue));
        else
            this.newWidth = 0;
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
        g2d.setColor(this.backgroundColor);
        g2d.fillRect(this.background.x, this.background.y, this.background.width, this.background.height);
        g2d.setColor(this.foregroundColor);
        g2d.fillRect(this.foreground.x, this.foreground.y, this.foreground.width, this.foreground.height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(Constants.FONT);
        if(!reverse)
            g2d.drawString(Integer.toString(wins), this.winsX - g2d.getFontMetrics().stringWidth(Integer.toString(wins).trim()), 
            Constants.HP_BAR_POS_Y_1 + Constants.HP_BAR_HEIGHT);
        else
            g2d.drawString(Integer.toString(wins), this.winsX, 
            Constants.HP_BAR_POS_Y_1 + Constants.HP_BAR_HEIGHT);
	}

	@Override
    public void update() 
    {
        if(foreground.width != this.newWidth)
            foreground.width -= 10;
        if(reverse && foreground.width != this.newWidth)
            foreground.x += 10;
        if(reverse && foreground.width < this.newWidth)
            foreground.x += foreground.width - newWidth;
        if(foreground.width < this.newWidth)
            foreground.width = this.newWidth;
	}
}