package com.watersfall.tankgame;

import java.awt.Dimension;
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
}