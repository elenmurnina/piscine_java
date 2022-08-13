import java.util.Random;

public class Program {

    private static final String ARRAY_SIZE_PREFIX = "--arraySize=";
    private static final String THREADS_COUNT_PREFIX = "--threadsCount=";
    private static final int MAX_VALUE_ARRAY = 1000;

    public static void main(String[] args) {
        if (args.length != 2 || !args[0].startsWith(ARRAY_SIZE_PREFIX) || !args[1].startsWith(THREADS_COUNT_PREFIX)) {
            error();
        }

        int arreySize = 0;
        int threadCount = 0;
        try {
            arreySize = Integer.parseInt(args[0].substring(ARRAY_SIZE_PREFIX.length()));
            threadCount = Integer.parseInt(args[1].substring(THREADS_COUNT_PREFIX.length()));
        } catch (NumberFormatException ignored) {
            error();
        }
        if (arreySize < 0 || threadCount < 0 || arreySize > 2000000 || threadCount > 2000000) {
            error();
        }

        long sum = 0;
        int[] arr = new int[arreySize];
        Random random = new Random();
        for (int i = 0; i < arreySize; i++) {
            arr[i] = random.nextInt(-MAX_VALUE_ARRAY, MAX_VALUE_ARRAY);
            sum += arr[i];
        }
        System.out.println("Sum: " + sum);
        if (arreySize < threadCount) {
            System.out.println("The array size is less than the number of threads, " +
                    "it is impossible to perform correct calculations.");
            System.exit(0);
        }

        int splitter = 1;
        if (threadCount > 0) {
            splitter = arreySize / threadCount;
        }
        int[] resultArr = new int[threadCount];
        int j = 0;
        long sumThread = 0;
        for (int i = 0; i < threadCount; i++) {
            if (i < threadCount - 1) {
                new ThreadCalculator(arr, resultArr, i, j, j + splitter).run();
                j += splitter;
            } else {
                new ThreadCalculator(arr, resultArr, i, j, arreySize).run();
            }
            sumThread += resultArr[i];
        }
        System.out.println("Sum by threads: " + sumThread);
    }

    private static void error() {
        System.err.println("""
                Expected two arguments like --arraySize=n --threadsCount=m,
                where n and m is integers between 0 and 2000000""");
        System.exit(-1);
    }
}