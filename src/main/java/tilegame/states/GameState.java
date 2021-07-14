/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

import tilegame.inventory.Inventory;
import tilegame.inventory.InventoryPanel;
import tilegame.worlds.World;
import tilegame.worlds.WorldLoader;

import java.awt.*;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class GameState implements State {
    private World world;
    private Inventory inventory;
    private InventoryPanel inventoryPanel;
    
    public GameState()
    {
        world = WorldLoader.loadWorld(this, "res/worlds/houseInside.txt");
        inventory = Inventory.getInstance();
        inventoryPanel = InventoryPanel.getInstance();
    }

    @Override
    public void tick() 
    {
        world.tick();
        inventory.tick();
        inventoryPanel.tick();
    }

    @Override
    public void render(Graphics g) 
    {
        world.render(g);
        inventoryPanel.render(g);
    }
}
