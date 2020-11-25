/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import tilegame.Game;
import tilegame.entities.Entity;
import tilegame.tiles.Tile;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class GameCamera 
{
    private float xOffset, yOffset;
    private Game game;
    
    public GameCamera(Game game, float xOffset, float yOffset)
    {
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    
    public void clamCameraBounds()
    {
        if(xOffset < 0)
            xOffset = 0;
        else if(xOffset > game.getGameState().getWorld().getWidth() * Tile.TILEWIDTH - game.getWidth())
            xOffset = game.getGameState().getWorld().getWidth() * Tile.TILEWIDTH - game.getWidth();
        if(yOffset < 0)
            yOffset = 0;
        else if(yOffset > game.getGameState().getWorld().getHeight() * Tile.TILEHEIGHT - game.getHeight())
            yOffset = game.getGameState().getWorld().getHeight() * Tile.TILEHEIGHT - game.getHeight();
    }
    
    public void centerOnEntity(Entity e)
    {
        xOffset = e.getX() - game.getGameState().getGame().getWidth() / 2 + e.getWidth() / 2;
        yOffset = e.getY() - game.getGameState().getGame().getHeight() / 2 + e.getHeight() / 2;
        clamCameraBounds();
    }
    
    public void move(float xAmt, float yAmt)
    {
        xOffset += xAmt;
        yOffset += yAmt;
        clamCameraBounds();
    }

    public float getxOffset() 
    {
        return xOffset;
    }

    public void setxOffset(float xOffset) 
    {
        this.xOffset = xOffset;
    }

    public float getyOffset() 
    {
        return yOffset;
    }

    public void setyOffset(float yOffset) 
    {
        this.yOffset = yOffset;
    }
}

