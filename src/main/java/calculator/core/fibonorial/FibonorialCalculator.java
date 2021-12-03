package calculator.core.fibonorial;

import calculator.core.LRUCache;
import calculator.core.fibonacci.FibonacciCalculator;
import calculator.utils.NumberUtils;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FibonorialCalculator {

  private final Function<Integer, Integer> calculateMemoized;
  private final FibonacciCalculator fibonacciCalculator;

  public FibonorialCalculator(final LRUCache<Integer, Integer> cache) {
    calculateMemoized = cache.memoize(this::calculateInternal);
    fibonacciCalculator = new FibonacciCalculator(cache);
  }

  /**
   * Calculates the Fibonorial number for a given position. Uses a LRU cache for caching.
   *
   * @param params the <code>position</code> in the fibonorial sequence.
   * @return the number <code>n</code> that corresponds to the <code>position</code> in the 
   *    fibonorial sequence..
   * @throws IllegalArgumentException if <code>position</code> isn't a valid number or if it is
        negative
   * @see <a href="https://en.wikipedia.org/wiki/Fibonorial">Fibonorial</a>
   */
  public int calculate(final String position) {
    final int n = NumberUtils.parse(position);
    return calculateMemoized.apply(n);
  }

  /**
   * Calculates the Fibonorial number for a given position. Uses a LRU cache for caching.
   *
   * @param params the <code>position</code> in the fibonorial sequence.
   * @return the number <code>n</code> that corresponds to the <code>position</code> in the 
   *    fibonorial sequence..
   * @throws IllegalArgumentException if <code>position</code> isn't a valid number or if it is
        negative
   * @see <a href="https://en.wikipedia.org/wiki/Fibonorial">Fibonorial</a>
   */
  int calculateInternal(final Integer position) {
    if (position < 0) {
      throw new IllegalArgumentException("n should be a non-negative number");
    }
    if (position <= 1) {
      return 1;
    }

    int fibonnaciN = this.fibonacciCalculator.calculate(String.valueOf(position));
    int fibonacciNMinusOne = this.fibonacciCalculator.calculate(String.valueOf(position-1));

    return fibonnaciN * this.calculateInternal(fibonacciNMinusOne);
  }
}
