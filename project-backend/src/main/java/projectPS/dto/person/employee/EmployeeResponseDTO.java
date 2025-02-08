package projectPS.dto.person.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectPS.dto.person.PersonResponseDTO;
import projectPS.model.person.employee.EmployeeType;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a response for an employee.
 * This class extends PersonResponseDTO and encapsulates the salary, hire date and employee type of the employee.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO extends PersonResponseDTO {
    private Double salary;
    private LocalDate hireDate;
    private EmployeeTypeDTO employeeType;
}
