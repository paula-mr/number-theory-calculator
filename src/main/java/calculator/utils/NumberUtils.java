package calculator.utils;

public class NumberUtils {
    public static int parse(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("the supplied string \"" + s + "\" is not a valid number");
        }
    }
}
