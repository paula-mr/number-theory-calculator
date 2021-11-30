package luiz787.uniquedigit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DigitalRootCalculatorTest {

  private DigitalRootCalculator calculator;

  @BeforeEach
  public void setup() {
    calculator = new DigitalRootCalculator();
  }

  @Test
  void calculate_GivenNIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "-2";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculate_GivenNIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "0";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculate_GivenKIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = -1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void calculate_GivenKIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = 0;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculate_GivenKIsGreaterThanTenToTheFive_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = (int) Math.pow(10, 5) + 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculate_GivenNADecimalStringWithDot_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123.456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculate_GivenNIsADecimalStringWithComma_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123,456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculate_GivenNIsNotANumericString_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "Teste";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculate_GivenNHasASingleDigitAndKIsOne_ShouldReturnK() {
    final String n = "1";
    final int k = 1;

    assertEquals(1, calculator.calculate(n, k));
  }

  @Test
  void givenNHasASingleDigitAndKIsGreaterThanOne_ShouldReturnMod9() {
    final String n = "1";
    final int k = 2;

    assertEquals(2, calculator.calculate(n, k));
  }

  @Test
  void calculate_GivenNEquals9875AndKEquals4_ShouldReturn8() {
    final String n = "9875";
    final int k = 4;

    assertEquals(8, calculator.calculate(n, k));
  }
}
