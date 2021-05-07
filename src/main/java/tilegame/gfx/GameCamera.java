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
    private static float xOffset, yOffset;
    private static final GameCamera gameCamera = new GameCamera();
    
    private GameCamera()
    {
        xOffset = 0;
        yOffset = 0;
    }

    public static final GameCamera getInstance(){
        return gameCamera;
    }
    
    public void clamCameraBounds()
    {
        if(xOffset < 0)
            xOffset = 0;
        else if(xOffset > World.getWidth() * Tile.TILEWIDTH - Game.getWidth())
            xOffset = World.getWidth() * Tile.TILEWIDTH - Game.getWidth();
        if(yOffset < 0)
            yOffset = 0;
        else if(yOffset > World.getHeight() * Tile.TILEHEIGHT - Game.getHeight())
            yOffset = World.getHeight() * Tile.TILEHEIGHT - Game.getHeight();
    }
    
    public void centerOnEntity(Entity e)
    {
        xOffset = e.getX() - Game.getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - Game.getHeight() / 2 + e.getHeight() / 2;
        clamCameraBounds();
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

