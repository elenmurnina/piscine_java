import java.util.Scanner;

public class Program {
    private static final int MAX_STUDENT_COUNT = 10;
    private static final int MAX_CLASS_COUNT = 10;
    private static final String[] WEEK_DAYS = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};
    private static final int MONTH_DAY_COUNT = 30;
    private static final int MAX_HOUR = 6;
    private static final int MAX_COLUMN_COUNT = MAX_HOUR * MONTH_DAY_COUNT;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] students = new String[MAX_STUDENT_COUNT];
        int studentCount = 0;
        while (true) {
            String student = scanner.nextLine();
            if (student.equals(".")) {
                break;
            }
            students[studentCount] = student;
            studentCount++;
        }

        int[] classHours = new int[MAX_CLASS_COUNT];
        String[] classDays = new String[MAX_CLASS_COUNT];
        int classCount = 0;
        while (true) {
            String hourAndDay = scanner.nextLine();
            if (hourAndDay.equals(".")) {
                break;
            }
            int hour = hourAndDay.charAt(0) - '0';
            String day = hourAndDay.substring(2);
            classHours[classCount] = hour;
            classDays[classCount] = day;
            classCount++;
        }

        String[] columnNames = new String[MAX_COLUMN_COUNT];
        int[] columnHours = new int[MAX_COLUMN_COUNT];
        String[] columnWeekDays = new String[MAX_COLUMN_COUNT];
        int[] columnDays = new int[MAX_COLUMN_COUNT];

        int columnCount = 0;
        int dayOfWeek = 1;
        for (int i = 1; i <= MONTH_DAY_COUNT; i++) {
            String day = WEEK_DAYS[dayOfWeek];
            for (int hour = 1; hour <= MAX_HOUR; hour++) {
                boolean has = false;
                for (int k = 0; k < classCount; k++) {
                    if (classDays[k].equals(day) && classHours[k] == hour) {
                        has = true;
                        break;
                    }
                }
                if (has) {
                    String columnName = hour + ":00 " + day + " ";
                    if (i <= 9) {
                        columnName += " ";
                    }
                    columnName += ("" + i);
                    columnNames[columnCount] = columnName;
                    columnHours[columnCount] = hour;
                    columnWeekDays[columnCount] = day;
                    columnDays[columnCount] = i;
                    columnCount++;
                }
            }
            dayOfWeek = (dayOfWeek + 1) % 7;
        }
        int[][] cells = new int[studentCount][columnCount];
        while (true) {
            String student = scanner.next();
            if (student.equals(".")) {
                break;
            }
            int hour = scanner.nextInt();
            int day = scanner.nextInt();
            String here = scanner.nextLine();
            int row = -1;
            for (int i = 0; i < studentCount; i++) {
                if (students[i].equals(student)) {
                    row = i;
                    break;
                }
            }
            int col = -1;
            for (int i = 0; i < columnCount; i++) {
                if (columnHours[i] == hour && columnDays[i] == day) {
                    col = i;
                    break;
                }
            }
            if (here.equals(" HERE")) {
                cells[row][col]++;
            } else {
                cells[row][col]--;
            }
        }
        System.out.print("          ");
        for (int i = 0; i < columnCount; i++) {
            System.out.print(columnNames[i] + "|");
        }
        System.out.println();
        for (int i = 0; i < studentCount; i++) {
            for (int j = 0; j < 10 - students[i].length(); j++) {
                System.out.print(" ");
            }
            System.out.print(students[i]);
            for (int j = 0; j < columnCount; j++) {
                if (cells[i][j] == 0) {
                    for (int k = 0; k < columnNames[j].length(); k++) {
                        System.out.print(" ");
                    }
                } else if (cells[i][j] > 0) {
                    for (int k = 0; k < columnNames[j].length() - 1; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(cells[i][j]);
                } else {
                    for (int k = 0; k < columnNames[j].length() - 2; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(cells[i][j]);
                }
                System.out.print("|");
            }
            System.out.println();
        }
    }
}
