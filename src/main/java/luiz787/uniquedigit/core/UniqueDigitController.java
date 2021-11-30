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

  @GetMapping
  public ResponseEntity<Integer> calculateUniqueDigit(
      @RequestParam final String n, @RequestParam final int k, @RequestParam final Long userId) {
    log.info(userId);
    final int result = calculator.calculate(n, k);

    return ResponseEntity.ok(result);
  }
}
