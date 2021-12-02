package luiz787.uniquedigit.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  private static final String FALLBACK_MESSAGE = "Field is not valid.";

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException caught at GlobalExceptionHandler", e);
    return e.getBindingResult().getAllErrors().stream()
        .map(objectError -> (FieldError) objectError)
        .collect(
            Collectors.toMap(
                FieldError::getField,
                (error) ->
                    Optional.ofNullable(error.getDefaultMessage()).orElse(FALLBACK_MESSAGE)));
  }

  @ExceptionHandler(CryptoException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleCryptoException(final CryptoException e) {
    log.error("CryptoException caught at GlobalExceptionHandler", e);
    return Map.of("message", "Provided public key is invalid.");
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleMethodArgumentTypeMismatchException(
      final MethodArgumentTypeMismatchException e) {
    log.error("MethodArgumentTypeMismatchException caught at GlobalExceptionHandler", e);
    return Map.of(e.getName(), Optional.ofNullable(e.getMessage()).orElse(FALLBACK_MESSAGE));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleIllegalArgumentException(final IllegalArgumentException e) {
    log.error("IllegalArgumentException caught at GlobalExceptionHandler", e);
    return Map.of("message", Optional.ofNullable(e.getMessage()).orElse(FALLBACK_MESSAGE));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Map<String, String> handleException(final Exception e) {
    log.error("Exception caught at GlobalExceptionHandler", e);
    return Map.of("message", e.getMessage());
  }
}
