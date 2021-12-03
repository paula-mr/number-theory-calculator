package calculator.core.triangle;

import calculator.core.LRUCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("UnitTest")
class TriangleValidatorTest {
    private TriangleValidator validator;

    @Mock
    private LRUCache<TriangleValidationParameters, Boolean> cache;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cache.memoize(any())).then(AdditionalAnswers.returnsFirstArg());
        validator = new TriangleValidator(cache);
    }

    @Test
    void calculate_GivenAIsANegativeNumber_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(-1)
                .b(4)
                .c(5)
                .build();

        final var exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter A (-1) is not a valid triangle side.");
    }

    @Test
    void calculate_GivenBIsANegativeNumber_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(3)
                .b(-876)
                .c(5)
                .build();

        final var exception = assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter B (-876) is not a valid triangle side.");
    }

    @Test
    void calculate_GivenCIsANegativeNumber_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(3)
                .b(4)
                .c(-674)
                .build();

        final var exception= assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter C (-674) is not a valid triangle side.");
    }

    @Test
    void calculate_GivenAIsNull_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(null)
                .b(4)
                .c(5)
                .build();

        final var exception= assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter A should not be null.");
    }

    @Test
    void calculate_GivenBIsNull_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(3)
                .b(null)
                .c(5)
                .build();

        final var exception= assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter B should not be null.");
    }

    @Test
    void calculate_GivenCIsNull_ShouldThrowIllegalArgumentExceptionWithExpectedMessage() {
        final var params = TriangleValidationParameters
                .builder()
                .a(3)
                .b(4)
                .c(null)
                .build();

        final var exception= assertThrows(
                IllegalArgumentException.class,
                () -> validator.validateTriangle(params));

        assertThat(exception.getMessage())
                .isEqualTo("The parameter C should not be null.");
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenBAndCIsGreaterThanA_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(1)
                .b(4)
                .c(6)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenBAndCIsEqualToA_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(2)
                .b(4)
                .c(6)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenAAndCIsGreaterThanB_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(5)
                .b(2)
                .c(8)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenAAndCIsEqualToB_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(5)
                .b(3)
                .c(8)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenAAndBIsGreaterThanC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(14)
                .b(10)
                .c(3)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAbsoluteDifferenceBetweenAAndBIsEqualToC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(14)
                .b(10)
                .c(4)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAIsGreaterThanTheSumOfBAndC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(14)
                .b(8)
                .c(5)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenAIsEqualToTheSumOfBAndC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(13)
                .b(8)
                .c(5)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenBIsGreaterThanTheSumOfAAndC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(5)
                .b(11)
                .c(5)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenBIsEqualToTheSumOfAAndC_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(5)
                .b(10)
                .c(5)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenCIsGreaterThanTheSumOfAAndB_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(11)
                .b(22)
                .c(34)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_GivenCIsEqualToTheSumOfAAndB_ShouldReturnFalse() {
        final var params = TriangleValidationParameters
                .builder()
                .a(11)
                .b(22)
                .c(33)
                .build();

        assertThat(validator.validateTriangle(params)).isFalse();
    }

    @Test
    void calculate_WhenAllSidesAreGreaterThanTheAbsoluteDifferenceThanTheOtherOnesAndAreLesserThanTheSumOfTheOtherOnes_ShouldReturnTrue() {
        final var params = TriangleValidationParameters
                .builder()
                .a(11)
                .b(22)
                .c(30)
                .build();

        assertThat(validator.validateTriangle(params)).isTrue();
    }

}
