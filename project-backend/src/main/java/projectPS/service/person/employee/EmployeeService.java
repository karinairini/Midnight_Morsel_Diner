package projectPS.service.person.employee;

import projectPS.dto.CollectionResponseDTO;
import projectPS.dto.person.employee.EmployeeRequestDTO;
import projectPS.dto.person.employee.EmployeeRequestDTOForPromotion;
import projectPS.dto.person.employee.EmployeeResponseDTO;
import projectPS.exception.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service interface for managing Employee entities.
 * This includes:
 * - Retrieving all employees
 * - Retrieving an employee by id
 * - Saving an employee
 * - Promoting an employee
 * - Deleting an employee
 */
public interface EmployeeService {

    /**
     * Retrieves a paginated list of employees based on specified parameters.
     * This method allows filtering by hire date, sorting by a specified field
     * and specifying the page number and page size.
     *
     * @param hireDate   The hire date to filter by.
     * @param sortBy     The field to sort by.
     * @param pageNumber The page number.
     * @param pageSize   The number of items per page.
     * @return A collection response DTO containing the list of employees and total count.
     */
    CollectionResponseDTO<EmployeeResponseDTO> getEmployees(LocalDate hireDate, String sortBy, Integer pageNumber, Integer pageSize);

    /**
     * Retrieves an employee by their unique identifier.
     *
     * @param id The unique identifier of the employee.
     * @return The response DTO representing the employee.
     * @throws NotFoundException If the employee with the specified ID is not found.
     */
    EmployeeResponseDTO getById(UUID id);

    /**
     * Saves a new employee based on the provided request DTO.
     *
     * @param employeeRequestDTO The request DTO containing data for the new employee.
     * @return The response DTO representing the saved employee.
     * @throws NotFoundException If the role specified in the request is not supported.
     */
    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    /**
     * Promotes an employee by updating their employee type and salary.
     *
     * @param id                 The unique identifier of the employee to promote.
     * @param employeeRequestDTO The request DTO containing data for the promotion.
     * @return The response DTO representing the promoted employee.
     * @throws NotFoundException If the employee with the specified ID is not found.
     */
    EmployeeResponseDTO promoteEmployee(UUID id, EmployeeRequestDTOForPromotion employeeRequestDTO);

    /**
     * Deletes an employee by their unique identifier.
     *
     * @param id The unique identifier of the employee to delete.
     * @throws NotFoundException If the employee with the specified ID is not found.
     */
    void deleteEmployeeById(UUID id);
}
