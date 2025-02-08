package projectPS.validator.page;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation to validate that the page size is an odd number.
 */
@Documented
@Constraint(validatedBy = OddPageSizeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OddPageSize {
    String message() default "Page size must be an odd number.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
