/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import java.awt.image.BufferedImage;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class SpriteSheet 
{        
    private BufferedImage sheet;
    
    public SpriteSheet(BufferedImage sheet)
    {
        this.sheet = sheet;
    }
    
    public BufferedImage crop(int x, int y, int width, int height)
    {
        return sheet.getSubimage(x, y, width, height);
    }
}
