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
  void calculate_givenNIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "-2";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculate_givenNIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "0";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void calculate_givenKIsNegative_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = -1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void calculate_givenKIsZero_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = 0;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculate_givenKIsGreaterThanTenToTheFive_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "1337";
    final int k = (int) Math.pow(10, 5) + 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "k must be between 1 and 10^5");
  }

  @Test
  void
      calculate_givenNADecimalStringWithDot_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123.456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculate_givenNIsADecimalStringWithComma_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "123,456";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void
      calculate_givenNIsNotANumericString_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
    final String n = "Teste";
    final int k = 1;

    assertThrows(
        IllegalArgumentException.class,
        () -> calculator.calculate(n, k),
        "n must be a numeric string between 1 and 10^1000000");
  }

  @Test
  void GivenNHasASingleDigitAndKIsOne_ShouldReturnK() {
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
  void calculate() {
    final String n = "9875";
    final int k = 4;

    assertEquals(8, calculator.calculate(n, k));
  }
}
