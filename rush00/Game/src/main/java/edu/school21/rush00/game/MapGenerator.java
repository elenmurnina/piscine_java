package edu.school21.rush00.game;

import edu.school21.rush00.logic.map.Map;
import edu.school21.rush00.logic.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private static final char ENEMY_CHAR = 'X';
    private static final char PLAYER_CHAR = 'o';
    private static final char WALL_CHAR = '#';
    private static final char GOAL_CHAR = 'O';
    private static final char EMPTY_CHAR = ' ';

    public static Map createMap(int enemiesCount, int wallsCount, int size,
                                char enemyChar, char playerChar, char wallChar,
                                char emptyChar) {
        char[][] map = new char[size][size];
        Map mapWithEnemies;

        Random random = new Random();
        while (true) {
            List<Cell> cells = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Cell cell = new Cell();
                    cell.setRow(i);
                    cell.setCol(j);
                    cells.add(cell);
                    map[i][j] = EMPTY_CHAR;
                }
            }
            Collections.shuffle(cells, random);
            map[cells.get(0).getRow()][cells.get(0).getCol()] = PLAYER_CHAR;
            map[cells.get(1).getRow()][cells.get(1).getCol()] = GOAL_CHAR;
            for (int i = 0; i < wallsCount; i++) {
                map[cells.get(i + 2).getRow()][cells.get(i + 2).getCol()] = WALL_CHAR;
            }
            for (int i = 0; i < enemiesCount; i++) {
                map[cells.get(i + 2 + wallsCount).getRow()][cells.get(i + 2 + wallsCount).getCol()] = ENEMY_CHAR;
            }
            mapWithEnemies = Map.instantiateMap(map, size, enemiesCount, enemyChar,
                    playerChar, wallChar, emptyChar);
            break;
        }
        System.out.println();
        return mapWithEnemies;
    }

    private static boolean isValid(Map map) {
        return Player.testReachability(map);
    }
}
