package calculator.core.triangle;

import calculator.core.LRUCache;

import java.util.function.Function;

public class TriangleValidator {

    private final Function<TriangleValidationParameters, Boolean> validateMemoized;

    public TriangleValidator(final LRUCache<TriangleValidationParameters, Boolean> cache) {
        validateMemoized = cache.memoize(this::validateInternal);
    }

    public boolean validateTriangle(final TriangleValidationParameters parameters) {
        return validateMemoized.apply(parameters);
    }

    boolean validateInternal(final TriangleValidationParameters parameters) {
        checkIfParameterIsValid(parameters);
        final int a = parameters.getA();
        final int b = parameters.getB();
        final int c = parameters.getC();

        /*
        | b - c | < a < b + c
        | a - c | < b < a + c
        | a - b | < c < a + b
         */

        return Math.abs(b - c) < a
                && a < b + c
                && Math.abs(a - c) < b
                && b < a + c
                && Math.abs(a - b) < c
                && c < a + b;
    }

    private void checkIfParameterIsValid(final TriangleValidationParameters parameters) {
        validateParameter("A", parameters.getA());
        validateParameter("B", parameters.getB());
        validateParameter("C", parameters.getC());
    }

    private void validateParameter(final String paramName, final Integer paramValue) {
        if (paramValue == null) {
            throw new IllegalArgumentException("The parameter " + paramName + " should not be null.");
        }
        if (paramValue < 0) {
            throw new IllegalArgumentException("The parameter " + paramName + " (" + paramValue +
                    ") is not a valid triangle side.");
        }
    }
}
