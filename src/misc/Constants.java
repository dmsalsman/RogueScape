/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author bakpakboi
 */
public class Constants {
	public static final int DEFAULT_WIDTH = 80;
	public static final int DEFAULT_HEIGHT = 21;
    
	public static Color BLACK = new Color(0,0,0);
    public static Color BLUE = new Color(0,0,128);
    public static Color CYAN = new Color(0,128,128);
    public static Color GREEN = new Color(0,128,0);
    public static Color GREY_128 = new Color(128,128,128);
    public static Color MAGENTA = new Color(128, 0, 128);
    public static Color ORANGE = new Color(255, 165, 0);
    public static Color RED = new Color(128,0,0);
    public static Color WHITE = new Color(192, 192, 192);
    public static Color YELLOW = new Color(128,128,0);
    
    public static Color BRIGHT_BLUE = new Color(0,0,255);
    public static Color BRIGHT_CYAN = new Color(0,255,255);
    public static Color BRIGHT_GREEN = new Color(0,255,0);
    public static Color BRIGHT_MAGENTA = new Color(255, 0, 255);
    public static Color BRIGHT_RED = new Color(255,0,0);
    public static Color BRIGHT_WHITE = new Color(192, 192, 192);
    public static Color BRIGHT_YELLOW = new Color(255,255,0);
    
    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    private int widthInCharacters;
    private int heightInCharacters;
    private int charWidth = 9;
    private int charHeight = 16;
    private Color defaultBackgroundColor;
    private Color defaultForegroundColor;
    private int cursorX;
    private int cursorY;
    private BufferedImage glyphSprite;
    private BufferedImage[] glyphs;
    private char[][] chars;
    private Color[][] backgroundColors;
    private Color[][] foregroundColors;
    private char[][] oldChars;
    private Color[][] oldBackgroundColors;
    private Color[][] oldForegroundColors;
    
    public void setCursorX(int cursorX) {
        if (cursorX < 0 || cursorX >= widthInCharacters)
        {
            System.err.println("IllegalArgumentException: \"cursorX \" + cursorX + \" must be within range [0,\"" + widthInCharacters + "\").\"");
        }
        this.cursorX = cursorX;
    }
}
