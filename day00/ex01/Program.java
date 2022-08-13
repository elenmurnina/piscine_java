import java.util.Scanner;

public class Program {

    private static final String ERROR = "IllegalArgument";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 1) {
            System.err.println(ERROR);
            System.exit(-1);
        } else {
            int stepCount = 1;
            boolean prime = true;

            for (int i = 2; i * i <= n; i++) {
                if (n % i == 0) {
                    prime = false;
                    break;
                }
                stepCount++;
            }
            System.out.println(prime + " " + stepCount);
        }
    }
}
