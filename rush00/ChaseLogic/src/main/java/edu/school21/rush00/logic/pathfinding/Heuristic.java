package edu.school21.rush00.logic.pathfinding;

import edu.school21.rush00.logic.tile.Tile;

import static java.lang.Math.abs;

public class Heuristic {

    public static final int MOVE_COST = 10;

    public static int heuristic(Tile source, Tile target) {
        int source_x = source.getxPos();
        int source_y = source.getyPos();
        int target_x = target.getxPos();
        int target_y = target.getyPos();
        return (abs(source_x - target_x) * MOVE_COST
            + abs(source_y - target_y) * MOVE_COST);
    }

}
