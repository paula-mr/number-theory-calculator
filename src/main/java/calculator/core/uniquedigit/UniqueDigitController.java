package calculator.core.uniquedigit;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import calculator.core.user.UserRepository;
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

  private final UniqueDigitCalculator calculator;
  private final UniqueDigitCalculationRepository repository;
  private final UserRepository userRepository;

  @GetMapping
  @ApiOperation("Calculates the unique digit for the provided n and k values.")
  @ApiResponses({
    @ApiResponse(
        code = 200,
        message = "Unique digit for the set of parameters.",
        response = Integer.class),
    @ApiResponse(code = 400, message = "Invalid parameters provided."),
    @ApiResponse(code = 500, message = "Internal server error.")
  })
  public ResponseEntity<Integer> calculateUniqueDigit(
      @RequestParam final String n,
      @RequestParam final int k,
      @RequestParam(required = false) final Long userId) {
    final int result = calculator.calculate(new UniqueDigitCalculationParameters(n, k));

    final var calculation = UniqueDigitCalculation.builder().n(n).k(k).result(result).build();
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
