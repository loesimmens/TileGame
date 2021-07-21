/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import tilegame.Game;
import tilegame.entities.Entity;
import tilegame.tiles.Tile;
import tilegame.worlds.World;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class GameCamera 
{
    private static float xOffset = 0;
    private static float yOffset = 0;
    
    private GameCamera() {}
    
    private static void clamCameraBoundsWithinWorldBorders()
    {
        if(xOffset < 0)
            xOffset = 0;
        else if(xOffset > World.getWidthInNTiles() * Tile.TILEWIDTH - Game.getDisplayWidth())
            xOffset = World.getWidthInNTiles() * Tile.TILEWIDTH - Game.getDisplayWidth();
        if(yOffset < 0)
            yOffset = 0;
        else if(yOffset > World.getHeightInNTiles() * Tile.TILEHEIGHT - Game.getDisplayHeight())
            yOffset = World.getHeightInNTiles() * Tile.TILEHEIGHT - Game.getDisplayHeight();
    }
    
    public static void centerOnEntity(Entity e)
    {
        xOffset = e.getxLocation() + e.getWidth() / 2 - Game.getDisplayWidth() / 2;
        yOffset = e.getyLocation() + e.getHeight() / 2 - Game.getDisplayHeight() / 2;
        clamCameraBoundsWithinWorldBorders();
    }

    public static float getxOffset()
    {
        return xOffset;
    }
    public static float getyOffset()
    {
        return yOffset;
    }
}

