package calculator.core.fibonacci;

import calculator.core.LRUCache;
import calculator.utils.NumberUtils;

import java.util.function.Function;

 /**
   * Calculates the number of the Fibonacci sequence that can be found at the <code>n</code>th position of the sequence.
   *
   * @param calculateMemoized allows the class to use cache for the calculations.
   * @return the value found at the <code>n</code>th position of the Fibonacci sequence.
   * @throws IllegalArgumentException if <code>n</code> is out of bounds
   * @see <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci</a>
   */
public class FibonacciCalculator {
    private final Function<Integer, Integer> calculateMemoized;

    public FibonacciCalculator(final LRUCache<Integer, Integer> cache) {
        calculateMemoized = cache.memoize(this::calculateInternal);
    }

    public int calculate(final String position) {
        final int n = NumberUtils.parse(position);
        return calculateMemoized.apply(n);
    }

    int calculateInternal(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n should be a non-negative number");
        }
        if (n <= 1) {
            return n;
        }
        return calculateInternal(n - 1) + calculateInternal(n - 2);
    }
}
