package projectPS.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectPS.model.reservation.ReservationEntity;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing ReservationEntity objects in the database.
 */
@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, UUID> {

    /**
     * Retrieves reservations based on the provided status in a paginated form.
     *
     * @param page      Pageable object specifying the page to retrieve.
     * @param status    The status to search by.
     * @return A page of ReservationEntity objects matching the search criteria.
     */
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM RESERVATION WHERE status = :status"
    )
    Page<ReservationEntity> findAll(Pageable page, @Param("status") String status);

    /**
     * Retrieves a list of reservations associated with the given client ID.
     *
     * @param clientId The ID of the client whose reservations to retrieve.
     * @return A list of ReservationEntity objects associated with the client.
     */
    List<ReservationEntity> findByClientId(UUID clientId);
}
