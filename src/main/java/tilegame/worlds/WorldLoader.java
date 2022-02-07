/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.worlds;

import tilegame.utils.Utils;
import tilegame.worlds.regions.Region;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class WorldLoader 
{   
    private WorldLoader(){}
    
    public static void loadWorld(String path)
    {
        var file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        var width = Integer.parseInt(tokens[0]);
        World.setWidthInNTiles(width);
        var height = Integer.parseInt(tokens[1]);
        World.setHeightInNTiles(height);
        var spawnX = Integer.parseInt(tokens[2]);
        var spawnY = Integer.parseInt(tokens[3]);
        World.setPlayer(spawnX, spawnY);
        var tiles = new int[width][height];
        for(var y = 0; y < height; y++)
        {
            for(var x = 0; x < width; x++)
            {
                tiles[x][y] = Integer.parseInt(tokens[(x + y * width) + 4]);
            }
        }
        int regionId = World.getnRegions();
        Region region = new Region(regionId);
        region.setTilesFromValues(tiles);
        World.addRegion(region);
    }
}
