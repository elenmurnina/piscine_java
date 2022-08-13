import java.util.Scanner;

public class Program {

    private static final String EOF = "42";
    private static final String WEEK_PREFIX = "Week ";
    private static final String ERROR = "IllegalArgument";
    private static final int MAX_WEEK_COUNT = 18;
    private static final int TEST_COUNT = 5;
    private static final int MIN_PROGRESS = 1;
    private static final int MAX_PROGRESS = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int weekCount = 0;
        long grades = 0;
        boolean eof = false;

        while (weekCount <= MAX_WEEK_COUNT) {
            String weekName = scanner.nextLine();
            if (weekName.equals(EOF)) {
                eof = true;
                break;
            }
            if (!weekName.equals(WEEK_PREFIX + (weekCount + 1))) {
                System.err.println(ERROR);
                System.exit(-1);
            }

            int minProgress = MAX_PROGRESS;
            for (int i = 0; i < TEST_COUNT; i++) {
                int progress = scanner.nextInt();
                if (progress > MAX_PROGRESS || progress < MIN_PROGRESS) {
                    System.err.println(ERROR);
                    System.exit(-1);
                }
                if (progress < minProgress) {
                    minProgress = progress;
                }
            }
            grades = grades * 10 + minProgress;
            scanner.nextLine();
            weekCount++;
        }

        if (!eof) {
            System.err.println(ERROR);
            System.exit(-1);
        }

        for (int i = 1; i <= weekCount; i++) {
            System.out.print(WEEK_PREFIX + i + " ");
            long progress = grades;
            for (int j = 0; j < weekCount - i; j++) {
                progress /= 10;
            }
            progress %= 10;
            for (int j = 0; j < progress; j++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }
}
