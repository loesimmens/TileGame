/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import tilegame.worlds.World;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class ItemManager implements java.io.Serializable
{
    private ArrayList<Item> items; 
    private World world;
    
    public ItemManager(World world)
    {
        this.world = world;
        items = new ArrayList<Item>();
    }
    
    public void tick()
    {
        Iterator<Item> it = items.iterator();
        while(it.hasNext())
        {
            Item i = it.next();
            i.tick();
            if(i.isPickedUp())
                it.remove();
        }
    }
    
    public void render(Graphics g)
    {
        for(Item i : items)
            i.render(g);
    }
    
    public void addItem(Item i)
    {
        i.setItemManager(this);
        items.add(i);
    }
    
    public World getWorld()
    {
        return world;
    }
}
