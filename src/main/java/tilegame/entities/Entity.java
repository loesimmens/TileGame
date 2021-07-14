/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities;

import tilegame.dialogue.Dialogue;

import java.awt.*;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 *
 */
public abstract class Entity 
{
    public static final int DEFAULT_HEALTH = 3;
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected int health;
    protected boolean active = true;
    protected Rectangle bounds;
    
    protected State state;
    
    protected long id;
    private String name;
    protected Dialogue dialogue;

    public Entity() {}
    
    public Entity(float x, float y, int width, int height, long id, String name)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;
        
        bounds = new Rectangle(0, 0, width, height);
        
        state = State.IDLE;
        
        this.id = id;
        this.name = name;
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
    
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
    
    public boolean checkEntityCollisions(float xOffset, float yOffset)
    {
        for(Entity e : EntityManager.getEntities())
        {
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }
        
    public Rectangle getCollisionBounds(float xOffset, float yOffset)
    {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public float getX() 
    {
        return x;
    }

    public void setX(float x) 
    {
        this.x = x;
    }

    public float getY() 
    {
        return y;
    }

    public void setY(float y) 
    {
        this.y = y;
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

    public State getState() {
        return state;
    }

    public void setState(State state)
    {
        this.state = state;
    }
}
