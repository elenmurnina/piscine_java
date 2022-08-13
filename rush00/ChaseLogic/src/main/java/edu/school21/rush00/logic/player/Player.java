package edu.school21.rush00.logic.player;

import edu.school21.rush00.logic.enemy.Enemy;
import edu.school21.rush00.logic.map.Map;
import edu.school21.rush00.logic.minheap.MinHeap;
import edu.school21.rush00.logic.pathfinding.PathFinding;
import edu.school21.rush00.logic.tile.Tile;
import edu.school21.rush00.logic.tile.TileType;

public class Player {
    public static boolean testReachability(Map map) {
        int currX = map.getPlayerX();
        int currY = map.getPlayerY();
        int exitX = map.getExitX(), exitY = map.getExitY();
        int mapSize = map.getMapHeight();
        Tile currPlayerTile = map.getTilesArr()[currY][currX];
        Tile exitTile = map.getTilesArr()[exitY][exitX];
        return PathFinding.pathFinding(map, currPlayerTile, exitTile,
                new MinHeap(mapSize * mapSize));
    }

    public static boolean playerMove(Map map, int key) {
        int currX = map.getPlayerX(), currY = map.getPlayerY();
        Tile currPlayerTile = map.getTilesArr()[currY][currX];
        Tile candTile = null;
        if (key == 1) {
            if (currY - 1 >= 0) {
                candTile = map.getTilesArr()[currY - 1][currX];
            }
        } else if (key == 2) {
            if (currX - 1 >= 0) {
                candTile = map.getTilesArr()[currY][currX - 1];
            }
        } else if (key == 3) {
            if (currY + 1 < map.getMapHeight()) {
                candTile = map.getTilesArr()[currY + 1][currX];
            }
        } else if (key == 4) {
            if (currX + 1 < map.getMapWidth()) {
                candTile = map.getTilesArr()[currY][currX + 1];
            }
        }
        if (candTile == null) {
            System.out.println("Obstacle!");
            return false;
        }
        if (candTile.getState() == TileType.EMPTY) {
            currPlayerTile.setState(TileType.EMPTY);
            candTile.setState(TileType.PLAYER);
            map.setPlayerX(candTile.getxPos());
            map.setPlayerY(candTile.getyPos());
            for (Enemy currEnemy : map.getEnemiesArr()) {
                currEnemy.setTarget(candTile);
            }
        } else if (candTile.getState() == TileType.EXIT) {
            currPlayerTile.setState(TileType.EMPTY);
            candTile.setState(TileType.PLAYER);
            map.setPlayerX(candTile.getxPos());
            map.setPlayerY(candTile.getyPos());
            for (Enemy currEnemy : map.getEnemiesArr()) {
                currEnemy.setTarget(candTile);
            }
            System.out.println("You won!");
            return true;
        } else if (candTile.getState() == TileType.ENEMY) {
            currPlayerTile.setState(TileType.EMPTY);
            candTile.setState(TileType.PLAYER);
            map.setPlayerX(candTile.getxPos());
            map.setPlayerY(candTile.getyPos());
            for (Enemy currEnemy : map.getEnemiesArr()) {
                currEnemy.setTarget(candTile);
            }
            System.out.println("You lose!");
            return true;
        } else {
            System.out.println("Obstacle!");
        }
        return false;
    }
}