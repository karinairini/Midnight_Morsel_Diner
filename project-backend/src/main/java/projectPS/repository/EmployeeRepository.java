package projectPS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectPS.model.person.client.ClientEntity;
import projectPS.model.person.employee.EmployeeEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Repository interface for accessing EmployeeEntity objects in the database.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    /**
     * Retrieves employees based on the provided hire date in a paginated form.
     *
     * @param page        Pageable object specifying the page to retrieve.
     * @param hireDate    The hire date to search by.
     * @return A page of EmployeeEntity objects matching the search criteria.
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM PERSON WHERE hire_date >= :hireDate"
    )
    Page<EmployeeEntity> findAll(Pageable page, @Param("hireDate") LocalDate hireDate);
}
