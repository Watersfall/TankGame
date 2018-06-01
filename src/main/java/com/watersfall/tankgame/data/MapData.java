/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watersfall.tankgame.data;

import com.watersfall.tankgame.Constants;
import com.watersfall.tankgame.Objects.Obstacle;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Christopher
 */
public class MapData {
    
    public Color background;
    public Obstacle[] obstacles;
    public String name;
    public Color color;
    private String type;
    
    public MapData(String data) throws IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
    {
        name = data.substring(0, data.indexOf(":"));
        data = data.substring(data.indexOf(":") + 1);
        int itemCount = Integer.parseInt(data.substring(0, data.indexOf(":")).trim());
        data = data.substring(data.indexOf(":") + 1);
        color = (Color)Color.class.getField(data.substring(0, data.indexOf(":")).trim()).get(null);
        data = data.substring(data.indexOf(":") + 1);
        obstacles = new Obstacle[itemCount];
        
        int width, height, x, y, angle;
        Image image;
        
        for(int i = 0; i < itemCount; i++)
        {
            width = (int)(Integer.parseInt(data.substring(0, data.indexOf(",")).trim()) * Constants.SCALE_X);
            data = data.substring(data.indexOf(",") + 1);
            height = (int)(Integer.parseInt(data.substring(0, data.indexOf(",")).trim()) * Constants.SCALE_Y);
            data = data.substring(data.indexOf(",") + 1);
            x = (int)(Integer.parseInt(data.substring(0, data.indexOf(",")).trim()) * Constants.SCALE_X);
            data = data.substring(data.indexOf(",") + 1);
            y = (int)(Integer.parseInt(data.substring(0, data.indexOf(",")).trim()) * Constants.SCALE_Y);
            data = data.substring(data.indexOf(",") + 1);
            angle = Integer.parseInt(data.substring(0, data.indexOf(",")).trim());
            data = data.substring(data.indexOf(",") + 1);
            image = ImageIO.read(getClass().getResourceAsStream("/Images/MapObjects/" + data.substring(0, data.indexOf(":")).trim() + ".png"));
            type = data.substring(0, data.indexOf(":")).trim();
            data = data.substring(data.indexOf(":") + 1);
            
            if(type.equalsIgnoreCase("tree"))
                obstacles[i] = new Obstacle(x, y, width, height, width / 3, height / 3, angle, image);
            else
                obstacles[i] = new Obstacle(x, y, width, height, angle, image);
        }
    }
}
