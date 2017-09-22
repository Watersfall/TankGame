package com.watersfall.tankgame.ui;

import com.watersfall.tankgame.game.Frame;
import java.awt.Color;
import java.awt.Rectangle;

public class HealthBar
{

    public Rectangle background, foreground;
    public Color backgroundColor, foregroundColor;
    public double x, y;
    public int width, height;
    public double maxValue;

    public HealthBar(double maxValue, double x, double y)
    {
        this.x = x;
        this.y = y;
        this.width = (int)(400 * Frame.SCALE_X);
        this.height = (int)(40 * Frame.SCALE_Y);
        this.maxValue = maxValue;
        this.backgroundColor = new Color(0, 0, 0);
        this.foregroundColor = new Color(255, 0, 0);
        this.background = new Rectangle((int)x, (int)y, width, height);
        this.foreground = new Rectangle((int)x, (int)y, width, height);
    }

    public void setProgress(double newValue)
    {
        if((newValue * (width / maxValue)) >= 0)
            foreground.width = (int)(newValue * (width / maxValue));
        else
            foreground.width = 0;
    }
}