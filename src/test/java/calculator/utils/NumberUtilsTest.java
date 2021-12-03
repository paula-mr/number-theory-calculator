package calculator.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UnitTest")
public class NumberUtilsTest {
    @Test
    void parse_GivenSIsInvalid_ShouldThrowIllegalArgumentException() {
        final String position = "invalid";

        assertThrows(
                IllegalArgumentException.class,
                () -> NumberUtils.parse(position),
                "the supplied string \"invalid\" is not a valid number");
    }

    @Test
    void parse_GivenSIsNull_ShouldThrowIllegalArgumentException() {
        final String position = null;

        assertThrows(
                IllegalArgumentException.class,
                () -> NumberUtils.parse(position),
                "the supplied string \"null\" is not a valid number");
    }

    @Test
    public void parse_GivenSIsEmpty_ShouldThrowIllegalArgumentException() {
        final String position = "";

        assertThrows(
                IllegalArgumentException.class,
                () -> NumberUtils.parse(position),
                "the supplied string \"null\" is not a valid number");
    }

    @Test
    public void parse_GivenSIsNegative_ShouldReturnNegativeNumber() {
        final String position = "-1";

        assertThat(NumberUtils.parse(position)).isEqualTo(-1);
    }

    @Test
    public void parse_GivenSIsPositive_ShouldReturnPositiveNumber() {
        final String position = "5";

        assertThat(NumberUtils.parse(position)).isEqualTo(5);
    }
}