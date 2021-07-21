/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.inventory;

import tilegame.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Inventory
{
    private static List<Item> inventoryItems = new ArrayList<>();
    
    private Inventory() {}

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

    public static List<Item> getInventoryItems()
    {
        return inventoryItems;
    }

    public static void setInventoryItems(List<Item> inventoryItems)
    {
        Inventory.inventoryItems = inventoryItems;
    }
}
