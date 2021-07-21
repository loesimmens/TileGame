/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities;

import tilegame.dialogue.Dialogue;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
import tilegame.logger.TileGameLogger;

import java.awt.*;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 *
 */
public abstract class Entity implements Serializable, Ticking, Rendering
{
    protected float xLocation;
    protected float yLocation;
    protected int width;
    protected int height;
    protected int health;
    protected boolean active = true;
    protected Rectangle bounds;
    protected EntityState entityState;
    protected long id;
    protected final String name;
    protected Dialogue dialogue;

    public static final int DEFAULT_HEALTH = 3;
    protected static final Logger LOGGER = TileGameLogger.getLogger();
    
    protected Entity(float xLocation, float yLocation, int width, int height, long id, String name)
    {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;
        
        bounds = new Rectangle(0, 0, width, height);
        
        entityState = EntityState.IDLE;
        
        this.id = id;
        this.name = name;
    }
    
    public abstract void die();
    
    public void hurt(int amt)
    {
        health -= amt;
        if(health <= 0)
        {
            active = false;
            die();
        }
    }
    
    public abstract void interact();

    public Rectangle getCollisionBounds(float xOffset, float yOffset)
    {
        return new Rectangle((int) (xLocation + bounds.x + xOffset), (int) (yLocation + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public float getxLocation()
    {
        return xLocation;
    }

    public void setxLocation(float xLocation)
    {
        this.xLocation = xLocation;
    }

    public float getyLocation()
    {
        return yLocation;
    }

    public void setyLocation(float yLocation)
    {
        this.yLocation = yLocation;
    }

    public int getWidth() 
    {
        return width;
    }

    public void setWidth(int width) 
    {
        this.width = width;
    }

    public int getHeight() 
    {
        return height;
    }

    public void setHeight(int height) 
    {
        this.height = height;
    }   

    public int getHealth() 
    {
        return health;
    }

    public long getId()
    {
        return id;
    }

    public void setHealth(int health) 
    {
        this.health = health;
    }

    public boolean isActive() 
    {
        return active;
    }

    public EntityState getState() {
        return entityState;
    }

    public void setState(EntityState entityState)
    {
        this.entityState = entityState;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
