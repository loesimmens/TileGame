package tilegame.worlds.regions;

import tilegame.tiles.TileType;
import tilegame.worlds.regions.dungeons.Ladder;
import tilegame.worlds.regions.dungeons.Room;

import java.util.List;

public class Region {
    private long id;
    private RegionType regionType;
    private TileType[][] tiles;
    private List<Room> rooms;
    private int nRooms;
    private Ladder ladder;
    private boolean underground;

    public Region(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RegionType getRegionType() {
        return regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }

    public TileType[][] getTiles() {
        return tiles;
    }

    public void setTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }

    public void setTilesFromValues(int[][] tiles) {
        this.tiles = new TileType[tiles.length][tiles[0].length];
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                this.tiles[i][j] = TileType.getTileType(tiles[i][j]);
            }
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getnRooms() {
        return nRooms;
    }

    public void setnRooms(int nRooms) {
        this.nRooms = nRooms;
    }

    public Ladder getLadder() {
        return ladder;
    }

    public void setLadder(Ladder ladder) {
        this.ladder = ladder;
    }

    public boolean isUnderground() {
        return underground;
    }

    public void setUnderground(boolean underground) {
        this.underground = underground;
    }
}
