/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.data;

import com.watersfall.tankgame.game.Obstacle;
import java.awt.Color;
import java.io.IOException;

/**
 *
 * @author Christopher
 */
public class MapData {
    
    public Color background;
    public Obstacle[] obstacles;
    public String name;
    
    public MapData(String data) throws IOException
    {
        name = data.substring(0, data.indexOf(":"));
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
