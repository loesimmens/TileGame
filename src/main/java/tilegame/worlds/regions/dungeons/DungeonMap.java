package tilegame.worlds.regions.dungeons;

import tilegame.tiles.TileType;

public class DungeonMap {
    private final int width;
    private final int height;
    private final TileType[][] map;
    private final TileType[][] pathMap;
    private final TileType[][] roomOrPath;

    public DungeonMap(int mapWidth, int mapHeight) {
        this.width = mapWidth;
        this.height = mapHeight;
        this.map = new TileType[mapWidth][mapHeight];
        this.pathMap = new TileType[mapWidth][mapHeight];
        this.roomOrPath = new TileType[mapWidth][mapHeight];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileType[][] getMap() {
        return map;
    }

    public TileType[][] getPathMap() {
        return pathMap;
    }

    public TileType[][] getRoomOrPath() {
        return roomOrPath;
    }

}
