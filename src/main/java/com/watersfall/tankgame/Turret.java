/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame;

import java.awt.Point;

/**
 *
 * @author Christopher
 */
public class Turret {
    
    
    double angle;
    Point p;
    
    public Turret(Double angle, Point p)
    {
        this.angle = angle;
        this.p = p;
    }
}
