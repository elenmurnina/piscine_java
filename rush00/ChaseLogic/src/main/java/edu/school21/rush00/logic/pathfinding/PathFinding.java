package edu.school21.rush00.logic.pathfinding;

import edu.school21.rush00.logic.map.Map;
import edu.school21.rush00.logic.minheap.MinHeap;
import edu.school21.rush00.logic.tile.Tile;
import edu.school21.rush00.logic.tile.TileType;

public class PathFinding {

    private static boolean newPathBetter(Tile currTile, Tile candParent,
                                  Tile target) {
        int newCost = candParent.getgScore() + Heuristic.MOVE_COST +
                Heuristic.heuristic(currTile, target);
        return newCost < currTile.getfScore();
    }
    private static void checkNeighbor(Tile neighbor, Map map,
                                      Tile candParent, Tile target,
                                      MinHeap minHeap) {
        TileType currNeighborState = neighbor.getState();
        if (currNeighborState == TileType.WALL || currNeighborState
            == TileType.ENEMY || currNeighborState == TileType.EXIT ||
            neighbor.getInClosed() == map.getaStarNum())
            return;
        if (neighbor.getInOpen() != map.getaStarNum()
            || newPathBetter(neighbor, candParent, target)) {
            neighbor.sethScore(Heuristic.heuristic(neighbor, target));
            neighbor.setgScore(Heuristic.MOVE_COST + candParent.getgScore());
            neighbor.setParentX(candParent.getxPos());
            neighbor.setParentY(candParent.getyPos());
            if (neighbor.getInOpen() != map.getaStarNum()) {
                neighbor.setInOpen(map.getaStarNum());
                neighbor.setfScore(neighbor.gethScore() + neighbor.getgScore());
                minHeap.insertKey(neighbor);
            } else {
                minHeap.decreaseKey(neighbor.getHeapIndex(), neighbor.gethScore()
                    + neighbor.getgScore());
            }
        }
    }
    public static boolean pathFinding(Map map, Tile source,
                                  Tile target, MinHeap minHeap) {
        Tile current;
        source.setgScore(0);
        source.sethScore(Heuristic.heuristic(source, target));
        source.setfScore(source.gethScore());
        minHeap.insertKey(source);
        while (true) {
            if (minHeap.getHeapSize() > 0) {
                current = minHeap.extractMin();
            } else {
                map.setaStarNum(map.getaStarNum() + 1);
                return (false);
            }
            current.setInOpen(map.getaStarNum() - 1);
            current.setInClosed(map.getaStarNum());
            if (current.getxPos() == target.getxPos()
            && current.getyPos() == target.getyPos())
                break;
            int currX = current.getxPos();
            int currY = current.getyPos();
            if (currX - 1 >= 0)
                checkNeighbor(map.getTilesArr()[currY][currX - 1],
                        map, current, target, minHeap);
            if (currX + 1 < map.getMapWidth())
                checkNeighbor(map.getTilesArr()[currY][currX + 1],
                        map, current, target, minHeap);
            if (currY - 1 >= 0)
                checkNeighbor(map.getTilesArr()[currY - 1][currX],
                        map, current, target, minHeap);
            if (currY + 1 < map.getMapHeight())
                checkNeighbor(map.getTilesArr()[currY + 1][currX],
                    map, current, target, minHeap);
        }
        map.setaStarNum(map.getaStarNum() + 1);
        return (true);
    }
}
