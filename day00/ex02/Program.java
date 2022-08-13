import java.util.Scanner;

public class Program {

    private static final int EOF = 42;
    private static final String RESPONSE_PREFIX = "Count of coffee-request - ";

    private static int findDigitSum(int n) {
        int digitSum = 0;
        while (n != 0) {
            digitSum += n % 10;
            n /= 10;
        }
        return digitSum;
    }

    private static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int coffeeRequestCount = 0;
        while (true) {
            int n = scanner.nextInt();
            if (n == EOF) {
                break;
            }

            int digitSum = findDigitSum(n);
            if (isPrime(digitSum)) {
                coffeeRequestCount++;
            }
        }

        System.out.println(RESPONSE_PREFIX + coffeeRequestCount);
    }
}
