package projectPS.dto.person.employee;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import projectPS.dto.person.PersonRequestDTO;
import projectPS.model.person.employee.EmployeeType;

import java.time.LocalDate;

/**
 * Represents a Data Transfer Object (DTO) for an employee request.
 * This DTO extends PersonRequestDTO and encapsulates the salary, hire date and type for an employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeRequestDTO extends PersonRequestDTO {
    @Min(value = 0, message = "Salary cannot be less that 0.")
    private Double salary;

    @Past(message = "The hire date must be made in a past or present date.")
    private LocalDate hireDate;

    @NotNull(message = "The type of employment cannot be missing.")
    private EmployeeType employeeType;
}
