package projectPS.validator.role;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validating person roles.
 * This annotation ensures that the role value is either CLIENT or EMPLOYEE.
 */
@Documented
@Constraint(validatedBy = PersonRoleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonRole {
    String message() default "Role must be CLIENT or EMPLOYEE.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
