package projectPS.validator.role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import projectPS.model.person.Role;

/**
 * Validator for the PersonRole annotation.
 * This validator checks whether the role value is either CLIENT or EMPLOYEE.
 */
public class PersonRoleValidator implements ConstraintValidator<PersonRole, Role> {

    /**
     * Validates the role value.
     *
     * @param role    The role value to be validated.
     * @param context The constraint validator context.
     * @return true if the role value is either CLIENT or EMPLOYEE, otherwise false.
     */
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        return role.equals(Role.CLIENT) || role.equals(Role.EMPLOYEE);
    }

}
