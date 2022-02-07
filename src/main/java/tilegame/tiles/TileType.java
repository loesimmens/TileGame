package tilegame.tiles;

public enum TileType {
    FLOOR(0),
    DIRT(1),
    GRASS(2),
    WALL_UP(3),
    WALL_LEFT(4),
    WALL_RIGHT(5),
    WALL_DOWN(6),
    WALL_UP_LEFT(7),
    WALL_UP_RIGHT(8),
    WALL_DOWN_LEFT(9),
    WALL_DOWN_RIGHT(10),
    WALL_LEFT_RIGHT(11),
    WALL_UP_DOWN(12),
    DOOR(13),
    KITCHEN_COUNTER(14),
    KITCHEN_SINK(15),
    CURTAIN(16),
    EMPTY(17),
    WALL(18),
    LADDER(19),
    //add new types here as the following types have no visuals
    //remember to change the int values of the last four types
    ROOM(20),
    PATH(21),
    NO_PATH(22),
    CONNECTED(23);

    private final int value;

    TileType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static TileType getTileType(int value) {
        return values()[value];
    }
}
