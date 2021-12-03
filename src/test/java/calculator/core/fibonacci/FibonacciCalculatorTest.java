package calculator.core.fibonacci;

import calculator.core.LRUCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FibonacciCalculatorTest {

    private FibonacciCalculator calculator;
    @Mock
    private LRUCache<Integer, Integer> cache;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(cache.memoize(any())).then(AdditionalAnswers.returnsFirstArg());
        calculator = new FibonacciCalculator(cache);
    }

    @Test
    void calculate_GivenNIsNegative_ShouldThrowIllegalArgumentException() {
        final String position = "-1";

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(position),
                "n should be a non-negative number");
    }

    @Test
    void calculate_GivenPositionIsNotAValidInteger_ShouldThrowIllegalArgumentException() {
        final String position = "invalid";

        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(position),
                "the supplied string \"invalid\" is not a valid number");
    }

    @Test
    void calculate_GivenPositionIsZero_ReturnsZero() {
        final String position = "0";

        assertThat(calculator.calculate(position)).isEqualTo(0);
    }

    @Test
    void calculate_GivenPositionIsOne_ReturnsOne() {
        final String position = "1";

        assertThat(calculator.calculate(position)).isEqualTo(1);
    }

    @Test
    void calculate_GivenPositionIsThree_ReturnsTwo() {
        final String position = "3";

        assertThat(calculator.calculate(position)).isEqualTo(2);
    }
}
