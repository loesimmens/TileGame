package tilegame.worlds.regions.dungeons;

public class Room {
    private int width;
    private int height;

    private int leftSideXCoordinate;
    private int rightSideXCoordinate;
    private int lowerSideYCoordinate;
    private int upperSideYCoordinate;

    private boolean startingRoom;
    private boolean endingRoom;
    private boolean connected;

    public Room(int width, int height, int leftSideXCoordinate, int rightSideXCoordinate, int lowerSideYCoordinate, int upperSideYCoordinate) {
        this.width = width;
        this.height = height;
        this.leftSideXCoordinate = leftSideXCoordinate;
        this.rightSideXCoordinate = rightSideXCoordinate;
        this.lowerSideYCoordinate = lowerSideYCoordinate;
        this.upperSideYCoordinate = upperSideYCoordinate;
        this.startingRoom = false;
        this.endingRoom = false;
        this.connected = false;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLeftSideXCoordinate() {
        return leftSideXCoordinate;
    }

    public void setLeftSideXCoordinate(int leftSideXCoordinate) {
        this.leftSideXCoordinate = leftSideXCoordinate;
    }

    public int getRightSideXCoordinate() {
        return rightSideXCoordinate;
    }

    public void setRightSideXCoordinate(int rightSideXCoordinate) {
        this.rightSideXCoordinate = rightSideXCoordinate;
    }

    public int getLowerSideYCoordinate() {
        return lowerSideYCoordinate;
    }

    public void setLowerSideYCoordinate(int lowerSideYCoordinate) {
        this.lowerSideYCoordinate = lowerSideYCoordinate;
    }

    public int getUpperSideYCoordinate() {
        return upperSideYCoordinate;
    }

    public void setUpperSideYCoordinate(int upperSideYCoordinate) {
        this.upperSideYCoordinate = upperSideYCoordinate;
    }

    public boolean isStartingRoom() {
        return startingRoom;
    }

    public void setStartingRoom(boolean startingRoom) {
        this.startingRoom = startingRoom;
    }

    public boolean isEndingRoom() {
        return endingRoom;
    }

    public void setEndingRoom(boolean endingRoom) {
        this.endingRoom = endingRoom;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
