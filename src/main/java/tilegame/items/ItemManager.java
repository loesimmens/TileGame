/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.items;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class ItemManager implements java.io.Serializable
{
    private static final ItemManager itemManager = ItemManager.getInstance();
    private static ArrayList<Item> items = new ArrayList<>();
    
    private ItemManager() {}

    public static ItemManager getInstance(){
        return itemManager;
    }
    
    public static void tick()
    {
        if(!items.isEmpty()){
            Iterator<Item> it = items.iterator();
            while(it.hasNext())
            {
                Item i = it.next();
                i.tick();
                if(i.isPickedUp())
                    it.remove();
            }
        }
    }
    
    public static void render(Graphics g)
    {
        for(Item i : items)
            i.render(g);
    }

    public void addItem(Item i)
    {
        i.setItemManager(this);
        items.add(i);
    }
}
