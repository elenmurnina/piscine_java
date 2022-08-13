package edu.school21.numbers;

import static java.lang.Math.abs;

public class NumberWorker {

    public int digitsSum(int n) {
        int digitsSum = 0;
        while (n != 0) {
            digitsSum += n % 10;
            n /= 10;
        }
        return abs(digitsSum);
    }

    public boolean isPrime(int n) {
        if (n < 2) {
            throw new IllegalNumberException("Expected n >= 2 but found n = " + n);
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
