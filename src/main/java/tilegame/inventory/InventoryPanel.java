/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.inventory;

import tilegame.Game;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.items.Note;
import tilegame.logger.TileGameLogger;
import tilegame.utils.Listener;
import tilegame.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class InventoryPanel implements Listener, Ticking, Rendering
{
    private static final InventoryPanel inventoryPanel = new InventoryPanel();
    private static boolean active = false;
    private int selectedItem;
    private int keyCode;
    
    private static final int INV_X = 64;
    private static final int INV_Y = 48;
    private static final int INV_WIDTH = 512;
    private static final int INV_HEIGHT = 384;
    private static final int INV_LIST_CENTER_X = INV_X + 171;
    
    private static final int INV_LIST_CENTER_Y = INV_Y + INV_HEIGHT / 2 + 5;
    private static final int INV_LIST_SPACING = 30;
    private static final int INV_IMAGE_X = 452;
    private static final int INV_IMAGE_Y = 82;
    
    private static final int INV_IMAGE_WIDTH = 64;
    private static final int INV_IMAGE_HEIGHT = 64;
    
    private static final int INV_COUNT_X = 484;
    private static final int INV_COUNT_Y = 172;

    private static final Logger LOGGER = TileGameLogger.getLogger();
    
    private InventoryPanel()
    {
        selectedItem = 0;
    }

    public static final InventoryPanel getInstance(){
        return inventoryPanel;
    }

    @Override
    public void tick()
    {
        if(keyCode == KeyEvent.VK_I)
            active = !active;
        if(!active)
            return;
        if(!Inventory.getInventoryItems().isEmpty()) //if empty, still show screen, but do not try to scroll through items
        {
            if(keyCode == KeyEvent.VK_W)
                selectedItem--;
            if(keyCode == KeyEvent.VK_S)
                selectedItem++;

            if(selectedItem < 0)
                selectedItem = Inventory.getInventoryItems().size() - 1;
            else if(selectedItem >= Inventory.getInventoryItems().size())
                selectedItem = 0;
        }
    }

    @Override
    public void render(Graphics g)
    {
        if(!active)
            return;
        
        g.drawImage(Assets.getAssets().IMAGE_MAP.get("inventoryScreen"), INV_X, INV_Y, INV_WIDTH, INV_HEIGHT, null);
        
        if(!Inventory.getInventoryItems().isEmpty())
        {
            renderItemsInInventory(g);
            renderSelectedItem(g);
        
            if(Inventory.getInventoryItems().get(selectedItem) instanceof Note)
            {
                var note = (Note)Inventory.getInventoryItems().get(selectedItem);

                if(!note.isActive() && keyCode == KeyEvent.VK_E)
                {
                    note.setActive(true);
                }
                else if(note.isActive() && keyCode == KeyEvent.VK_E)
                {
                    note.setActive(false);
                }
                if(note.isActive())
                   renderNote(g);
            }
        }
    }
    
    public void renderSelectedItem(Graphics g)
    {
        var item = Inventory.getInventoryItems().get(selectedItem);
        g.drawImage(item.getTexture(), INV_IMAGE_X, INV_IMAGE_Y, INV_IMAGE_WIDTH, INV_IMAGE_HEIGHT, null);
        
        Text.drawString(g, Integer.toString(item.getCount()), INV_COUNT_X, INV_COUNT_Y, true, Color.white, Assets.getAssets().FONT_28);
    }
    
    private void renderItemsInInventory(Graphics g) 
    {
        for(int i = -5; i < 6; i++)
        {
            if(selectedItem + i < 0 || selectedItem + i >= Inventory.getInventoryItems().size())
                continue;
            if(i == 0)
            {
                Text.drawString(g, "> " + Inventory.getInventoryItems().get(selectedItem + i).getName() + " <", INV_LIST_CENTER_X,
                    INV_LIST_CENTER_Y + i * INV_LIST_SPACING, true, Color.yellow, Assets.getAssets().FONT_28);
            }
            else
            {
                Text.drawString(g, Inventory.getInventoryItems().get(selectedItem + i).getName(), INV_LIST_CENTER_X,
                    INV_LIST_CENTER_Y + i * INV_LIST_SPACING, true, Color.white, Assets.getAssets().FONT_28);
            }
        }
    }
    
    private void renderNote(Graphics g) 
    {
        g.drawImage(Assets.getAssets().IMAGE_MAP.get("dialogueBox"), 10, 10, Game.getDisplayWidth() - 10,
                Game.getDisplayHeight() - 10, null);
        var note = (Note)Inventory.getInventoryItems().get(selectedItem);
        String textToDisplay = Utils.cutTextToFitLine(note.getText(), 30);
        
        var textY = 100;
        
        for(String line: textToDisplay.split("\n"))
        {
            Text.drawString(g, line, 64, textY, false, Color.black, Assets.getAssets().FONT_28);
            textY += 32; 
        }
        
    }
    
    public static boolean isActive()
    {
        return active;
    }

    @Override
    public void update(Object o) {
        if(o instanceof Integer){
            keyCode = (Integer) o;
        }
        else{
            throw new IllegalArgumentException("input is not an integer!");
        }
    }
}
