package seedu.address.logic.parser.inputpatterns;

import java.util.function.Supplier;

/**
 * A Token that takes in any integer
 * The range of valid integers are given by the Suppliers minValueSupplier and maxValueSupplier
 */
public class IntegerToken extends Token {

    private final int minValue;
    private final int maxValue;

    /**
     * @param _minValue the minimum possible integer in this field
     * @param _maxValue the maximum possible integer in this field
     */
    public IntegerToken(String _id, int _minValue, int _maxValue) {
        super(_id);
        this.minValue = _minValue;
        this.maxValue = _maxValue;
    }

    @Override
    public String getPreview() {
        return "[" + minValue + "..." + maxValue + "]";
    }

    @Override
    public boolean matches(String segment) {
        try {
            int value = Integer.parseInt(segment);

            if (value < minValue) {
                return false;
            }
            if (value > maxValue) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean prefixMatches(String segment) {
        if (segment.isEmpty()) {
            return true;
        }

        try {
            int value = Integer.parseInt(segment);

            if (value > maxValue) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
