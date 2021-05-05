/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class Assets 
{
    private static Assets assets = new Assets();
    
    private final int width = 32, height = 32;
    
    public Font font14, font28;
    
    public HashMap<String, BufferedImage> imageMap;
    public HashMap<String, BufferedImage[]> imageArrayMap;
    public HashMap<Integer, HashMap<String, BufferedImage[]>> creatureAnimMap;
    
    private Assets(){}
    
    public static Assets getAssets()
    {
        return assets;
    }
    
    //going to load everything only once
    public void init()
    {
        font14 = FontLoader.loadFont("res/fonts/slkscr.ttf", 14);
        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
        
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
        SpriteSheet creatureSheet = new SpriteSheet(ImageLoader.loadImage("res/textures/creatureSheet.png"));
        
        imageMap = new HashMap<>();
        
        imageMap.put("inventoryScreen", ImageLoader.loadImage("res/textures/inventoryScreen.png"));
        imageMap.put("dialogueBox", ImageLoader.loadImage("res/textures/window_bg.png"));
        
        //Tiles
        imageMap.put("dirt", sheet.crop(width, 0, width, height));
        imageMap.put("grass", sheet.crop(width * 2, 0, width, height));
        imageMap.put("floor", sheet.crop(width * 3, height * 2, width, height));
        
        imageMap.put("wallUp", sheet.crop(width * 3, 0, width, height));
        imageMap.put("wallLeft", sheet.crop(width * 4, height * 6, width, height));
        imageMap.put("wallRight", sheet.crop(width * 5, height * 6, width, height));
        imageMap.put("wallDown", sheet.crop(width * 6, height * 6, width, height));
        imageMap.put("wallUL", sheet.crop(width * 7, height * 6, width, height));
        imageMap.put("wallUR", sheet.crop(0, height * 7, width, height));
        imageMap.put("wallDL", sheet.crop(width, height * 7, width, height));
        imageMap.put("wallDR", sheet.crop(width * 2, height * 7, width, height));
        imageMap.put("wallLR", sheet.crop(0, height * 6, width, height));
        imageMap.put("wallUD", sheet.crop(width * 3, height * 6, width, height));
        
        imageMap.put("door", sheet.crop(width, height * 6, width, height));
        imageMap.put("curtain", sheet.crop(width * 5, 0, width, height));
        imageMap.put("curtainItem", sheet.crop(width * 7, height * 2, width, height));
        
        imageMap.put("counter", sheet.crop(width * 5, height, width, height));
        imageMap.put("sink", sheet.crop(width * 6, height, width, height));
        imageMap.put("counterItem", sheet.crop(width * 2, height * 6, width, height));
        
        imageMap.put("couch", sheet.crop(width * 4, 0, width, height));
        imageMap.put("table", sheet.crop(0, 0, width, height));
        
        imageMap.put("tree", sheet.crop(0, height, width, height * 2));
        imageMap.put("rock", sheet.crop(width * 2, height, width, height));
        imageMap.put("wood", sheet.crop(width, height, width, height));
        
        imageMap.put("pan", sheet.crop(width, height * 2, width, height));
        imageMap.put("present", sheet.crop(width * 3, height, width, height));
        imageMap.put("note", sheet.crop(width * 4, height, width, height));
        imageMap.put("toys", sheet.crop(width * 6, 0, width, height));
        imageMap.put("suitcase", sheet.crop(width * 7, 0, width, height));
        
        imageMap.put("cat1", sheet.crop(width * 5, height * 2, width, height));
        imageMap.put("cat2", sheet.crop(width * 4, height * 2, width, height));
        
        imageMap.put("lb", sheet.crop(width * 4, height * 4, width * 2, height * 2));
        
        imageArrayMap = new HashMap<>();
        
        imageArrayMap.put("btn_start", new BufferedImage[]
                {
                    sheet.crop(width * 6, height * 5, width * 2, height),
                    sheet.crop(width * 6, height * 4, width * 2, height)
                });
        
        imageArrayMap.put("btn_empty", new BufferedImage[]
        {
            sheet.crop(width * 4, height * 5, width * 2, height),
            sheet.crop(0, height * 5, width * 2, height)
        });
        
        creatureAnimMap = new HashMap<>(); //stores all the animations for a single creature
        
        
        for(int i = 0; i <= 3; i++)
        {
            creatureAnimMap.put(i, new HashMap<>());
            creatureAnimMap.get(i).put("down", new BufferedImage[]
            {
                creatureSheet.crop(0, height * i, width, height),
                creatureSheet.crop(width, height * i, width, height)
            });
            creatureAnimMap.get(i).put("up", new BufferedImage[]
            {
                creatureSheet.crop(width * 2, height * i, width, height),
                creatureSheet.crop(width * 3, height * i, width, height)
            });
            creatureAnimMap.get(i).put("left", new BufferedImage[]
            {
                creatureSheet.crop(width * 4, height * i, width, height),
                creatureSheet.crop(width * 5, height * i, width, height)
            });
            creatureAnimMap.get(i).put("right", new BufferedImage[]
            {
                creatureSheet.crop(width * 6, height * i, width, height),
                creatureSheet.crop(width * 7, height * i, width, height)
            });
        }
    }
}
