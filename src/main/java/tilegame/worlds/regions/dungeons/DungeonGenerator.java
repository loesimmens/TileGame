package tilegame.worlds.regions.dungeons;

import tilegame.logger.TileGameLogger;
import tilegame.tiles.TileType;
import tilegame.utils.Vector3Int;
import tilegame.worlds.regions.Region;
import tilegame.worlds.regions.RegionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class DungeonGenerator {
    private static final Logger LOGGER = TileGameLogger.getLogger();

    private static final Random RANDOM = new Random();

    private Region region;
    private DungeonMap dungeonMap;

    private int z;

    private int minRoomWidth;
    private int maxRoomWidth;
    private int minRoomHeight;
    private int maxRoomHeight;

    private List<Room> rooms;
    private int nRooms;
    private boolean dungeonHasStartingRoom;
    private boolean dungeonHasEndingRoom;

    private int ladderX;
    private int ladderY;

    public void buildMap(int z, int mapWidth, int mapHeight, int minRoomWidth, int maxRoomWidth, int minRoomHeight, int maxRoomHeight, int nTries)
    {
        initVariables(z, mapWidth, mapHeight, minRoomWidth, maxRoomWidth, minRoomHeight, maxRoomHeight);
        fillMapWithEmptyTiles();
        buildRooms(nTries);

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (dungeonMap.getMap()[i][j] == TileType.FLOOR && dungeonMap.getPathMap()[i][j].equals(TileType.NO_PATH)) {
                    dungeonMap.getMap()[i][j] = TileType.WALL;
                }
                if(!dungeonHasStartingRoom && dungeonMap.getMap()[i][j] == TileType.FLOOR && dungeonMap.getRoomOrPath()[i][j].equals(TileType.ROOM)) {
                    ladderX = i + 1;
                    ladderY = j + 1;

                    dungeonMap.getMap()[i + 1][j + 1] = TileType.LADDER;
                    dungeonMap.getMap()[ladderX][ladderY] = TileType.LADDER;
                    dungeonHasStartingRoom = true;
                }
                if(dungeonMap.getMap()[i][j] == TileType.EMPTY) {
                    dungeonMap.getMap()[i][j] = TileType.WALL;
                }
            }
        }

//        setRegionVariables(z);
    }

    private void initVariables(int z, int mapWidth, int mapHeight, int minRoomWidth, int maxRoomWidth, int minRoomHeight, int maxRoomHeight) {
        this.z = z;
        this.minRoomHeight = minRoomHeight;
        this.minRoomWidth = minRoomWidth;
        this.maxRoomHeight = maxRoomHeight;
        this.maxRoomWidth = maxRoomWidth;
        this.rooms = new ArrayList<>();

        this.dungeonMap = new DungeonMap(mapWidth, mapHeight);
//        this.region = new Region(dungeonMap.getMap());
    }

    private void fillMapWithEmptyTiles() {
        for (int i = 0; i < dungeonMap.getWidth(); i++) {
            for (int j = 0; j < dungeonMap.getHeight(); j++) {
                dungeonMap.getMap()[i][j] = TileType.EMPTY;
                dungeonMap.getPathMap()[i][j] = TileType.EMPTY;
                dungeonMap.getRoomOrPath()[i][j] = TileType.EMPTY;
            }
        }
    }

    private void buildRooms(int nTries) {
        for (int k = 0; k < nTries; k++) {
            tryToBuildRandomRoom();
        }
    }

    private void tryToBuildRandomRoom() {
        int roomWidth = RANDOM.nextInt(maxRoomWidth - minRoomWidth) + minRoomWidth;
        int roomHeight = RANDOM.nextInt(maxRoomHeight - minRoomHeight) + minRoomHeight;

        int roomLeftSideXCoordinate = RANDOM.nextInt(dungeonMap.getWidth() - 4);
        int roomRightSideXCoordinate = roomLeftSideXCoordinate + roomWidth - 1;
        int roomLowerSideYCoordinate = RANDOM.nextInt(dungeonMap.getHeight() - 4);
        int roomUpperSideYCoordinate = roomLowerSideYCoordinate + roomHeight - 1;

        tryToBuildRoom(roomWidth, roomHeight, roomLeftSideXCoordinate, roomRightSideXCoordinate, roomLowerSideYCoordinate, roomUpperSideYCoordinate);
    }

    private void tryToBuildRoom(int roomWidth, int roomHeight, int roomLeftSideXCoordinate, int roomRightSideXCoordinate, int roomLowerSideYCoordinate, int roomUpperSideYCoordinate) {
        Room room = new Room(roomWidth, roomHeight, roomLeftSideXCoordinate, roomRightSideXCoordinate, roomLowerSideYCoordinate, roomUpperSideYCoordinate);
        RoomValidator roomValidator = new RoomValidator(room, dungeonMap);

        if(roomValidator.ableToBuildRoom()) {
            LOGGER.info("building room from " + new Vector3Int(roomRightSideXCoordinate, roomLowerSideYCoordinate, z) + " to " + new Vector3Int(roomRightSideXCoordinate, roomUpperSideYCoordinate, z));
            setAllTilesAsRoom(room, roomValidator);

            setStartingOrEndingRoomIfThereIsNoneYet(room);
            connectRoom(room);

            rooms.add(room);
            nRooms++;
        } else {
            removeUnusedDoors(roomWidth, roomHeight, roomLeftSideXCoordinate, roomLowerSideYCoordinate);
        }
    }



    private void setAllTilesAsRoom(Room room, RoomValidator roomValidator) {
        for (int i = room.getLeftSideXCoordinate(); i <= room.getRightSideXCoordinate(); i++) {
            for (int j = room.getLowerSideYCoordinate(); j <= room.getUpperSideYCoordinate(); j++) {
                setTileAsRoom(i, j, roomValidator);
            }
        }
    }

    private void setTileAsRoom(int i, int j, RoomValidator roomValidator) {
        if (dungeonMap.getMap()[i][j] == TileType.DOOR) {
            LOGGER.info("building room door at " + new Vector3Int(i, j, z));
        } else if (roomValidator.onEdgeOfRoom(i, j)) {
            LOGGER.info("building wall at " + new Vector3Int(i, j, z));
            dungeonMap.getMap()[i][j] = TileType.WALL;
        } else {
            LOGGER.info("building floor at " + new Vector3Int(i, j, z));
            dungeonMap.getMap()[i][j] = TileType.FLOOR;
        }
        dungeonMap.getRoomOrPath()[i][j] = TileType.ROOM;
    }

    private void setStartingOrEndingRoomIfThereIsNoneYet(Room room) {
        if (!dungeonHasEndingRoom)
        {
            room.setEndingRoom(true);

            if (!dungeonHasStartingRoom)
            {
                room.setStartingRoom(true);
            }
        }
    }

    private void connectRoom(Room room) {
        if (nRooms > 0) {
            connectToPreviousRoom(room, rooms.get(nRooms - 1));
        } else {
            setWholeRoomAsConnected(room);
        }
    }

    private void setWholeRoomAsConnected(Room room) {
        for (int i = room.getLeftSideXCoordinate() + 1; i < room.getLeftSideXCoordinate() + room.getWidth() - 1; i++) {
            for (int j = room.getLowerSideYCoordinate() + 1; j < room.getLowerSideYCoordinate() + room.getHeight() - 1; j++) {
                dungeonMap.getPathMap()[i][j] = TileType.CONNECTED;
            }
        }
        room.setConnected(true);
    }

    private void setRegionVariables(int z) {
        region.setTiles(dungeonMap.getMap());
        region.setRegionType(RegionType.DUNGEON);
        region.setRooms(rooms);
        region.setnRooms(nRooms);

        Ladder ladder = new Ladder();
        ladder.setxDown(ladderX);
        ladder.setyDown(ladderY);
        ladder.setZ(z);
        region.setLadder(ladder);
    }

    private void removeUnusedDoors(int width, int height, int x, int y)
    {
        for(int i = x; i < x + width && i < dungeonMap.getWidth(); i++) {
            for (int j = y; j < y + height && j < dungeonMap.getHeight(); j++) {
                if (dungeonMap.getMap()[i][j] == TileType.DOOR && !wallsOnBothSideOfDoor(i, j)) {
                    dungeonMap.getMap()[i][j] = TileType.FLOOR;
                }
            }
        }
    }

    private boolean wallsOnBothSideOfDoor(int i, int j) {
        return (dungeonMap.getMap()[i - 1][j] == TileType.WALL && dungeonMap.getMap()[i + 1][j] == TileType.WALL)
        || (dungeonMap.getMap()[i][j - 1] == TileType.WALL && dungeonMap.getMap()[i][j + 1] == TileType.WALL);
    }

    private void connectToPreviousRoom(Room fromRoom, Room toRoom)
    {
        boolean connected = false;
        Vector3Int fromPos = randomPointInRoom(fromRoom);
        Vector3Int toPos = randomPointInRoom(toRoom);

        for(int i = fromRoom.getLeftSideXCoordinate(); i < fromRoom.getLeftSideXCoordinate() + fromRoom.getWidth(); i++)
            for(int j = fromRoom.getLowerSideYCoordinate(); j < fromRoom.getLowerSideYCoordinate() + fromRoom.getHeight(); j++)
            {
                if (dungeonMap.getPathMap()[i][j].equals(TileType.CONNECTED))
                {
                    connected = true;
                    LOGGER.info("no path needed, room is already connected");
                    break;
                }
            }
        if (!connected) //if not connected yet, find path to connected area
        {
            if (!findPath(fromPos, fromPos, toPos, 0))
            {
                LOGGER.info("No path from " + fromPos + " to " + toPos);
            }
            else //if path can be found, add path to connected area on pathMap
            {
                connected = true;
                for (int i = 0; i < dungeonMap.getWidth(); i++)
                    for (int j = 0; j < dungeonMap.getHeight(); j++)
                    {
                        if (dungeonMap.getPathMap()[i][j].equals(TileType.PATH)) {
                            dungeonMap.getPathMap()[i][j] = TileType.CONNECTED;
                        }
                    }
            }
        }
        if (connected) //if room is connected, add room to connected area on pathMap
        {
            setWholeRoomAsConnected(fromRoom);
        }
        //debug: else: undo room?
    }

    private boolean findPath(Vector3Int previousPos, Vector3Int currentPos, Vector3Int goalPos, int xOrY)
    {
        LOGGER.info("try path from " + currentPos + " to " + goalPos);
        if(currentPos == goalPos)
        {
            LOGGER.info("goal reached");
            return true;
        }
        if (currentPos.getX() < 0 || currentPos.getX() >= dungeonMap.getWidth() || currentPos.getY() < 0 || currentPos.getY() >= dungeonMap.getHeight())
        {
            LOGGER.warning("abort, path cannot go outside of tileMap");
            return false;
        }
        if (dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()].equals(TileType.NO_PATH) || dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()].equals(TileType.PATH))
        {
            LOGGER.warning("abort, this tile has already been tried out");
            return false;
        }
        if (dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] == TileType.WALL
                && dungeonMap.getMap()[previousPos.getX()][previousPos.getY()] == TileType.WALL)
        {
            LOGGER.warning("abort, the path cannot go through two or more consecutive walls");
            return false;
        }
        if (dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] == TileType.WALL
                && dungeonMap.getMap()[previousPos.getX()][previousPos.getY()] == TileType.DOOR
            || dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] == TileType.DOOR
                && dungeonMap.getMap()[previousPos.getX()][previousPos.getY()] == TileType.WALL)
        {
            LOGGER.warning("abort, no two consecutive doors in path allowed");
            return false;
        }
        if (dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()].equals(TileType.CONNECTED))
        {
            LOGGER.info("connected area reached");
            return true;
        }
        if (currentPos.getX() == goalPos.getX() && xOrY == 0)
            xOrY = 1;
        if (currentPos.getY() == goalPos.getY() && xOrY == 1)
            xOrY = 0;

        dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()] = TileType.PATH;

        int xDirection;

        if (goalPos.getX() - currentPos.getX() != 0)
            xDirection = (goalPos.getX() - currentPos.getX()) / Math.abs(goalPos.getX() - currentPos.getX());
        else
            xDirection = 0;

        int yDirection;

        if (goalPos.getY() - currentPos.getY() != 0)
            yDirection = (goalPos.getY() - currentPos.getY()) / Math.abs(goalPos.getY() - currentPos.getY());
        else
            yDirection = 0;

        Vector3Int nextPos;

        if(xOrY == 0)
            nextPos = new Vector3Int(currentPos.getX() + xDirection, currentPos.getY(), z);
        else
            nextPos = new Vector3Int(currentPos.getX(), currentPos.getY() + yDirection, z);

        LOGGER.info("from " + currentPos + " go to " + nextPos);
        if (findPath(currentPos, nextPos, goalPos, xOrY) && nextPos != previousPos) {
            paintPath(currentPos, previousPos);
            return true;
        }

        if (dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] != TileType.WALL) {
            if (xOrY == 0) {
                xOrY = 1;
                if(yDirection == 0) {
                    yDirection = 1;
                }
                nextPos = new Vector3Int(currentPos.getX(), currentPos.getY() + yDirection, z);
            }
            else {
                xOrY = 0;
                if (xDirection == 0)
                    xDirection = 1;
                nextPos = new Vector3Int(currentPos.getX() + xDirection, currentPos.getY(), z);
            }

            LOGGER.info("from " + currentPos + " go to " + nextPos);
            if (findPath(currentPos, nextPos, goalPos, xOrY) && nextPos != previousPos) {
                paintPath(currentPos, previousPos);
                return true;
            }

            if (xOrY == 0)
                nextPos = new Vector3Int(currentPos.getX() - xDirection, currentPos.getY(), z);
            else
                nextPos = new Vector3Int(currentPos.getX(), currentPos.getY() - yDirection, z);

            LOGGER.info("from " + currentPos + " go to " + nextPos);
            if (findPath(currentPos, nextPos, goalPos, xOrY) && nextPos != previousPos) {
                paintPath(currentPos, previousPos);
                return true;
            }
        }

        dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()] = TileType.NO_PATH;
        LOGGER.info("pathMap[" + currentPos.getX() + "," + currentPos.getY() + "] = " + dungeonMap.getPathMap()[currentPos.getX()][currentPos.getY()]);
        LOGGER.warning("abort, every direction from " + currentPos + " is impossible");
        return false;
    }

    private void paintPath(Vector3Int currentPos, Vector3Int previousPos) {
        if (dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] == TileType.WALL) {
            LOGGER.warning("currentPosition " + currentPos + " is a wall tile, will be turned into door");
            if (currentPos.getX() == previousPos.getX() || currentPos.getY() == previousPos.getY()) {
                dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] = TileType.DOOR;
            }
        }
        if (dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] == TileType.EMPTY) {
            LOGGER.warning("currentPosition " + currentPos + " is empty, will be turned into floor");
            dungeonMap.getMap()[currentPos.getX()][currentPos.getY()] = TileType.FLOOR;
        }
    }

    private Vector3Int randomPointInRoom(Room r) {
        int randomX = RANDOM.nextInt(r.getLeftSideXCoordinate() + r.getWidth() - r.getLeftSideXCoordinate()) + r.getLeftSideXCoordinate() + 1;
        int randomY = RANDOM.nextInt(r.getLowerSideYCoordinate() + r.getHeight()- r.getLowerSideYCoordinate()) + r.getLowerSideYCoordinate() + 1;

        return new Vector3Int(randomX, randomY, z);
    }
}