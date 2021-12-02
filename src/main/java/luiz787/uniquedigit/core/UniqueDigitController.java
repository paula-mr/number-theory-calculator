package luiz787.uniquedigit.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unique-digit")
@RequiredArgsConstructor
@Log4j2
public class UniqueDigitController {

  private final DigitalRootCalculator calculator;
  private final DigitalRootCalculationRepository repository;
  private final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<Integer> calculateUniqueDigit(
      @RequestParam final String n,
      @RequestParam final int k,
      @RequestParam(required = false) final Long userId) {
    log.info(userId);
    final int result = calculator.calculate(n, k);

    final var calculation = DigitalRootCalculation.builder().n(n).k(k).result(result).build();
    repository.save(calculation);

    if (userId != null) {
      final var userOpt = userRepository.findById(userId);
      userOpt.ifPresent(
          (user) -> {
            user.addUniqueDigit(calculation);
            userRepository.save(user);
          });
    }

    return ResponseEntity.ok(result);
  }
}
