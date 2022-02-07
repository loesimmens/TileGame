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
import tilegame.entities.static_entities.StaticEntity;
import tilegame.game_elements.Rendering;
import tilegame.game_elements.Ticking;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.items.Item;
import tilegame.items.ItemManager;
import tilegame.items.Note;
import tilegame.tiles.Tile;
import tilegame.tiles.TileManager;
import tilegame.worlds.regions.Region;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class World implements java.io.Serializable, Ticking, Rendering
{
    private static int widthInNTiles;
    private static int heightInNTiles;
    private static List<Region> regions;
    private static int nRegions;
    private static int activeRegionId;
    private static final EntityManager ENTITY_MANAGER = EntityManager.getInstance();
    private static final World WORLD = new World();

    private World() {
        regions = new ArrayList<>();
        activeRegionId = 0;
        Item[] counterItems = {Item.pan, new Note(Assets.getAssets().IMAGE_MAP.get("note"), "note", 10,
        "Goed gevonden!\n\n"
                + "Kijk nu op de plek waar Jip het liefst Kiekeboe speelde."
                )};
        ENTITY_MANAGER.addEntity(new StaticEntity(512, 192, Tile.TILEWIDTH, Tile.TILEHEIGHT, 0, 0, 3 * Tile.TILEWIDTH / 4, Tile.TILEHEIGHT, 8, "counter", true, counterItems));
        
        ENTITY_MANAGER.addEntity(new StaticEntity(64, 448, 2 * Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, Creature.DEFAULT_CREATURE_WIDTH / 2, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 5, "couch", false, null));
        ENTITY_MANAGER.addEntity(new StaticEntity(192, 448, Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, 40, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 6, "table", false, null));
        ENTITY_MANAGER.addEntity(new StaticEntity(128, 128, 2 * Creature.DEFAULT_CREATURE_WIDTH, 2 * Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, 80, 2 * Creature.DEFAULT_CREATURE_HEIGHT - 8, 7, "table", false, null));
        
        Item[] curtainNote = {new Note(Assets.getAssets().IMAGE_MAP.get("note"), "note", 14,
        "Kiekeboe! Gevonden! "
                + "Laatste hint:\n"
                + "dit kan open en dicht. Er past een heleboel in, maar soms overschat Jip dit een klein beetje. "
                + "Zie je het voorwerp niet liggen, kijk dan op de plek waar je het normaal gesproken vindt.")};
        ENTITY_MANAGER.addEntity(new StaticEntity(64, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 0, 0, Tile.TILEWIDTH, Tile.TILEHEIGHT, 13, "curtain", true, curtainNote));
        
        Item[] toys = {Item.suitcase, Item.present, new Note(Assets.getAssets().IMAGE_MAP.get("note"), "note", 15,
        "Gefeliciteerd! Je hebt je cadeau gevonden! "
                + "Ook in het echte huis vind je hier je cadeau. "
                + "Veel plezier ermee! "
                + "Liefs, Loes en Jip")};         
        
        ENTITY_MANAGER.addEntity(new StaticEntity(320, 192, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0, 0, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT / 2,
        11, "toys", true, toys));
        
        ENTITY_MANAGER.addEntity(new NPC(256, 288, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 2, "Loes"));
        ENTITY_MANAGER.addEntity(new NPC(192, 256, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 1, "Jip"));
        ENTITY_MANAGER.addEntity(new Cat(128, 300, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3, "Roos"));
        ENTITY_MANAGER.addEntity(new Cat(320, 500, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 4, "Douwe"));
    }

    public static World getInstance() {
        return WORLD;
    }

    public static void addRegion(Region region) {
        regions.add(region);
        nRegions++;
    }

    @Override
    public void tick() {
        ItemManager.getInstance().tick();
        ENTITY_MANAGER.tick();
    }

    @Override
    public void render(Graphics g) {
        //these 4 variables instead of 0 and width/height, so not the entire map will be rendered if it's not visible through the camera
        int xStart = (int) Math.max(0, GameCamera.getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(widthInNTiles, (GameCamera.getxOffset() + Game.getDisplayWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, GameCamera.getyOffset() / Tile.TILEHEIGHT);
        int yEnd = (int) Math.min(heightInNTiles, (GameCamera.getyOffset() + Game.getDisplayHeight()) / Tile.TILEHEIGHT + 1);
        
        for(int y = yStart; y < yEnd; y++)
        {
            for(int x = xStart; x < xEnd; x++)
            {
                getTile(activeRegionId, x, y).render(g, (int) (x * Tile.TILEWIDTH - GameCamera.getxOffset()),
                        (int) (y * Tile.TILEHEIGHT - GameCamera.getyOffset()));
            }
        }

        ItemManager.getInstance().render(g);
        ENTITY_MANAGER.render(g);
    }
    
    @Override
    public String toString()
    {
        return "world";
    }
    
    public static Tile getTile(int regionId, int x, int y)
    {
        if(x < 0 || y < 0 || x >= widthInNTiles || y >= heightInNTiles)
            return TileManager.getTileManager().getGrassTile();
        
        Tile t = TileManager.getTileManager().getTiles().get(regions.get(regionId).getTiles()[x][y].getValue());
        if(t == null)
            return TileManager.getTileManager().getDirtTile();
        return t;
    }

    public static int getWidthInNTiles()
    {
        return widthInNTiles;
    }

    public static void setWidthInNTiles(int widthInNTiles) {
        World.widthInNTiles = widthInNTiles;
    }

    public static int getHeightInNTiles()
    {
        return heightInNTiles;
    }

    public static void setHeightInNTiles(int heightInNTiles) {
        World.heightInNTiles = heightInNTiles;
    }

    public static void setPlayer(int spawnX, int spawnY){
        var player = Player.getInstance(spawnX, spawnY);
        ENTITY_MANAGER.addEntity(player);
    }

    public static int getnRegions() {
        return nRegions;
    }

    public static List<Region> getRegions() {
        return regions;
    }

    public static Region getActiveRegion() {
        return regions.get(activeRegionId);
    }

    public static int getActiveRegionId() {
        return activeRegionId;
    }
}
