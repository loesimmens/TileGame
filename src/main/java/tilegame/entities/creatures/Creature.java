/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.creatures;

import tilegame.Game;
import tilegame.entities.Direction;
import tilegame.entities.Entity;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.tiles.Tile;
import tilegame.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public abstract class Creature extends Entity
{
    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 64,
                            DEFAULT_CREATURE_HEIGHT = 64;
    
    protected float speed;
    protected float xMove, yMove;
    
    protected Direction facing;
    
    //Animations
    protected Animation animDown, animUp, animLeft, animRight;
    
    public Creature(float x, float y, int width, int height, int id, String name)
    {
        super(x, y, width, height, id, name);
        
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        
        bounds.x = 25;
        bounds.y = 26;
        bounds.width = 12;
        bounds.height = 37;
        
        facing = Direction.DOWN;
        
        //Animations
        animDown = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("down"));
        animUp = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("up"));
        animLeft = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("left"));
        animRight = new Animation(400, Assets.getAssets().creatureAnimMap.get(id).get("right"));
    }
    
    @Override
    public void tick() 
    {
        //Animations
        animDown.tick();
        animUp.tick();
        animLeft.tick();
        animRight.tick();
        
        //Move
        move();
    }
    
    @Override
    public void render(Graphics g)
    {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - Game.getGameState().getGame().getGameCamera().getxOffset()),
                (int) (y - Game.getGameState().getGame().getGameCamera().getyOffset()), width, height, null);
    }
    
    public void move()
    {
        if(!checkEntityCollisions(xMove, 0f))
            moveX();
        if(!checkEntityCollisions(0f, yMove))
            moveY();
    }
    
    public void moveX()
    {
        if(xMove > 0) //moving right
        {
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            
            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
            {
                x += xMove;
            }
            else
            {
                x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
            }
        }
        else if(xMove < 0) //moving left
        {
            int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
            
            if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT))
            {
                x += xMove;
            }
            else
            {
                x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
            }
        }
        
    }
    
    public void moveY()
    {
        if(yMove < 0)//up
        {
            int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
            
            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
            {
                y += yMove;
            }
            else
            {
                y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
            }
        }
        else if(yMove > 0)//down
        {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
            
            if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
                    !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty))
            {
                y += yMove;
            }
            else
            {
                y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
            }
        }
    }
    
    private BufferedImage getCurrentAnimationFrame()
    {
        switch(facing)
        {
            case LEFT:
                return animLeft.getCurrentFrame(); 
            case RIGHT:
                return animRight.getCurrentFrame();
            case UP:
                return animUp.getCurrentFrame(); 
            case DOWN:
                return animDown.getCurrentFrame();
            default: 
                return animDown.getCurrentFrame();
        }
    }
    
    protected boolean collisionWithTile(int x, int y)
    {
        return World.getTile(x, y).isSolid();
    }
    
    //GETTERS SETTERS

    public float getxMove() 
    {
        return xMove;
    }

    public void setxMove(float xMove) 
    {
        this.xMove = xMove;
    }

    public float getyMove() 
    {
        return yMove;
    }

    public void setyMove(float yMove) 
    {
        this.yMove = yMove;
    }

    public float getSpeed() 
    {
        return speed;
    }

    public void setSpeed(float speed) 
    {
        this.speed = speed;
    }
}
