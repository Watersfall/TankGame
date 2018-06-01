package com.watersfall.tankgame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;

public class Constants
{
    private static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREENWIDTH = dim.width;
    public static final int SCREENHEIGHT = dim.height;
    public static final Rectangle2D TOP = new Rectangle2D.Float(0, 0, SCREENWIDTH, 1);
    public static final Rectangle2D LEFT = new Rectangle2D.Float(0, 0, 1, SCREENHEIGHT);
    public static final Rectangle2D RIGHT = new Rectangle2D.Float(SCREENWIDTH, 0, 1, SCREENHEIGHT);
    public static final Rectangle2D BOTTOM = new Rectangle2D.Float(0, SCREENHEIGHT, SCREENWIDTH, 1);
    public static final double SCALE_X = SCREENWIDTH / 1920.0;
    public static final double SCALE_Y = SCREENHEIGHT / 1080.0;
    public static final int FONTSIZE = (int)(56 * SCALE_X);
    public static final int HP_BAR_POS_X_1 = Math.round(Math.round(450 * SCALE_X));
    public static final int HP_BAR_POS_Y_1 = Math.round(Math.round(25 * SCALE_Y));
    public static final int HP_BAR_POS_X_2 = Math.round(Math.round(SCREENWIDTH - (450 * SCALE_X) - (400 * SCALE_X)));
    public static final int HP_BAR_POS_Y_2 = Math.round(Math.round(25 * SCALE_Y));
    public static final int HP_BAR_WIDTH = Math.round(Math.round(400 * SCALE_X));
    public static final int HP_BAR_HEIGHT = Math.round(Math.round(40 * SCALE_Y));
    public static final Font FONT = new Font("Monospace", 0, FONTSIZE);
    public static final int WINS_POSITION_1 = HP_BAR_POS_X_1 + HP_BAR_WIDTH;
    public static final int WINS_POSITION_2 = HP_BAR_POS_X_2;
    public static final int TANK_WIDTH = Math.round(Math.round(256 * SCALE_X));
    public static final int TANK_HEIGHT = Math.round(Math.round(128 * SCALE_Y));
    public static final int TANK_1_X = 0;
    public static final int TANK_2_X = SCREENWIDTH - TANK_WIDTH;
    public static final int TANK_1_Y = 0;
    public static final int TANK_2_Y = SCREENHEIGHT - TANK_HEIGHT;
}