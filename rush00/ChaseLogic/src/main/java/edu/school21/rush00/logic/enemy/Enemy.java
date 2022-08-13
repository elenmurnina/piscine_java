package edu.school21.rush00.logic.enemy;

import edu.school21.rush00.logic.map.Map;
import edu.school21.rush00.logic.minheap.MinHeap;
import edu.school21.rush00.logic.pathfinding.PathFinding;
import edu.school21.rush00.logic.tile.Tile;
import edu.school21.rush00.logic.tile.TileType;

public class Enemy {
    private MinHeap minHeap;

    public Tile getCurrTile() {
        return currTile;
    }

    public void setCurrTile(Tile currTile) {
        this.currTile = currTile;
    }

    public Tile getTarget() {
        return target;
    }

    public void setTarget(Tile target) {
        this.target = target;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public void setMovesCount(int movesCount) {
        this.movesCount = movesCount;
    }

    private Tile currTile;
    private Tile target;
    private int movesCount;

    public Enemy(Tile currTile, int mapSize) {
        this.currTile = currTile;
        this.target = null;
        this.movesCount = 0;
        minHeap = new MinHeap(mapSize * mapSize);
    }

    private boolean enemyPathfinding(Map map) {
        return (PathFinding.pathFinding(map, currTile, target, minHeap));
    }

    public boolean enemyMove(Map map) {
        if (enemyPathfinding(map)) {
            int currX = target.getxPos();
            int currY = target.getyPos();
            Tile currentTile = target;
            if (currX != currTile.getxPos() || currY != currTile.getyPos()) {
                while (currentTile.getParentX() != currTile.getxPos()
                        || currentTile.getParentY() != currTile.getyPos()) {
                    currX = currentTile.getParentX();
                    currY = currentTile.getParentY();
                    currentTile = map.getTilesArr()[currY][currX];
                }
                if (currX == map.getPlayerX() && currY == map.getPlayerY()) {
                    System.out.println("You lost!");
                    return true;
                }
                map.getTilesArr()[currY][currX].setState(TileType.ENEMY);
                currTile.setState(TileType.EMPTY);
                currTile = map.getTilesArr()[currY][currX];
            }
            int mapWidth = map.getMapWidth();
            minHeap = new MinHeap(mapWidth * mapWidth);
        }
        return false;
    }
}
