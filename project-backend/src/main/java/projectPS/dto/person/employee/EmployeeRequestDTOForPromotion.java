package projectPS.dto.person.employee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.model.person.employee.EmployeeType;

/**
 * Data Transfer Object (DTO) representing a request for promoting an employee.
 * This class encapsulates the new salary and promotion title (employee type) for the employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTOForPromotion {
    private Double salary;

    @NotNull(message = "Promotion title cannot be missing.")
    private EmployeeType employeeType;
}
