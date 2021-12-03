package calculator.core.fibonacci;

import calculator.core.LRUCache;
import calculator.utils.NumberUtils;

import java.util.function.Function;

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
