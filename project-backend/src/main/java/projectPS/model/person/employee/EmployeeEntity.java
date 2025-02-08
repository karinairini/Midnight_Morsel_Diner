package projectPS.model.person.employee;

import jakarta.persistence.*;
import lombok.*;
import projectPS.model.person.PersonEntity;

import java.time.LocalDate;

/**
 * Represents an Employee entity. This class extends the PersonEntity class and serves as a subclass representing
 * employees. It encapsulates the data of an employee including its unique identifier, name, email, password,
 * role, salary, hire date and employee type.
 * It serves as a POJO for mapping data to the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("EMPLOYEE")
public class EmployeeEntity extends PersonEntity {
    @Column(name = "salary")
    private Double salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "employee_type")
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;
}
