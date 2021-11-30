package luiz787.uniquedigit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UniqueDigitControllerTest {

  @Mock private DigitalRootCalculator calculator;
  private UniqueDigitController uniqueDigitController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    uniqueDigitController = new UniqueDigitController(calculator);
  }

  @Test
  void calculateUniqueDigit_Success_HttpStatusShouldBe200() {
    final String n = "1234";
    final int k = 2;
    when(calculator.calculate(n, k)).thenReturn(5);

    final var response = uniqueDigitController.calculateUniqueDigit(n, k, null);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void calculateUniqueDigit_Success_ResponseBodyShouldBeTheNumberReturnedByCalculator() {
    final String n = "1234";
    final int k = 2;
    when(calculator.calculate(n, k)).thenReturn(5);

    final var response = uniqueDigitController.calculateUniqueDigit(n, k, null);
    assertEquals(5, response.getBody());
  }
}
