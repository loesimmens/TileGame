/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.inventory;

import tilegame.items.Item;

import java.util.ArrayList;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Inventory
{
    private static final Inventory inventory = new Inventory();
    private static ArrayList<Item> inventoryItems;
    
    private Inventory()
    {
        inventoryItems = new ArrayList<>();
    }
    
    public void tick()
    {
        
    }
    
    public static final Inventory getInstance(){
        return inventory;
    }
    
    public static void addItem(Item item)
    {
        for(Item i : inventoryItems)
        {
            if(i.getId() == item.getId())
            {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    public static ArrayList<Item> getInventoryItems()
    {
        return inventoryItems;
    }

    public void setInventoryItems(ArrayList<Item> inventoryItems) 
    {
        this.inventoryItems = inventoryItems;
    }
}
