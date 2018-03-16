package com.watersfall.tankgame.Objects;

import com.watersfall.tankgame.game.Renderer;
import java.awt.Graphics2D;
import java.awt.Image;

public class Obstacle extends Sprite
{
    public Obstacle()
    {
        super();
    }

    public Obstacle(float x, float y, int width, int height, double angle, Image image)
    {
        super(x, y, width, height, angle, image);
    }

	@Override
    public void draw(Graphics2D g2d, Renderer renderer) 
    {
		
	}

	@Override
    public void update() 
    {
		
	}
}