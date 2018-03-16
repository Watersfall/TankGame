
package com.watersfall.tankgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import com.watersfall.tankgame.Objects.Particle;

public class Particles
{
    private Particle[] particles;
    private double angle;
    private double angleVariance;

    public Particles(int count, double angle, double angleVariance, double x, double y, int size, double velocity, double velocityVariance, double acceleration, double accelerationVariance, Color color)
    {
        this.angle = angle;
        this.angleVariance = angleVariance;
        particles = new Particle[count];

        for(int i = 0; i < count; i++)
        {
            particles[i] = new Particle(
                x, 
                y, 
                angle + ((Math.random() * angleVariance * 2) - angleVariance), 
                size, 
                velocity + ((Math.random() * velocityVariance * 2) - velocityVariance), 
                acceleration + ((Math.random() * accelerationVariance * 2) - accelerationVariance), 
                color);
        }
    }

    public void update()
    {
        for(int i = 0; i < particles.length; i++)
        {
            particles[i].move();
        }
    }

    public void draw(Graphics2D g2d, Renderer renderer)
    {
        for(int i = 0; i < particles.length; i++)
        {
            particles[i].draw(g2d, renderer);
        }
        update();
    }
}