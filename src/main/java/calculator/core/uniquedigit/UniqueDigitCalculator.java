package calculator.core.uniquedigit;

import calculator.core.LRUCache;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.function.Function;

@Component
public class UniqueDigitCalculator {

  private final Function<UniqueDigitCalculationParameters, Integer> calculateMemoized;

  public UniqueDigitCalculator(final LRUCache<UniqueDigitCalculationParameters, Integer> cache) {
    calculateMemoized = cache.memoize(this::calculateInternal);
  }

  /**
   * Calculates the Digital Root (or unique digit) of <code>p</code>, which is <code>n</code>,
   * concatenated with itself <code>k</code> times. Uses a LRU cache for caching.
   *
   * @param params an object wrapping <code>n</code> and <code>k</code>.
   * @return the digital root of <code>n</code> concatenated with itself <code>k</code> times, base
   *     10.
   * @throws IllegalArgumentException if <code>n</code> or <code>k</code> are out of bounds, or if
   *     <code>n</code> does not represent an integer.
   * @see <a href="https://en.wikipedia.org/wiki/Digital_root">Digital Root</a>
   */
  public int calculate(final UniqueDigitCalculationParameters params) {
    return calculateMemoized.apply(params);
  }

  /**
   * Calculates the Digital Root (or unique digit) of <code>p</code>, which is <code>n</code>,
   * concatenated with itself <code>k</code> times.
   *
   * @param params an object wrapping <code>n</code> and <code>k</code>.
   * @return the digital root of <code>n</code> concatenated with itself <code>k</code> times, base
   *     10.
   * @throws IllegalArgumentException if <code>n</code> or <code>k</code> are out of bounds, or if
   *     <code>n</code> does not represent an integer.
   * @see <a href="https://en.wikipedia.org/wiki/Digital_root">Digital Root</a>
   */
  int calculateInternal(final UniqueDigitCalculationParameters params) {
    if (params.getK() < 1 || params.getK() > Math.pow(10, 5)) {
      throw new IllegalArgumentException("k must be between 1 and 10^5");
    }
    if (!isNValid(params.getN())) {
      throw new IllegalArgumentException("n must be a numeric string between 1 and 10^1000000");
    }

    final BigInteger p = constructP(params.getN(), params.getK());

    final int pModNine = p.mod(BigInteger.valueOf(9L)).intValue();
    return pModNine == 0 ? 9 : pModNine;
  }

  private BigInteger constructP(final String n, final int k) {
    final var concatenatedNumber = n.repeat(k);
    return new BigInteger(concatenatedNumber);
  }

  private boolean isNValid(final String n) {
    try {
      final BigInteger bigIntegerRepresentation = new BigInteger(n);
      return isGreaterThanZero(bigIntegerRepresentation);
    } catch (final NumberFormatException e) {
      return false;
    }
  }

  private boolean isGreaterThanZero(final BigInteger bi) {
    return bi.compareTo(BigInteger.ZERO) > 0;
  }
}
