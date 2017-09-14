package com.watersfall.tankgame.ui;

import java.awt.Color;
import java.awt.Rectangle;

public class HealthBar
{

    public Rectangle background, foreground;
    public Color backgroundColor, foregroundColor;
    public int x, y, width, height;
    public double maxValue;

    public HealthBar(double maxValue, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = 400;
        this.height = 40;
        this.maxValue = maxValue;
        this.backgroundColor = new Color(0, 0, 0);
        this.foregroundColor = new Color(255, 0, 0);
        this.background = new Rectangle(x, y, width, height);
        this.foreground = new Rectangle(x, y, width, height);
    }

    public void setProgress(double newValue)
    {
        if((newValue * (width / maxValue)) >= 0)
            foreground.width = (int)(newValue * (width / maxValue));
        else
            foreground.width = 0;
    }
}