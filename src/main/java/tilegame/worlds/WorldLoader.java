/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.worlds;

import tilegame.states.GameState;
import tilegame.utils.Utils;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class WorldLoader 
{   
    private WorldLoader(){}
    
    public static World loadWorld(GameState gameState, String path)
    {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+"); //split on any white space
        int width = Integer.parseInt(tokens[0]);
        World.setWidth(width);
        int height = Integer.parseInt(tokens[1]);
        World.setHeight(height);
        int spawnX = Integer.parseInt(tokens[2]);
        int spawnY = Integer.parseInt(tokens[3]);
        
        int tiles[][] = new int[width][height];
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
        }
        
        World world = new World(gameState, spawnX, spawnY, tiles);
        return world;
    }
}
