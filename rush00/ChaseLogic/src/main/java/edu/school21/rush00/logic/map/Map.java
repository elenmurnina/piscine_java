package edu.school21.rush00.logic.map;

import edu.school21.rush00.logic.enemy.Enemy;
import edu.school21.rush00.logic.tile.Tile;
import edu.school21.rush00.logic.tile.TileType;

public class Map {
    private final Tile[][] tilesArr;
    private final int mapHeight;
    private final int mapWidth;
    private int playerX;
    private int playerY;

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    private int exitX;
    private int exitY;
    private int aStarNum;

    private final Enemy[] enemiesArr;
    public Map(Tile[][] tilesArr, int mapHeight,
               int mapWidth, int playerX, int playerY,
               int exitX, int exitY,
               Enemy[] enemiesArr) {
        this.tilesArr = tilesArr;
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.playerX = playerX;
        this.playerY = playerY;
        this.exitX = exitX;
        this.exitY = exitY;
        this.aStarNum = 1;
        this.enemiesArr = enemiesArr;
    }

    public static Map instantiateMap(char[][] map, int size, int numEnemies,
                                     char enemyChar, char playerChar, char wallChar,
                                     char emptyChar) {
        Tile[][] tilesArr = new Tile[size][size];
        Enemy[] enemiesArr = new Enemy[numEnemies];
        int enemiesCounter = 0;
        int playerX = 0;
        int playerY = 0;
        int exitX = 0, exitY = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileType tileType;
                char currMark = map[i][j];
                if (currMark == enemyChar) {
                    Tile currTile = new Tile(0, 0, TileType.ENEMY, j, i);
                    tilesArr[i][j] = currTile;
                    enemiesArr[enemiesCounter] = new Enemy(currTile, size);
                    enemiesCounter++;
                    continue;
                }
                else if (currMark == playerChar) {
                    playerX = j;
                    playerY = i;
                    tileType = TileType.PLAYER;
                }
                else if (currMark == emptyChar)
                    tileType = TileType.EMPTY;
                else if (currMark == wallChar)
                    tileType = TileType.WALL;
                else {
                    exitY = i;
                    exitX = j;
                    tileType = TileType.EXIT;
                }
                tilesArr[i][j] = new Tile(0, 0, tileType, j, i);
            }
        }
        for (Enemy currEnemy : enemiesArr) {
            currEnemy.setTarget(tilesArr[playerY][playerX]);
        }
        return new Map(tilesArr, size, size, playerX,
                playerY, exitX, exitY, enemiesArr);
    }

    public Tile[][] getTilesArr() {
        return tilesArr;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getaStarNum() {
        return aStarNum;
    }

    public void setaStarNum(int aStarNum) {
        this.aStarNum = aStarNum;
    }

    public Enemy[] getEnemiesArr() {
        return enemiesArr;
    }
}
