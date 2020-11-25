/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.tiles;

import tilegame.gfx.Assets;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class TileManager 
{
    private static TileManager tileManager = new TileManager();
    
    private TileManager(){}
    
    public Tile[] tiles; 
    public Tile floorTile;
    public Tile dirtTile;
    public Tile grassTile;
    public Tile wallUpTile;
    public Tile wallLeftTile;
    public Tile wallRightTile;
    public Tile wallDownTile;
    public Tile wallULTile;
    public Tile wallURTile;
    public Tile wallDLTile;
    public Tile wallDRTile;
    public Tile wallLRTile;
    public Tile wallUDTile;
    public Tile doorTile;
    public Tile curtainTile;
    public Tile counterTile, sinkTile;
    
    public static TileManager getTileManager()
    {
        return tileManager;
    }
    
    public void init()
    {
        tiles = new Tile[256]; 
        floorTile = new Tile(Assets.getAssets().imageMap.get("floor"), 0, false);
        
        tiles[0] = floorTile;
        dirtTile = new Tile(Assets.getAssets().imageMap.get("dirt"), 1, false);
        tiles[1] = dirtTile;
        grassTile = new Tile(Assets.getAssets().imageMap.get("grass"), 2, false);
        tiles[2] = grassTile;
        
        //walls
        wallUpTile = new Tile(Assets.getAssets().imageMap.get("wallUp"), 3, true);
        tiles[3] = wallUpTile;
        wallLeftTile = new Tile(Assets.getAssets().imageMap.get("wallLeft"), 4, true);
        tiles[4] = wallLeftTile;
        wallRightTile = new Tile(Assets.getAssets().imageMap.get("wallRight"), 5, true);
        tiles[5] = wallRightTile;
        wallDownTile = new Tile(Assets.getAssets().imageMap.get("wallDown"), 6, true);
        tiles[6] = wallDownTile;
        wallULTile = new Tile(Assets.getAssets().imageMap.get("wallUL"), 7, true);
        tiles[7] = wallULTile;
        wallURTile = new Tile(Assets.getAssets().imageMap.get("wallUR"), 8, true);
        tiles[8] = wallURTile;
        wallDLTile = new Tile(Assets.getAssets().imageMap.get("wallDL"), 9, true);
        tiles[9] = wallDLTile;
        wallDRTile = new Tile(Assets.getAssets().imageMap.get("wallDR"), 10, true);
        tiles[10] = wallDRTile;
        wallLRTile = new Tile(Assets.getAssets().imageMap.get("wallLR"), 11, true);
        tiles[11] = wallLRTile;
        wallUDTile = new Tile(Assets.getAssets().imageMap.get("wallUD"), 12, true);
        tiles[12] = wallUDTile;
        
        doorTile = new Tile(Assets.getAssets().imageMap.get("door"), 13, false);
        tiles[13] = doorTile;
        curtainTile = new Tile(Assets.getAssets().imageMap.get("curtain"), 16, true);
        tiles[16] = curtainTile;
        
        counterTile = new Tile(Assets.getAssets().imageMap.get("counter"), 14, true);
        tiles[14] = counterTile;
        sinkTile = new Tile(Assets.getAssets().imageMap.get("sink"), 15, true);
        tiles[15] = sinkTile;
    }
}
