package calculator.core.triangle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TriangleValidationParameters {
    private final Integer a;
    private final Integer b;
    private final Integer c;
}
