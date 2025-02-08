package projectPS.model.person.client;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import projectPS.model.person.PersonEntity;

import java.time.LocalDate;

/**
 * Represents a Client entity. This class extends the PersonEntity class and serves as a subclass representing
 * clients. It encapsulates the data of a client including its unique identifier, name, email, password,
 * role and registration date.
 * It serves as a POJO for mapping data to the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CLIENT")
public class ClientEntity extends PersonEntity {
    @Column(name = "registration_date")
    private LocalDate registrationDate;
}
