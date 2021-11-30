package luiz787.uniquedigit.core;

import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class DigitalRootCalculator {
  /**
   * Calculates the Digital Root of <code>p</code>, which is <code>n</code>, concatenated with
   * itself <code>k</code> times.
   *
   * @param n the original number, 1 <= n <= 10^1000000
   * @param k the amount of times to concatenate <code>n</code> with itself, 1 <= k <= 10^5
   * @return the digital root of <code>n</code> concatenated with itself <code>k</code> times, base
   *     10.
   * @throws IllegalArgumentException if <code>n</code> or <code>k</code> are out of bounds, or if
   *     <code>n</code> does not represent an integer.
   * @see <a href="https://en.wikipedia.org/wiki/Digital_root">Digital Root</a>
   */
  public int calculate(final String n, final int k) {
    if (k < 1 || k > Math.pow(10, 5)) {
      throw new IllegalArgumentException("k must be between 1 and 10^5");
    }
    if (!isNValid(n)) {
      throw new IllegalArgumentException("n must be a numeric string between 1 and 10^1000000");
    }

    final BigInteger p = constructP(n, k);
    final int moduloNine = p.mod(BigInteger.valueOf(9L)).intValue();
    return moduloNine == 0 ? 9 : moduloNine;
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
