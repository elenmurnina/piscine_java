package edu.school21.numbers;

import edu.school21.numbers.IllegalNumberException;
import edu.school21.numbers.NumberWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    private final NumberWorker numberWorker = new NumberWorker();

    @Test
    public void testOne() {
        Assertions.assertFalse(numberWorker.isPrime(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 999331, 1645333507})
    public void isPrimeForPrimes(int n) {
        Assertions.assertTrue(numberWorker.isPrime(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 10000011, 487 * 487, 857 * 859})
    public void isPrimeForNotPrimes(int n) {
        Assertions.assertFalse(numberWorker.isPrime(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {-4, 0, 1, -9, -10000011, -487 * 487, Integer.MIN_VALUE})
    public void isPrimeForIncorrectNumbers(int n) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(n));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void digitSum(int n, int expected) {
        Assertions.assertEquals(expected, numberWorker.digitsSum(n));
    }
}
