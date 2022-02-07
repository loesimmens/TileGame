package tilegame.worlds.regions.dungeons;

import tilegame.logger.TileGameLogger;
import tilegame.tiles.TileType;

import java.util.logging.Logger;

public class RoomValidator {

    private static final Logger LOGGER = TileGameLogger.getLogger();

    private final Room room;
    private final DungeonMap dungeonMap;

    public RoomValidator(Room room, DungeonMap dungeonMap) {
        this.room = room;
        this.dungeonMap = dungeonMap;
    }

    public boolean ableToBuildRoom() {

        if (roomWouldFallOfTheMap()) {
            return false;
        }

        boolean roomBuildMoved = false;

        for (int i = room.getLeftSideXCoordinate(); i < room.getLeftSideXCoordinate() + room.getWidth(); i++) {
            for (int j = room.getLowerSideYCoordinate(); j < room.getLowerSideYCoordinate() + room.getHeight(); j++) {
                if (tryingToBuildOnExistingRoom(i, j)) {
                    return false;
                }
                if (tryingToBuildCornerOfRoomOnExistingFloor(i, j)) {
                    return false;
                }
                if (tryingToBuildWallNotCornerOnExistingFloor(i, j)) {
                    if (tryingToBuildWallOnExistingFloorNextToExistingDoor(i, j)) {
                        return false;
                    } else {
                        dungeonMap.getMap()[i][j] = TileType.DOOR; //todo: if room ends up not getting built, this should not be a door?
                    }
                }
                if (tryingToBuildFloorOnExistingWall(i, j)) {
                    return false;
                }
                //if trying to build on empty tile but neighbouring tile is wall, move build location
                if (dungeonMap.getMap()[i][j] == TileType.FLOOR)
                {
                    boolean roomBuildMovedInThisIteration;
                    if (!roomBuildMoved) {
                        roomBuildMovedInThisIteration = moveRoomBuildIfNecessary(i, j);
                    } else {
                        return false;
                    }

                    if (roomBuildMovedInThisIteration) {
                        LOGGER.warning("trying to build wall and finding neighbouring existing wall, so moving building site to overlap wall with other room");
                        roomBuildMoved = true;
                        return ableToBuildRoom();
                    }
                }
            }
        }
        return true;
    }

    private boolean moveRoomBuildIfNecessary(int i, int j) {
        if (onLeftEdgeOfRoom(i) && dungeonMap.getMap()[i - 1][j] == TileType.WALL) {
            room.setLeftSideXCoordinate(room.getLeftSideXCoordinate() - 1);
        } else if (onRightEdgeOfRoom(i) && dungeonMap.getMap()[i + 1][j] == TileType.WALL) {
            room.setLeftSideXCoordinate(room.getLeftSideXCoordinate() + 1);
        } else if (onLowerSideOfRoom(j) && dungeonMap.getMap()[i][j - 1] == TileType.WALL) {
            room.setLowerSideYCoordinate(room.getLowerSideYCoordinate() - 1);
        } else if (onUpperSideOfRoom(j) && dungeonMap.getMap()[i][j + 1] == TileType.WALL) {
            room.setLowerSideYCoordinate(room.getLowerSideYCoordinate() + 1);
        } else {
            return false;
        }
        return true;
    }

    private boolean tryingToBuildFloorOnExistingWall(int i, int j) {
        return dungeonMap.getMap()[i][j] == TileType.WALL
                && (i != room.getLeftSideXCoordinate() && i != room.getLeftSideXCoordinate() + room.getWidth() - 1
                && j != room.getLowerSideYCoordinate() && j != room.getLowerSideYCoordinate() + room.getHeight() - 1);
    }


    private boolean tryingToBuildWallOnExistingFloorNextToExistingDoor(int i, int j) {
        return dungeonMap.getMap()[i - 1][j] == TileType.DOOR || dungeonMap.getMap()[i + 1][j] == TileType.DOOR
                || dungeonMap.getMap()[i][j - 1] == TileType.DOOR || dungeonMap.getMap()[i][j + 1] == TileType.DOOR;
    }

    private boolean roomWouldFallOfTheMap() {
        return room.getLeftSideXCoordinate() + room.getWidth() > dungeonMap.getWidth() || room.getLowerSideYCoordinate() + room.getHeight() > dungeonMap.getHeight();
    }

    private boolean tryingToBuildOnExistingRoom(int i, int j) {
        return dungeonMap.getRoomOrPath()[i][j].equals(TileType.ROOM);
    }

    private boolean tryingToBuildCornerOfRoomOnExistingFloor(int i, int j) {
        return (dungeonMap.getMap()[i][j] == TileType.FLOOR
                && onLeftEdgeOfRoom(i) && (onVerticalEdgeOfRoom(j)))
                || (onRightEdgeOfRoom(i) && (onVerticalEdgeOfRoom(j)));
    }

    private boolean tryingToBuildWallNotCornerOnExistingFloor(int i, int j) {
        return dungeonMap.getMap()[i][j] == TileType.FLOOR
                && (((onHorizontalEdgeOfRoom(i)) && !(onVerticalEdgeOfRoom(j)))
                || (!(onHorizontalEdgeOfRoom(i)) && (onVerticalEdgeOfRoom(j))));
    }

    boolean onEdgeOfRoom(int i, int j) {
        return onHorizontalEdgeOfRoom(i) || onVerticalEdgeOfRoom(j);
    }

    private boolean onHorizontalEdgeOfRoom(int i) {
        return onLeftEdgeOfRoom(i) || onRightEdgeOfRoom(i);
    }

    private boolean onLeftEdgeOfRoom(int i) {
        return i == room.getLeftSideXCoordinate();
    }

    private boolean onRightEdgeOfRoom(int i) {
        return i == room.getRightSideXCoordinate();
    }

    private boolean onVerticalEdgeOfRoom(int j) {
        return onLowerSideOfRoom(j) || onUpperSideOfRoom(j);
    }

    private boolean onLowerSideOfRoom(int j) {
        return j == room.getLowerSideYCoordinate();
    }

    private boolean onUpperSideOfRoom(int j) {
        return j == room.getUpperSideYCoordinate();
    }
}
