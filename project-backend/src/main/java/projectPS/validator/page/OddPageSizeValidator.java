package projectPS.validator.page;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator class for the OddPageSize annotation.
 * This class checks if the provided value is an odd number.
 */
public class OddPageSizeValidator implements ConstraintValidator<OddPageSize, Integer> {

    /**
     * Validates if the given value is an odd number.
     *
     * @param value   The value to validate.
     * @param context Context in which the constraint is evaluated.
     * @return true if the value is an odd number, false otherwise.
     */
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value % 2 == 1;
    }
}
