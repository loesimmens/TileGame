/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.ui;

import tilegame.game_elements.Rendering;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public abstract class UIObject implements Rendering
{
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected Rectangle bounds;
    protected boolean hovering = false;
    
    protected UIObject(float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }
    
    public abstract void onClick();
    
    public void onMouseMove(MouseEvent e)
    {
        hovering = bounds.contains(e.getX(), e.getY());
    }
    
    public void onMouseRelease(MouseEvent e)
    {
        if(hovering)
            onClick();
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
}
