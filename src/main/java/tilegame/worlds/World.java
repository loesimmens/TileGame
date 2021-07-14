/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.worlds;

import tilegame.Game;
import tilegame.entities.EntityManager;
import tilegame.entities.creatures.Cat;
import tilegame.entities.creatures.Creature;
import tilegame.entities.creatures.NPC;
import tilegame.entities.creatures.Player;
import tilegame.entities.staticEntities.StaticEntity;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.items.Item;
import tilegame.items.ItemManager;
import tilegame.items.Note;
import tilegame.states.GameState;
import tilegame.tiles.Tile;
import tilegame.tiles.TileManager;

import java.awt.*;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class World implements java.io.Serializable
{
    private GameState gameState;
    private static int width, height; //in amount of tiles
    private int spawnX, spawnY;
    private static int[][] tiles;
    //Entities
    private EntityManager entityManager;
    private Player player;
    //Item
    private static ItemManager itemManager;
    
    public World(GameState gameState, int spawnX, int spawnY, int[][] tiles)
    {
        this.gameState = gameState;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.tiles = tiles;
        
        player = Player.getInstance(spawnX, spawnY);
        entityManager = EntityManager.getInstance();

        Item[] counterItems = {Item.pan, new Note(Assets.getAssets().imageMap.get("note"), "note", 10, 
        "Goed gevonden!\n\n"
                + "Kijk nu op de plek waar Jip het liefst Kiekeboe speelde."
                )};
        entityManager.addEntity(new StaticEntity(512, 192, Tile.TILEWIDTH, Tile.TILEHEIGHT, 0, 0, 3 * Tile.TILEWIDTH / 4, Tile.TILEHEIGHT, 8, "counter", true, counterItems));
        
        entityManager.addEntity(new StaticEntity(64, 448, 2 * Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, Creature.DEFAULT_CREATURE_WIDTH / 2, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 5, "couch", false, null));
        entityManager.addEntity(new StaticEntity(192, 448, Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, 40, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 6, "table", false, null));
        entityManager.addEntity(new StaticEntity(128, 128, 2 * Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, 80, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 7, "table", false, null));
        
        Item[] curtainNote = {new Note(Assets.getAssets().imageMap.get("note"), "note", 14,
        "Kiekeboe! Gevonden! "
                + "Laatste hint:\n"
                + "dit kan open en dicht. Er past een heleboel in, maar soms overschat Jip dit een klein beetje. "
                + "Zie je het voorwerp niet liggen, kijk dan op de plek waar je het normaal gesproken vindt.")};
        entityManager.addEntity(new StaticEntity(64, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 13, "curtain", true, curtainNote));
        
        Item[] toys = {Item.suitcase, Item.present, new Note(Assets.getAssets().imageMap.get("note"), "note", 15,
        "Gefeliciteerd! Je hebt je cadeau gevonden! "
                + "Ook in het echte huis vind je hier je cadeau. "
                + "Veel plezier ermee! "
                + "Liefs, Loes en Jip")};         
        
        entityManager.addEntity(new StaticEntity(320, 192, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT / 2,
        11, "toys", true, toys));
        
        entityManager.addEntity(new NPC(256, 288, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 2, "Loes"));
        entityManager.addEntity(new NPC(192, 256, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 1, "Jip"));
        entityManager.addEntity(new Cat(128, 300, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3, "Roos"));
        entityManager.addEntity(new Cat(320, 500, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 4, "Douwe"));
        
        entityManager.addEntity(player);
        
        itemManager = ItemManager.getInstance();
    }
    
    public void tick()
    {
        ItemManager.tick();
        entityManager.tick();
    }
    
    public void render(Graphics g)
    {
        //these 4 variables instead of 0 and width/height, so not the entire map will be rendered if it's not visible through the camera
        int xStart = (int) Math.max(0, GameCamera.getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (GameCamera.getxOffset() + Game.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, GameCamera.getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(height, (GameCamera.getyOffset() + Game.getHeight()) / Tile.TILEHEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++)
        {
            for(int x = xStart; x < xEnd; x++)
            {
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - GameCamera.getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - GameCamera.getyOffset()));
            }
        }

        ItemManager.render(g);
        entityManager.render(g);
    }
    
    @Override
    public String toString()
    {
        return "world";
    }
    
    public static Tile getTile(int x, int y)
    {
        if(x < 0 || y < 0 || x >= width || y >= height)
            return TileManager.getTileManager().grassTile;
        
        Tile t = TileManager.getTileManager().tiles[tiles[x][y]];
        if(t == null)
            return TileManager.getTileManager().dirtTile;
        return t;
    }

    public static int getWidth()
    {
        return width;
    }

    public static void setWidth(int width) {
        World.width = width;
    }

    public static int getHeight()
    {
        return height;
    }

    public static void setHeight(int height) {
        World.height = height;
    }

    public Player getPlayer() 
    {
        return player;
    }
}
