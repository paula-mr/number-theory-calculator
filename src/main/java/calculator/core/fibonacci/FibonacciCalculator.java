package calculator.core.fibonacci;

import calculator.core.LRUCache;

import java.util.function.Function;

public class FibonacciCalculator {
    private final Function<Integer, Integer> calculateMemoized;

    public FibonacciCalculator(final LRUCache<Integer, Integer> cache) {
        calculateMemoized = cache.memoize(this::calculateInternal);
    }

    public int calculate(final String position) {
        final int n = parse(position);
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

    private int parse(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("the supplied string \"" + s + "\" is not a valid number");
        }
    }
}
