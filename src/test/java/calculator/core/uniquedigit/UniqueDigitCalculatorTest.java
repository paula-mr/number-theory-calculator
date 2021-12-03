package calculator.core.uniquedigit;

import calculator.core.LRUCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("UnitTest")
class UniqueDigitCalculatorTest {

  private UniqueDigitCalculator calculator;
  @Mock private LRUCache<UniqueDigitCalculationParameters, Integer> cache;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    calculator = new UniqueDigitCalculator(cache);
  }

  @Test
  void calculateInternal_GivenNIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "-2";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculateInternal_GivenNIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "0";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculateInternal_GivenKIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = -1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "k must be between 1 and 10^5");
  }

  @Test
  void calculateInternal_GivenKIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = 0;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculateInternal_GivenKIsGreaterThanTenToTheFive_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = (int) Math.pow(10, 5) + 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculateInternal_GivenNADecimalStringWithDot_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123.456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculateInternal_GivenNIsADecimalStringWithComma_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123,456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculateInternal_GivenNIsNotANumericString_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "Teste";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculateInternal_GivenNHasASingleDigitAndKIsOne_ShouldReturnK() {
    final String n = "1";
    final int k = 1;

    assertEquals(1, calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)));
  }

  @Test
  void calculateInternal_givenNHasASingleDigitAndKIsGreaterThanOne_ShouldReturnMod9() {
    final String n = "1";
    final int k = 2;

    assertEquals(2, calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)));
  }

  @Test
  void calculateInternal_GivenNEquals9875AndKEquals4_ShouldReturn8() {
    final String n = "9875";
    final int k = 4;

    assertEquals(8, calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)));
  }

  @Test
  void calculateInternal_givenNIsDivisibleByNine_ShouldReturn9() {
    final String n = "18";
    final int k = 1;

    assertEquals(9, calculator.calculateInternal(new UniqueDigitCalculationParameters(n, k)));
  }
}
