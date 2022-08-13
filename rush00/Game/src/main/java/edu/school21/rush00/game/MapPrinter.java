package edu.school21.rush00.game;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.rush00.logic.tile.Tile;
import edu.school21.rush00.logic.tile.TileType;

public class MapPrinter {
    private String enemyColor;
    private String playerColor;
    private String wallColor;
    private String goalColor;
    private String emptyColor;
    private char enemyChar;
    private char playerChar;
    private char wallChar;
    private char goalChar;
    private char emptyChar;

    MapPrinter(String enemyColor, String playerColor, String wallColor, String goalColor, String emptyColor,
              char enemyChar, char playerChar, char wallChar, char goalChar, char emptyChar) {
        this.enemyColor = enemyColor;
        this.playerColor = playerColor;
        this.wallColor = wallColor;
        this.goalColor = goalColor;
        this.emptyColor = emptyColor;
        this.enemyChar = enemyChar;
        this.playerChar = playerChar;
        this.wallChar = wallChar;
        this.goalChar = goalChar;
        this.emptyChar = emptyChar;
    }

    private Ansi.BColor toBColor(String color) {
        return Ansi.BColor.valueOf(color);
    }

    private Ansi.FColor toFColor(String color) {
        return Ansi.FColor.valueOf(color);
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printMap(int size, String profile, Tile[][] mapWithEnemies) {
        if (!profile.equals("dev")) {
            clearScreen();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mapWithEnemies[i][j].getState() == TileType.EMPTY) {
                    ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                            .foreground(toFColor(emptyColor)).background(toBColor(emptyColor))
                            .build();
                    cp.print(emptyChar);
                }
                if (mapWithEnemies[i][j].getState() == TileType.WALL) {
                    ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                            .foreground(Ansi.FColor.BLACK).background(toBColor(wallColor))
                            .build();
                    cp.print(wallChar);
                }
                if (mapWithEnemies[i][j].getState() == TileType.PLAYER) {
                    ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                            .foreground(Ansi.FColor.BLACK).background(toBColor(playerColor))
                            .build();
                    cp.print(playerChar);
                }
                if (mapWithEnemies[i][j].getState() == TileType.ENEMY) {
                    ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                            .foreground(Ansi.FColor.BLACK).background(toBColor(enemyColor))
                            .build();
                    cp.print(enemyChar);
                }
                if (mapWithEnemies[i][j].getState() == TileType.EXIT) {
                    ColoredPrinter cp = new ColoredPrinter.Builder(1, false)
                            .foreground(Ansi.FColor.BLACK).background(toBColor(goalColor))
                            .build();
                    cp.print(goalChar);
                }
            }
            System.out.print("\n");
        }
    }
}
