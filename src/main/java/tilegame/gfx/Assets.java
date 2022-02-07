/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import tilegame.tiles.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Assets 
{
    private static Assets assets = new Assets();
    
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;
    
    public static final Font FONT_14 = FontLoader.loadFont("res/fonts/slkscr.ttf", 14);
    public static final Font FONT_28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
    
    public static final Map<String, BufferedImage> IMAGE_MAP = new HashMap<>();
    public static final Map<String, BufferedImage[]> IMAGE_ARRAY_MAP = new HashMap<>();
    public static final Map<TileType, BufferedImage> TILE_IMAGE_MAP = new EnumMap<>(TileType.class);
    //stores all the animations for a single creature
    public static final Map<Integer, HashMap<String, BufferedImage[]>> CREATURE_ANIM_MAP = new HashMap<>();
    
    private Assets(){}
    
    public static Assets getAssets()
    {
        return assets;
    }
    
    //going to load everything only once
    public void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
        SpriteSheet creatureSheet = new SpriteSheet(ImageLoader.loadImage("res/textures/creatureSheet.png"));

        IMAGE_MAP.put("inventoryScreen", ImageLoader.loadImage("res/textures/inventoryScreen.png"));
        IMAGE_MAP.put("dialogueBox", ImageLoader.loadImage("res/textures/window_bg.png"));

        TILE_IMAGE_MAP.put(TileType.DIRT, sheet.crop(WIDTH, 0, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.GRASS, sheet.crop(WIDTH * 2, 0, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.FLOOR, sheet.crop(WIDTH * 3, HEIGHT * 2, WIDTH, HEIGHT));

        TILE_IMAGE_MAP.put(TileType.WALL_UP, sheet.crop(WIDTH * 3, 0, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_LEFT, sheet.crop(WIDTH * 4, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_RIGHT, sheet.crop(WIDTH * 5, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_DOWN, sheet.crop(WIDTH * 6, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_UP_LEFT, sheet.crop(WIDTH * 7, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_UP_RIGHT, sheet.crop(0, HEIGHT * 7, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_DOWN_LEFT, sheet.crop(WIDTH, HEIGHT * 7, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_DOWN_RIGHT, sheet.crop(WIDTH * 2, HEIGHT * 7, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_LEFT_RIGHT, sheet.crop(0, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL_UP_DOWN, sheet.crop(WIDTH * 3, HEIGHT * 6, WIDTH, HEIGHT));

        TILE_IMAGE_MAP.put(TileType.KITCHEN_COUNTER, sheet.crop(WIDTH * 5, HEIGHT, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.KITCHEN_SINK, sheet.crop(WIDTH * 6, HEIGHT, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.DOOR, sheet.crop(WIDTH, HEIGHT * 6, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.CURTAIN, sheet.crop(WIDTH * 5, 0, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.EMPTY, sheet.crop(WIDTH * 3, HEIGHT * 7, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.WALL, sheet.crop(WIDTH * 4, HEIGHT * 7, WIDTH, HEIGHT));
        TILE_IMAGE_MAP.put(TileType.LADDER, sheet.crop(WIDTH * 5, HEIGHT * 7, WIDTH, HEIGHT));

        IMAGE_MAP.put("curtainItem", sheet.crop(WIDTH * 7, HEIGHT * 2, WIDTH, HEIGHT));

        IMAGE_MAP.put("counterItem", sheet.crop(WIDTH * 2, HEIGHT * 6, WIDTH, HEIGHT));

        IMAGE_MAP.put("couch", sheet.crop(WIDTH * 4, 0, WIDTH, HEIGHT));
        IMAGE_MAP.put("table", sheet.crop(0, 0, WIDTH, HEIGHT));

        IMAGE_MAP.put("tree", sheet.crop(0, HEIGHT, WIDTH, HEIGHT * 2));
        IMAGE_MAP.put("rock", sheet.crop(WIDTH * 2, HEIGHT, WIDTH, HEIGHT));
        IMAGE_MAP.put("wood", sheet.crop(WIDTH, HEIGHT, WIDTH, HEIGHT));

        IMAGE_MAP.put("pan", sheet.crop(WIDTH, HEIGHT * 2, WIDTH, HEIGHT));
        IMAGE_MAP.put("present", sheet.crop(WIDTH * 3, HEIGHT, WIDTH, HEIGHT));
        IMAGE_MAP.put("note", sheet.crop(WIDTH * 4, HEIGHT, WIDTH, HEIGHT));
        IMAGE_MAP.put("toys", sheet.crop(WIDTH * 6, 0, WIDTH, HEIGHT));
        IMAGE_MAP.put("suitcase", sheet.crop(WIDTH * 7, 0, WIDTH, HEIGHT));

        IMAGE_MAP.put("cat1", sheet.crop(WIDTH * 5, HEIGHT * 2, WIDTH, HEIGHT));
        IMAGE_MAP.put("cat2", sheet.crop(WIDTH * 4, HEIGHT * 2, WIDTH, HEIGHT));

        IMAGE_MAP.put("lb", sheet.crop(WIDTH * 4, HEIGHT * 4, WIDTH * 2, HEIGHT * 2));

        IMAGE_ARRAY_MAP.put("btn_start", new BufferedImage[]
                {
                    sheet.crop(WIDTH * 6, HEIGHT * 5, WIDTH * 2, HEIGHT),
                    sheet.crop(WIDTH * 6, HEIGHT * 4, WIDTH * 2, HEIGHT)
                });
        
        IMAGE_ARRAY_MAP.put("btn_empty", new BufferedImage[]
        {
            sheet.crop(WIDTH * 4, HEIGHT * 5, WIDTH * 2, HEIGHT),
            sheet.crop(0, HEIGHT * 5, WIDTH * 2, HEIGHT)
        });

        for(int i = 0; i <= 3; i++)
        {
            CREATURE_ANIM_MAP.put(i, new HashMap<>());
            CREATURE_ANIM_MAP.get(i).put("down", new BufferedImage[]
            {
                creatureSheet.crop(0, HEIGHT * i, WIDTH, HEIGHT),
                creatureSheet.crop(WIDTH, HEIGHT * i, WIDTH, HEIGHT)
            });
            CREATURE_ANIM_MAP.get(i).put("up", new BufferedImage[]
            {
                creatureSheet.crop(WIDTH * 2, HEIGHT * i, WIDTH, HEIGHT),
                creatureSheet.crop(WIDTH * 3, HEIGHT * i, WIDTH, HEIGHT)
            });
            CREATURE_ANIM_MAP.get(i).put("left", new BufferedImage[]
            {
                creatureSheet.crop(WIDTH * 4, HEIGHT * i, WIDTH, HEIGHT),
                creatureSheet.crop(WIDTH * 5, HEIGHT * i, WIDTH, HEIGHT)
            });
            CREATURE_ANIM_MAP.get(i).put("right", new BufferedImage[]
            {
                creatureSheet.crop(WIDTH * 6, HEIGHT * i, WIDTH, HEIGHT),
                creatureSheet.crop(WIDTH * 7, HEIGHT * i, WIDTH, HEIGHT)
            });
        }
    }
}
