/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.items;

import tilegame.entities.creatures.Player;
import tilegame.entities.exceptions.PlayerException;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.inventory.Inventory;
import tilegame.logger.TileGameLogger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Item implements java.io.Serializable
{
    private ItemManager itemManager;
    
    public static Item[] items = new Item[256];
    public static Item woodItem = new Item(Assets.getAssets().imageMap.get("wood"), "Wood", 0);
    public static Item rockItem = new Item(Assets.getAssets().imageMap.get("rock"), "Rock", 1);
    public static Item present = new Item(Assets.getAssets().imageMap.get("present"), "Present", 2);
    public static Item pan = new Item(Assets.getAssets().imageMap.get("pan"), "pan", 9);
    //public static Item note = new Item(Assets.getAssets().imageMap.get("note"), "note", 10);
    public static Item suitcase = new Item(Assets.getAssets().imageMap.get("suitcase"), "suitcase", 12);
    
    //Class
    
    public static final int ITEMWIDTH = 32, ITEMHEIGHT = 32;
    
    /**
     *made transient, because BufferedImage is not Serializable and this field doesn't have to be Serialized.
     */
    protected transient BufferedImage texture;
    protected String name;
    protected final int id;
    
    protected Rectangle bounds;
    
    protected int x, y, count;
    protected boolean pickedUp = false;
    protected boolean visible = false;

    private static final Logger LOGGER = TileGameLogger.getLogger();
    
    public Item(BufferedImage texture, String name, int id)
    {
        this.texture = texture;
        this.name = name;
        this.id = id;
        count = 1; //instead of creating several of the same object, set a counter
        
        bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
        
        items[id] = this;
    }
    
    public void tick()
    {
        try {
            if (Player.getInstance().getCollisionBounds(0f, 0f).intersects(bounds)) {
                pickedUp = true;
                Inventory.addItem(this);
            }
        } catch (PlayerException e) {
            LOGGER.log(Level.SEVERE, "Player not created yet", e);
        }
    }
    
    //to render onto the game world
    public void render(Graphics g)
    {
        if(itemManager == null)
            return;
        if(visible)
            render(g, (int) (x - GameCamera.getxOffset()),
                (int) (y - GameCamera.getyOffset()));
    }
    
    //to render at a specific position, like in an inventory
    public void render(Graphics g, int x, int y)
    {
        g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
    }
    
    //for testing purposes
    public Item createNew(int count)
    {
        Item i = new Item(texture, name, id);
        i.setPickedUp(true);
        i.setCount(count);
        return i;
    }
    
    public Item createNew(int x, int y)
    {
        Item i = new Item(texture, name, id);
        i.setPosition(x, y);
        return i;
    }
    
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
        bounds.x = x;
        bounds.y = y;
    }
    
    //GETTERS SETTERS

    public BufferedImage getTexture() 
    {
        return texture;
    }

    public void setTexture(BufferedImage texture) 
    {
        this.texture = texture;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getX() 
    {
        return x;
    }

    public void setX(int x) 
    {
        this.x = x;
    }

    public int getY() 
    {
        return y;
    }

    public void setY(int y) 
    {
        this.y = y;
    }

    public int getCount() 
    {
        return count;
    }

    public void setCount(int count) 
    {
        this.count = count;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public int getId() 
    {
        return id;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    void setItemManager(ItemManager itemManager) 
    {
        this.itemManager = itemManager;
    }

    public void setVisible(boolean visible) 
    {
        this.visible = visible;
    }
    
    
    
}
