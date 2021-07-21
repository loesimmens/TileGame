/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.entities.static_entities;

import tilegame.entities.Entity;
import tilegame.entities.EntityState;
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
    private final boolean dropping;
    private final Item[] items;
    
    public StaticEntity(float x, float y, int width, int height, int xBounds, int yBounds, int boundsWidth, int boundsHeight, int id, String name, boolean dropping, Item[] dropped)
    {
        super(x, y, width, height, id, name);

        this.dropping = dropping;
        items = dropped;
        
        bounds.x = xBounds;
        bounds.y = yBounds;
        bounds.width = boundsWidth;
        bounds.height = boundsHeight;
    }

    @Override
    public void tick(){}

    @Override
    public void render(Graphics g) 
    {
        g.drawImage(Assets.getAssets().imageMap.get(name), (int) (xLocation - GameCamera.getxOffset()),
                (int) (yLocation - GameCamera.getyOffset()), width, height, null);
    }

    @Override
    public void die() 
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void interact() {
        entityState = EntityState.INTERACTING;
        if(dropping) {
            for(var i = 0; i < items.length; i++) {
                if(xLocation < 128) {
                    items[i].setPosition((int) xLocation, (int) (yLocation + 128) + i * 32);
                }
                else {
                    items[i].setPosition((int) (xLocation - 64), (int)(yLocation + i * 32));
                }
                items[i].setVisible(true);
                ItemManager.getInstance().addItem(items[i]);
            }
        }
    }
}
