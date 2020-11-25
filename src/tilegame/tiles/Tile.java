/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Tile 
{
    public static final int TILEWIDTH = 64, TILEHEIGHT = 64;
        
    protected BufferedImage texture;
    protected final int id;
    protected boolean solid;
    
    public Tile(BufferedImage texture, int id, boolean solid)
    {
        this.texture = texture;
        this.id = id;
        this.solid = solid;
    }
    
    //for animated tiles?
    public void tick()
    {
        
    }
    
    public void render(Graphics g, int x, int y)
    {
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }
    
    public boolean isSolid()
    {
        return solid;
    }
    
    public int getId()
    {
        return id;
    }
}
