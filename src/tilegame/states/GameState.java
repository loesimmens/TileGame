/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.states;

import java.awt.Graphics;
import tilegame.Game;
import tilegame.inventory.Inventory;
import tilegame.inventory.InventoryPanel;
import tilegame.worlds.World;
import tilegame.worlds.WorldLoader;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class GameState extends State
{
    private World world;
    
    //Inventory
    private Inventory inventory;
    private InventoryPanel inventoryPanel;
    
    public GameState(Game game)
    {
        super(game);
        world = WorldLoader.loadWorld(this, "res/worlds/houseInside.txt");
        
        //Inventory
        inventory = new Inventory();
        inventoryPanel = new InventoryPanel(this, inventory);
    }

    @Override
    public void tick() 
    {
        world.tick();
        //Inventory
        inventory.tick();
        inventoryPanel.tick();
    }

    @Override
    public void render(Graphics g) 
    {
        world.render(g);
        inventoryPanel.render(g);
    }

    public Game getGame() 
    {
        return game;
    }

    public World getWorld() 
    {
        return world;
    }

    public Inventory getInventory() 
    {
        return inventory;
    }

    public InventoryPanel getInventoryPanel() 
    {
        return inventoryPanel;
    }
}
