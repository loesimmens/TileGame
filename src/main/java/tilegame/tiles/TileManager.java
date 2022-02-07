/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tilegame.tiles;

import tilegame.gfx.Assets;

import java.util.ArrayList;
import java.util.List;

/**
 * based on CodeNMore's tutorial, see: https://github.com/CodeNMore/New-Beginner-Java-Game-Programming-Src
 * expanded on by Loes Immens
 */
public class TileManager 
{
    private static TileManager tileManager = new TileManager();
    
    private TileManager(){}

    private List<Tile> tiles;
    private Tile emptyTile;
    private Tile floorTile;
    private Tile dirtTile;
    private Tile grassTile;
    private Tile wallTile;
    private Tile wallUpTile;
    private Tile wallLeftTile;
    private Tile wallRightTile;
    private Tile wallDownTile;
    private Tile wallULTile;
    private Tile wallURTile;
    private Tile wallDLTile;
    private Tile wallDRTile;
    private Tile wallLRTile;
    private Tile wallUDTile;
    private Tile doorTile;
    private Tile curtainTile;
    private Tile counterTile;
    private Tile sinkTile;
    private Tile ladderTile;
    
    public static TileManager getTileManager()
    {
        return tileManager;
    }
    
    public void init()
    {
        tiles = new ArrayList<>();

        floorTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.FLOOR), 0, TileType.FLOOR,false);
        tiles.add(floorTile);
        dirtTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.DIRT), 1, TileType.DIRT, false);
        tiles.add(dirtTile);
        grassTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.GRASS), 2, TileType.GRASS, false);
        tiles.add(grassTile);

        wallUpTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_UP), 3, TileType.WALL_UP, true);
        tiles.add(wallUpTile);
        wallLeftTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_LEFT), 4, TileType.WALL_LEFT,true);
        tiles.add(wallLeftTile);
        wallRightTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_RIGHT), 5, TileType.WALL_RIGHT,true);
        tiles.add(wallRightTile);
        wallDownTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_DOWN), 6, TileType.WALL_DOWN, true);
        tiles.add(wallDownTile);
        wallULTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_UP_LEFT), 7, TileType.WALL_UP_LEFT, true);
        tiles.add(wallULTile);
        wallURTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_UP_RIGHT), 8, TileType.WALL_UP_RIGHT, true);
        tiles.add(wallURTile);
        wallDLTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_DOWN_LEFT), 9, TileType.WALL_DOWN_LEFT, true);
        tiles.add(wallDLTile);
        wallDRTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_DOWN_RIGHT), 10, TileType.WALL_DOWN_RIGHT,true);
        tiles.add(wallDRTile);
        wallLRTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_LEFT_RIGHT), 11, TileType.WALL_LEFT_RIGHT,true);
        tiles.add(wallLRTile);
        wallUDTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL_UP_DOWN), 12, TileType.WALL_UP_DOWN,true);
        tiles.add(wallUDTile);
        
        doorTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.DOOR), 13, TileType.DOOR,false);
        tiles.add(doorTile);

        counterTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.KITCHEN_COUNTER), 14,TileType.KITCHEN_COUNTER, true);
        tiles.add(counterTile);
        sinkTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.KITCHEN_SINK), 15,TileType.KITCHEN_SINK, true);
        tiles.add(sinkTile);

        curtainTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.CURTAIN), 16, TileType.CURTAIN,true);
        tiles.add(curtainTile);

        emptyTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.EMPTY), 17, TileType.EMPTY, false);
        tiles.add(emptyTile);

        wallTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.WALL), 18, TileType.WALL, true);
        tiles.add(wallTile);

        ladderTile = new Tile(Assets.getAssets().TILE_IMAGE_MAP.get(TileType.LADDER), 19, TileType.LADDER, false);
        tiles.add(ladderTile);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile getDirtTile() {
        return dirtTile;
    }

    public Tile getGrassTile() {
        return grassTile;
    }
}
