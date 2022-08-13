import java.util.Scanner;

public class Program {

    private static final int MAX_SHARP_COUNT = 10;
    private static final int MAX_COLUMN_COUNT = 10;
    private static final int MAX_CHAR = 65535;

    private static int findHeight(int[] charCounts) {
        int height = 0;
        for (int count : charCounts) {
            if (count > height) {
                height = count;
            }
        }
        if (height > MAX_SHARP_COUNT) {
            height = MAX_SHARP_COUNT;
        }
        height += 2;
        return height;
    }

    private static int findWidth(int columnCount, int[] charCounts) {
        int width = 0;
        for (int i = 0; i < columnCount; i++) {
            width += findColumnWidth(charCounts[i]);
        }
        return width;
    }

    private static int findColumnWidth(int count) {
        int countWidth = 0;
        while (count != 0) {
            countWidth++;
            count /= 10;
        }
        return countWidth + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] s = scanner.nextLine().toCharArray();
        int[] counts = new int[MAX_CHAR + 1];
        for (char c : s) {
            counts[c]++;
        }
        char[] chars = new char[MAX_COLUMN_COUNT];
        int[] charCounts = new int[MAX_COLUMN_COUNT];
        int columnCount = 0;

        for (int i = 0; i < MAX_COLUMN_COUNT; i++) {
            int maxCount = 0;
            char maxCountChar = 0;
            for (int j = MAX_CHAR; j >= 0; j--) {
                if (counts[j] >= maxCount) {
                    maxCount = counts[j];
                    maxCountChar = (char) j;
                }
            }
            if (maxCount == 0) {
                break;
            }
            chars[i] = maxCountChar;
            charCounts[i] = maxCount;
            counts[maxCountChar] = 0;
            columnCount++;
        }
        int height = findHeight(charCounts);
        int width = findWidth(columnCount, charCounts);
        char[][] field = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                field[i][j] = ' ';
            }
        }
        int col = 0;
        for (int i = 0; i < columnCount; i++) {
            col += findColumnWidth(charCounts[i]) - 1;
            int columnHeight = charCounts[i] * (height - 2) / charCounts[0];
            int row = height - 2;
            for (int j = 0; j < columnHeight; j++) {
                field[row][col] = '#';
                row--;
            }
            int count = charCounts[i];
            int digitCol = col;
            while (count != 0) {
                field[row][digitCol] = (char) ('0' + count % 10);
                count /= 10;
                digitCol--;
            }
            field[height - 1][col] = chars[i];
            col++;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
