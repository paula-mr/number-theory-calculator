package luiz787.uniquedigit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UniqueDigitControllerTest {

  @Mock private DigitalRootCalculator calculator;
  @Mock private UserRepository userRepository;
  @Mock private DigitalRootCalculationRepository calculationRepository;
  private UniqueDigitController uniqueDigitController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    uniqueDigitController =
        new UniqueDigitController(calculator, calculationRepository, userRepository);
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

  @Test
  void calculateUniqueDigit_UserIdProvidedAndUserExists_CalculationShouldBeAssociatedWithUser() {
    final String n = "1234";
    final int k = 2;
    final long userId = 44;
    when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
    when(calculator.calculate(n, k)).thenReturn(5);

    uniqueDigitController.calculateUniqueDigit(n, k, userId);

    final var expectedUserToSave = new User();
    expectedUserToSave.addUniqueDigit(DigitalRootCalculation.builder().n(n).k(k).result(5).build());

    verify(userRepository).save(expectedUserToSave);
  }

  @Test
  void calculateUniqueDigit_UserIdProvidedButUserDoesNotExist_CalculationShouldNotBeAssociatedWithAnyUser() {
    final String n = "1234";
    final int k = 2;
    final long userId = 44;

    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    when(calculator.calculate(n, k)).thenReturn(5);

    uniqueDigitController.calculateUniqueDigit(n, k, userId);

    verify(userRepository, never()).save(any());
  }

  @Test
  void calculateUniqueDigit_UserIdNotProvided_CalculationShouldNotBeAssociatedWithAnyUser() {
    final String n = "1234";
    final int k = 2;
    when(calculator.calculate(n, k)).thenReturn(5);

    uniqueDigitController.calculateUniqueDigit(n, k, null);

    verify(userRepository, never()).save(any());
  }

  @Test
  void calculateUniqueDigit_Success_ShouldSaveCalculationInDatabase() {
    final String n = "1234";
    final int k = 2;
    when(calculator.calculate(n, k)).thenReturn(5);

    uniqueDigitController.calculateUniqueDigit(n, k, null);

    verify(calculationRepository).save(DigitalRootCalculation.builder().n(n).k(k).result(5).build());
  }
}
