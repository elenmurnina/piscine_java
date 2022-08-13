import java.util.Arrays;
import java.util.List;

public class ThreadCalculator {
    private final int[] arr;
    private final int[] resultArr;
    private final int threadIndex;
    private final int startIndex;
    private final int endIndex;

    public ThreadCalculator(int[] arr, int[] resultArr, int threadIndex, int startIndex, int endIndex) {
        this.arr = arr;
        this.resultArr = resultArr;
        this.threadIndex = threadIndex;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public void run() {
        long sum = 0;

        for (int i = startIndex; i < endIndex; i++) {
            sum += arr[i];
        }
        resultArr[threadIndex] = (int) sum;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Thread " + (threadIndex + 1) +
                ": from " + startIndex +
                " to " + (endIndex - 1) +
                " sum is " + resultArr[threadIndex];
    }
}