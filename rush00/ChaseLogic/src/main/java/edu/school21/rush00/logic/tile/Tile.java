package edu.school21.rush00.logic.tile;

public class Tile {
    private int inOpen;
    private int inClosed;
    private TileType state;
    private int parentX;
    private int parentY;
    private int heapIndex;
    private int gScore;
    private int hScore;
    private int fScore;
    private int xPos;
    private int yPos;

    public int getInOpen() {
        return inOpen;
    }

    public void setInOpen(int inOpen) {
        this.inOpen = inOpen;
    }

    public int getInClosed() {
        return inClosed;
    }

    public void setInClosed(int inClosed) {
        this.inClosed = inClosed;
    }

    public TileType getState() {
        return state;
    }

    public void setState(TileType state) {
        this.state = state;
    }

    public int getParentX() {
        return parentX;
    }

    public void setParentX(int parentX) {
        this.parentX = parentX;
    }

    public int getParentY() {
        return parentY;
    }

    public void setParentY(int parentY) {
        this.parentY = parentY;
    }

    public int getHeapIndex() {
        return heapIndex;
    }

    public void setHeapIndex(int heapIndex) {
        this.heapIndex = heapIndex;
    }

    public int getgScore() {
        return gScore;
    }

    public void setgScore(int gScore) {
        this.gScore = gScore;
    }

    public int gethScore() {
        return hScore;
    }

    public void sethScore(int hScore) {
        this.hScore = hScore;
    }

    public int getfScore() {
        return fScore;
    }

    public void setfScore(int fScore) {
        this.fScore = fScore;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public Tile(int inOpen, int inClosed, TileType tileType,
                int xPos, int yPos) {
        this.inClosed = inClosed;
        this.inOpen = inOpen;
        this.state = tileType;
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
