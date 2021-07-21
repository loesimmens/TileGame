/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.saves;

import tilegame.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Loes Immens, based on Almas Baimagambetov's Java Serialization (Save/Load data) video:
 * https://www.youtube.com/watch?v=-xW0pBZqpjU
 */
public class SaveData implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int health;
    private float x;
    private float y;
    private List<Item> inventoryItems;

    public String getName() 
    {
        return name;
    }

    public int getHealth() 
    {
        return health;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }

    public void setHealth(int health) 
    {
        this.health = health;
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

    public List<Item> getInventoryItems()
    {
        return inventoryItems;
    }
}
