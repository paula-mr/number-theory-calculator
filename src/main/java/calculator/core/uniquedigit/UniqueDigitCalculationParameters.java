package calculator.core.uniquedigit;

import lombok.Value;

@Value
public class UniqueDigitCalculationParameters {
  String n;
  int k;
  /**
   * @param n the original number, 1 <= n <= 10^1000000
   * @param k the amount of times to concatenate <code>n</code> with itself, 1 <= k <= 10^5
   */
  public UniqueDigitCalculationParameters(String n, int k) {
    this.n = n;
    this.k = k;
  }
}
