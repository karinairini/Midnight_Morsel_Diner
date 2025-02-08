package projectPS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectPS.model.person.client.ClientEntity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Repository interface for accessing ClientEntity objects in the database.
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, UUID> {

    /**
     * Retrieves clients based on the provided hire date in a paginated form.
     *
     * @param page                Pageable object specifying the page to retrieve.
     * @param registrationDate    The registration date to search by.
     * @return A page of ClientEntity objects matching the search criteria.
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM PERSON WHERE registration_date >= :registrationDate"
    )
    Page<ClientEntity> findAll(Pageable page, @Param("registrationDate") LocalDate registrationDate);
}
