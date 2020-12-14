/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.items.Item;
import tilegame.items.Note;
import tilegame.states.GameState;
import tilegame.utils.Utils;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class InventoryPanel 
{
    private boolean active = false;
    private GameState gameState;
    private Inventory inventory;
    int selectedItem;
    
    private final int invX = 64;
    private final int invY = 48;
    private final int invWidth = 512;
    private final int invHeight = 384;
    private final int invListCenterX = invX + 171;
    
    private final int invListCenterY = invY + invHeight / 2 + 5;
    private final int invListSpacing = 30;
    private final int invImageX = 452;
    private final int invImageY = 82;
    
    private final int invImageWidth = 64;
    private final int invImageHeight = 64;
    
    private final int invCountX = 484;
    private final int invCountY = 172;
    
    public InventoryPanel(GameState gameState, Inventory inventory)
    {
        this.gameState = gameState;
        this.inventory = inventory;
        selectedItem = 0;
    }
    
    public void tick()
    {
        if(gameState.getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_I))
            active = !active;
        if(!active)
            return;
        if(!inventory.getInventoryItems().isEmpty()) //if empty, still show screen, but do not try to scroll through items
        {
            if(gameState.getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_W))
                selectedItem--;
            if(gameState.getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_S))
                selectedItem++;

            if(selectedItem < 0)
                selectedItem = inventory.getInventoryItems().size() - 1;
            else if(selectedItem >= inventory.getInventoryItems().size())
                selectedItem = 0;
        }
    }
    
    public void render(Graphics g)
    {
        if(!active)
            return;
        
//        if(inventory.getInventoryItems().isEmpty()) //todo
//            return;
        
        g.drawImage(Assets.getAssets().imageMap.get("inventoryScreen"), invX, invY, invWidth, invHeight, null);
        
        if(!inventory.getInventoryItems().isEmpty())
        {
            renderItemsInInventory(g);
            renderSelectedItem(g);
        
            if(inventory.getInventoryItems().get(selectedItem) instanceof Note)
            {
                Note note = (Note)inventory.getInventoryItems().get(selectedItem);

                if(!note.isActive() && gameState.getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_E))
                {
                    note.setActive(true);
                }
                else if(note.isActive() && gameState.getGame().getKeyManager().hasJustBeenPressed(KeyEvent.VK_E))
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
        Item item = inventory.getInventoryItems().get(selectedItem);
        g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
        
        Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.white, Assets.getAssets().font28);
    }
    
    private void renderItemsInInventory(Graphics g) 
    {
        for(int i = -5; i < 6; i++)
        {
            if(selectedItem + i < 0 || selectedItem + i >= inventory.getInventoryItems().size())
                continue;
            if(i == 0)
            {
                Text.drawString(g, "> " + inventory.getInventoryItems().get(selectedItem + i).getName() + " <", invListCenterX,
                    invListCenterY + i * invListSpacing, true, Color.yellow, Assets.getAssets().font28);
            }
            else
            {
                Text.drawString(g, inventory.getInventoryItems().get(selectedItem + i).getName(), invListCenterX,
                    invListCenterY + i * invListSpacing, true, Color.white, Assets.getAssets().font28);
            }
        }
    }
    
    private void renderNote(Graphics g) 
    {
        g.drawImage(Assets.getAssets().imageMap.get("dialogueBox"), 10, 10, gameState.getGame().getWidth() - 10, 
                gameState.getGame().getHeight() - 10, null);
        Note note = (Note)inventory.getInventoryItems().get(selectedItem);
        String textToDisplay = Utils.cutTextToFitLine(note.getText(), 30);
        
        int textY = 100;
        
        for(String line: textToDisplay.split("\n"))
        {
            Text.drawString(g, line, 64, textY, false, Color.black, Assets.getAssets().font28);
            textY += 32; 
        }
        
    }
    
    public boolean isActive()
    {
        return active;
    }
}
