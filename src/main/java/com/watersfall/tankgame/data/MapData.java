/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.data;

import com.watersfall.tankgame.game.Obstacle;
import java.awt.Color;

/**
 *
 * @author Christopher
 */
public class MapData {
    
    public Color background;
    public Obstacle[] obstacles;
    
    public MapData(String data)
    {
        System.out.println(data);
        data = data.substring(data.indexOf(":") + 1);
        int itemCount = Integer.parseInt(data.substring(0, data.indexOf(":")).trim());
        data = data.substring(data.indexOf(":") + 1);
        obstacles = new Obstacle[itemCount];
        
        for(int i = 0; i < itemCount; i++)
        {
            obstacles[i] = new Obstacle(data.substring(0, data.indexOf(":")).trim());
            data = data.substring(data.indexOf(":") + 1);
        }
    }
}
