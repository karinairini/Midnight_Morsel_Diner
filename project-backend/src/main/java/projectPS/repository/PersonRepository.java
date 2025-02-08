package projectPS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectPS.model.person.PersonEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing PersonEntity objects in the database.
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    /**
     * Retrieves a person entity based on the provided email.
     *
     * @param email The email of the person to retrieve.
     * @return An Optional containing the person entity if found, otherwise empty.
     */
    Optional<PersonEntity> findByEmail(String email);
}
