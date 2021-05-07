/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.staticEntities;

import tilegame.entities.Entity;
import tilegame.entities.StateMachine;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.items.Item;
import tilegame.items.ItemManager;

import java.awt.*;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class StaticEntity extends Entity implements java.io.Serializable
{
    private String name;
    private boolean dropping;
    private Item[] items;
    
    public StaticEntity(float x, float y, int width, int height, int xBounds, int yBounds, int boundsWidth, int boundsHeight, int id, String name, boolean dropping, Item[] dropped)
    {
        super(x, y, width, height, id, name);
        
        this.name = name;
        this.dropping = dropping;
        items = dropped;
        
        bounds.x = xBounds;
        bounds.y = yBounds;
        bounds.width = boundsWidth;
        bounds.height = boundsHeight;
    }

    @Override
    public void tick() 
    {
        
    }

    @Override
    public void render(Graphics g) 
    {
        g.drawImage(Assets.getAssets().imageMap.get(name), (int) (x - GameCamera.getInstance().getxOffset()),
                (int) (y - GameCamera.getInstance().getyOffset()), width, height, null);
        //g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public void die() 
    {
        
    }

    @Override
    public void interact() 
    {
        state = StateMachine.INTERACTING;
        if(dropping)
        {
            for(int i = 0; i < items.length; i++)
            {
                if(x < 128)
                {
                    items[i].setPosition((int) x, (int) (y + 128) + i * 32);
                    items[i].setVisible(true);
                    ItemManager.getInstance().addItem(items[i]);
                }
                else
                {
                    items[i].setPosition((int) (x - 64), (int)(y + i * 32));
                    items[i].setVisible(true);
                    ItemManager.getInstance().addItem(items[i]);
                }
            }
        }
    }
}
